
package dto;

import entities.Loan;
import java.util.Date;

public class LoanDTO {
    private Date checkoutDate;
    private Date dueDate;
    private Date returnedDate;

    public LoanDTO(Loan loan) {
        this.checkoutDate = loan.getCheckoutDate();
        this.dueDate = loan.getDueDate();
        this.returnedDate = loan.getReturnedDate();
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
