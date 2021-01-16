
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
 @Temporal(TemporalType.DATE)
 private Date checkoutDate;
 @Column(name="due_date", nullable = false)
 @Temporal(TemporalType.DATE)
 private Date dueDate;
 @Column(name="returned_date", nullable = true)
 @Temporal(TemporalType.DATE)
 private Date returnedDate;
 
 @ManyToOne
 private User user;
 
 @ManyToOne
 private Book book;

    public Book getBook() {
        return book;
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
 
 

    public Loan(Date checkoutDate, Date dueDate) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnedDate = new Date();
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }
 
 
}
