package com.example.gordan.addpeople;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by gordan on 10/24/15.
 */
public class PersonModel{
    private UUID id;
    private String name;
    private String surname;
    private Date dateAdded;

    public PersonModel(String name, String surname) {
        id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        dateAdded = Calendar.getInstance().getTime();
    }

    public UUID getID() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

}
