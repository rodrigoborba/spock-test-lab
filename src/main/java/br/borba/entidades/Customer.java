package br.borba.entidades;

import java.time.LocalDate;

public class Customer {

    private long id;

    private String firstName;

    private String lastName;

    private LocalDate since;

    private String fullName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
    }

    public String getFullName() {
        if(null == fullName){
            fullName = firstName + " " + lastName;
        }
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getSince() {
        return since;
    }

    public void setSince(LocalDate since) {
        this.since = since;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
