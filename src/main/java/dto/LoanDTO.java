
package dto;

import entities.Book;
import entities.Loan;
import java.util.Date;

public class LoanDTO {
    private String checkoutDate;
    private String dueDate;
    private String returnedDate;
    
    private long book;
    private String username;

    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    

    public LoanDTO(Loan loan) {
        this.checkoutDate = loan.getCheckoutDate();
        this.dueDate = loan.getDueDate();
        this.returnedDate = loan.getReturnedDate();
        this.book = loan.getBook().getId();
        this.username = loan.getUser().getUserName();
        
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
