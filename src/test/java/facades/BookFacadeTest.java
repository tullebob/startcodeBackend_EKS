/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.BookDTO;
import entities.Book;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author jimmy
 */
public class BookFacadeTest {
    private static EntityManagerFactory emf;
    private static BookFacade facade;
    
    private static User user = new User("user", "password");
    private static User admin = new User("admin", "password");
    private static User both = new User("user_admin", "password");
    
    
    private static Book book = new Book(111, "Løvernes Konge", "Donald Trump", "Republikanerne", 2000);
    private static Book book2 = new Book(222, "Harry", "Potter", "Gyldendal", 2013);
    private static Book book3 = new Book(333, "Bjørnenes ven", "testAuthor3", "testPublisher3", 2007);

    public BookFacadeTest() {}
    
    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = BookFacade.getBookFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() 
    {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() 
    {
        EntityManager em = emf.createEntityManager();
        try 
        {
            em.getTransaction().begin();
                em.createQuery("delete from User").executeUpdate();
                em.createQuery("delete from Role").executeUpdate();
                 
    Book book = new Book(111, "Løvernes Konge", "Donald Trump", "Republikanerne", 2000);
    Book book2 = new Book(222, "Harry", "Potter", "Gyldendal", 2013);
    Book book3 = new Book(333, "Bjørnenes ven", "testAuthor3", "testPublisher3", 2007);
                Role userRole = new Role("user");
                Role adminRole = new Role("admin");
                user.addRole(userRole);
                admin.addRole(adminRole);
                both.addRole(userRole);
                both.addRole(adminRole);
                em.persist(userRole);
                em.persist(adminRole);
                em.persist(user);
                em.persist(admin);
                em.persist(both);
                em.persist(book);
                em.persist(book2);
                em.persist(book3);
            em.getTransaction().commit();
        } finally 
        {
            em.close();
        }
    }
    
    @Test
    public void testAddBook() {
        int isbn = 888;
        String title = "Havet";
        String author = "Per Daal";
        String publisher = "rakaen";
        int publicYear = 1994;
        
        BookDTO result = facade.createBook(isbn, title, author, publisher, publicYear);
        Book b = new Book (isbn, title, author, publisher, publicYear );
        BookDTO expected = new BookDTO(b);
        assertEquals(expected.getTitle(), result.getTitle());
    }
    
}
