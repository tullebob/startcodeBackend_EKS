package facades;

import dto.BookDTO;
import entities.Book;
import errorhandling.API_Exception;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class BookFacade {

    private static EntityManagerFactory emf;
    private static BookFacade instance;

    public BookFacade() {
    }

    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    public List<BookDTO> getAllBooks() throws API_Exception {
        EntityManager em = emf.createEntityManager();
        List<Book> allBooks = new ArrayList();
        List<BookDTO> allBooksDTO = new ArrayList();

        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            allBooks = query.getResultList();

            if (allBooks.isEmpty()) {
                throw new API_Exception("No books found, 404");
            }
            for (Book book : allBooks) {
                allBooksDTO.add(new BookDTO(book));
            }

            return allBooksDTO;

        } finally {
            em.close();
        }

    }
    
    public List<BookDTO> searchBook(String title, String author) throws API_Exception {
        EntityManager em = emf.createEntityManager();
        List<Book> allBooks = new ArrayList();
        List<BookDTO> allBooksDTO = new ArrayList();

        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.title = :title AND b.author = :author", Book.class)
                    .setParameter("title", title).setParameter("author", author);
            allBooks = query.getResultList();

            if (allBooks.isEmpty()) {
                throw new API_Exception("No books found, 404");
            }
            for (Book book : allBooks) {
                allBooksDTO.add(new BookDTO(book));
            }

            return allBooksDTO;

        } finally {
            em.close();
        }

    }

    public String testData() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Book book = new Book(111, "testTitel", "testAuthor", "testPublisher", 2000);
            Book book2 = new Book(222, "Titel", "Author", "Publisher", 2013);
            Book book3 = new Book(333, "testTitel3", "testAuthor3", "testPublisher3", 2007);
            
            em.persist(book);
            em.persist(book2);
            em.persist(book3);
            
            em.getTransaction().commit();
            
            return "Test data indsat";
        } finally {
            em.close();
        }
    }

}
