package facades;

import dto.BookDTO;
import dto.LoanDTO;
import entities.Book;
import entities.Loan;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
            
            User user = new User("user", "1234");
            
            User admin = new User("admin", "1234");
            
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            
            user.addRole(userRole);
            admin.addRole(adminRole);
            
            
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(admin);
            em.persist(user);
            
            em.persist(book);
            em.persist(book2);
            em.persist(book3);
            
            em.getTransaction().commit();
            
            return "Test data indsat";
        } finally {
            em.close();
        }
    }
    
    public LoanDTO createLoan(Date checkoutDate, Date dueDate, int bookID, String userName ) {
        EntityManager em = emf.createEntityManager();
        
        Loan loan;
        
        try {
            em.getTransaction().begin();
            loan = new Loan(checkoutDate, dueDate);
        } finally {
            em.close();
        }
        
        Book book = em.find(Book.class, bookID);
        User user = em.find(User.class, userName);
        
        user.addLoan(loan);
        book.addLoan(loan);
        loan.setUser(user);
        loan.setBook(book);
        
        em.getTransaction().begin();
            em.persist(loan);
        em.getTransaction().commit();
        return new LoanDTO(loan);
    }
    
    //int isbn, String title, String author, String publisher, int publishYear
    public BookDTO createBook(int isbn, String title, String author, String publisher, int publishYear) {
        EntityManager em = emf.createEntityManager();
        Book book;
        try{
            em.getTransaction().begin();
            book = new Book(isbn, title, author, publisher, publishYear);
            em.persist(book);
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }return new BookDTO(book);
    }
    
    public BookDTO deleteBook(long id){
        EntityManager em = emf.createEntityManager();
        Book identifyBook = null;
        try{
           em.getTransaction().begin();
           identifyBook = em.find(Book.class, id);
           
           if (identifyBook == null) {
               throw new NoResultException("Book not found");
           }
           em.remove(identifyBook);
           em.getTransaction().commit();
        } finally {
            em.close();
        } 
        return new BookDTO(identifyBook);
    }
    
     public BookDTO editBook(long id, int editedIsbn, String editedTitle, String editedAuthor, String editedPublisher, int editedPublishYear) {
        EntityManager em = emf.createEntityManager();
        Book book = null;
        try {
            em.getTransaction().begin();
            book = em.find(Book.class, id);

            if (book == null) {
                throw new NoResultException("Book does not exist");
            }
            book.setIsbn(editedIsbn);
            book.setTitle(editedTitle);
            book.setAuthor(editedAuthor);
            book.setPublisher(editedPublisher);
            book.setPublishYear(editedPublishYear);
            em.getTransaction().commit();
            

        } finally {
            em.close();
        }

        return new BookDTO(book);

    }
    
}
