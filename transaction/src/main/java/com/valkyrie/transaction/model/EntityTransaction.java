package com.valkyrie.transaction.model;

import java.util.Date;

public class EntityTransaction {
    private String bookId;
    private Date issueDate;
    private Date returnDate;

    public EntityTransaction(String bookId, Date issueDate, Date returnDate) {
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public String getBookId() {return bookId;}

    public EntityTransaction setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public Date getIssueDate() {return issueDate;}

    public EntityTransaction setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public Date getReturnDate() {return returnDate;}

    public EntityTransaction setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        return this;
    }

}
