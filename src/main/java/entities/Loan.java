
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name= "loan")
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
     @Column(name="checkout_date", nullable = false)

 private String checkoutDate;
 @Column(name="due_date", nullable = false)
 private String dueDate;
 @Column(name="returned_date", nullable = true)
 private String returnedDate;
 
 @ManyToOne
 private User user;
 
 @ManyToOne
 private Book book;

    public Book getBook() {
        return book;
    }

    public Loan(String checkoutDate, String dueDate) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }
    
    

    public void setBook(Book book) {
        this.book = book;
    }
 
 
    
    public Loan(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Loan(Long id, String checkoutDate, String dueDate, String returnedDate, User user, Book book) {
        this.id = id;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.user = user;
        this.book = book;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }
 
 

   
 
}
