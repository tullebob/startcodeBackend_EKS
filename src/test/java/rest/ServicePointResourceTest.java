package rest;

import dto.AddressRequestDTO;
import dto.PostnordResponseDTO;
import dto.WeatherResponseDTO;
import utils.EMF_Creator;
import rest.ServicePointResource;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class ServicePointResourceTest 
{

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static AddressRequestDTO addressDTO = new AddressRequestDTO("Herlev", "2730", "Kamdalen", "21");
    private static final ExecutorService es = Executors.newCachedThreadPool();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() 
    {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() 
    {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() 
    {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/servicepoints").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception 
    {
        given()
                .contentType("application/json")
                .get("/servicepoints").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }
    
    @Test
    public void responseFromExternalServersParallelTest() throws InterruptedException, ExecutionException, TimeoutException 
    {
        String expectedServicepoints = "\"servicePoints\":";
        String expectedWeather = "\"weather\": {";
        String result = ServicePointResource.responseFromExternalServersParallel(es, addressDTO);
        boolean isExpectedLikeResult = (result.contains(expectedServicepoints) && result.contains(expectedWeather));
        
        assertTrue(isExpectedLikeResult);
    }
    
    @Test
    public void servicepointsTest() {
        String json = String.format
        (
                "{city: \"%s\", postalCode: \"%s\", streetName: \"%s\", streetNumber: \"%s\"}"
                , addressDTO.getCity(), addressDTO.getPostalCode(), addressDTO.getStreetName(), addressDTO.getStreetNumber()
        );
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .body(json)
                .when().post("/servicepoints/servicepoints")
                .then()
                .statusCode(200);
    }
}
