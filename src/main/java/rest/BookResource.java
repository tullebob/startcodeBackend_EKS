
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookDTO;
import entities.Book;
import errorhandling.API_Exception;
import facades.BookFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

@Path("books")
public class BookResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BookFacade facade =  BookFacade.getBookFacade(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<BookDTO> books = new ArrayList();
    

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String demo() {
        facade.testData();
        return "{\"msg\":\"Der er hul igennem\"}";
    }
    
    @Path("all")
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllBooks() throws API_Exception {
        return gson.toJson(facade.getAllBooks());
    }
    
    
}
