package com.valkyrie.transaction.model;

import java.util.Date;

public class EntityTransactionDTO {
    private BookDTO bookDTO;
    private Date issueDate;
    private Date returnDate;

    public BookDTO getBookDTO() {return bookDTO;}

    public EntityTransactionDTO setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
        return this;
    }

    public Date getIssueDate() {return issueDate;}

    public EntityTransactionDTO setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public Date getReturnDate() {return returnDate;}

    public EntityTransactionDTO setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        return this;
    }

}
