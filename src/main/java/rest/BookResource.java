
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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    
    @Path("search/{title}/{author}")
    @GET
    @Produces ({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String searchBook(@PathParam("title") String title, @PathParam("author")String author) throws API_Exception {
        return gson.toJson(facade.searchBook(title, author));
        /*String msg;
        msg = title + author;
        return gson.toJson(msg);*/
    }
    
    /*@Path("search2")
    @POST
    @Produces ({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String searchBook2(String title, String author) throws API_Exception {
        return gson.toJson(facade.searchBook(title, author));
    }*/
    
    
    
}
