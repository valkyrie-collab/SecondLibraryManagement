package com.valkyrie.entity.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "entity")
public class Users {
    @Id
    private String id;
    private String name;
    private String email;
    private long number;
    @ElementCollection
    private List<String> bookIds;

    public String getId() {return id;}

    public Users setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {return name;}

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {return email;}

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getNumber() {return number;}

    public Users setNumber(long number) {
        this.number = number;
        return this;
    }

    public List<String> getBookIds() {return bookIds;}

    public Users setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
        return this;
    }

    @Override
    public String toString() {
        return id + name + email + number;
    }

}
