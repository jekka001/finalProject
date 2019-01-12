package com.epam.cruiseCompany.model.entity.people;


import com.epam.cruiseCompany.model.entity.ticket.CruiseTicket;
import com.epam.cruiseCompany.model.entity.ticket.ExcursionTicket;

import java.util.List;

public class User extends Person{
    private String email;
    private String password;
    private Role role;

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
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }


    public User() {
        this( "noEmail", "noPassword", Role.NO_ROLE);
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int id, String name, String surname, String email, String password, Role role) {
        super(id, name, surname);
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
