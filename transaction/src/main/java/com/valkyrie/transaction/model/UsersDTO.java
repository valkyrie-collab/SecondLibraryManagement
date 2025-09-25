package com.valkyrie.transaction.model;

import java.util.List;

public class UsersDTO {
    private String id;
    private String name;
    private String email;
    private long number;
    private List<BookDTO> bookDTOs;

    public String getId() {return id;}

    public UsersDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {return name;}

    public UsersDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {return email;}

    public UsersDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getNumber() {return number;}

    public UsersDTO setNumber(long number) {
        this.number = number;
        return this;
    }

    public List<BookDTO> getBookDTOs() {return bookDTOs;}

    public UsersDTO setBookDTOs(List<BookDTO> bookDTOs) {
        this.bookDTOs = bookDTOs;
        return this;
    }
    
}
