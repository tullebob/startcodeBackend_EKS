package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.AddressRequestDTO;
import dto.PostnordResponseDTO;
import dto.ServicepointsResponseDTO;
import dto.WeatherResponseDTO;
import utils.EMF_Creator;
import facades.FacadeExample;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.Helper;
import utils.HttpUtils;
import utils.Keys;

@Path("servicepoints")
public class ServicePointResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final FacadeExample facade =  FacadeExample.getFacadeExample(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String postnordURL = "https://api2.postnord.com/rest/businesslocation/v1/servicepoint/";
    private static final String weatherURL = "https://api.weatherbit.io/v2.0/current";
    private static final ExecutorService es = Executors.newCachedThreadPool();
    private static Helper helper = new Helper();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("servicepoints")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String getServicePointAndWeather(String address) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        AddressRequestDTO adresse = gson.fromJson(address, AddressRequestDTO.class);
        return responseFromExternalServersParallel(es, adresse);
    }
    
    public static String responseFromExternalServersParallel(ExecutorService threadPool, AddressRequestDTO adresse) throws InterruptedException, ExecutionException, TimeoutException {
        String city = adresse.getCity();
        String postalCode = adresse.getPostalCode();
        String streetName = adresse.getStreetName();
        String streetNumber = adresse.getStreetNumber();
        
        Callable<PostnordResponseDTO> postnordTask = new Callable<PostnordResponseDTO>() {
            @Override
            public PostnordResponseDTO call() throws IOException {
                String fullURL = (postnordURL + "findNearestByAddress.json?apikey=" + Keys.postNordKey + "&countryCode=DK&agreementCountry=DK&city=" + helper.fixInput(city)
                + "&postalCode=" + helper.fixInput(postalCode) + "&streetName=" + helper.fixInput(streetName) + "&streetNumber=" + helper.fixInput(streetNumber));
                String postnord = HttpUtils.fetchData(fullURL);
                PostnordResponseDTO postnordDTO = gson.fromJson(postnord, PostnordResponseDTO.class);
                return postnordDTO;
            }
        };
        Callable<WeatherResponseDTO> weatherTask = new Callable<WeatherResponseDTO>() {
            @Override
            public WeatherResponseDTO call() throws IOException {
                String weather = HttpUtils.fetchData(weatherURL + "?key=" + Keys.weatherKey + "&lang=da&postal_code=" + postalCode + "&country=DK");
                WeatherResponseDTO weatherDTO = gson.fromJson(weather, WeatherResponseDTO.class);
                return weatherDTO;
            }
        };
        
        Future<PostnordResponseDTO> futurePostnord = threadPool.submit(postnordTask);
        Future<WeatherResponseDTO> futureWeather = threadPool.submit(weatherTask);
        
        PostnordResponseDTO postnord = futurePostnord.get(3, TimeUnit.SECONDS);
        WeatherResponseDTO weather = futureWeather.get(3, TimeUnit.SECONDS);
        
        ServicepointsResponseDTO combinedDTO = new ServicepointsResponseDTO(postnord, weather);
        String combinedJSON = gson.toJson(combinedDTO);
        
        return combinedJSON;
    }
  
    
}
