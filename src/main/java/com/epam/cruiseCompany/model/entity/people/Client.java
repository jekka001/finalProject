package com.epam.cruiseCompany.model.entity.people;


public class Client extends Person{
    private String email;
    private String password;


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Client() {
        this("noEmail","noPassword");
    }

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Client(int id, String name, String surName, String email, String password) {
        super(id, name, surName);
        this.email = email;
        this.password = password;
    }
}
