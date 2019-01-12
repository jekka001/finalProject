package com.epam.cruiseCompany.servlets.servlet.user.menu;

import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.service.singIn.ServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Change")
public class Change extends HttpServlet {
    private User user;
    private String name;
    private String surname;
    private String email;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;

        getRequestData();
        uploadUser();
        setRequestData();

        req.getRequestDispatcher("/WEB-INF/view/userProfile.jsp").forward(req, resp);
    }

    private void getRequestData(){
        user = (User)req.getSession().getAttribute("User");
        name = req.getParameter("Name");
        surname = req.getParameter("Surname");
        email = req.getParameter("Email");
    }

    private void uploadUser(){
        ServiceUser serviceUser = new ServiceUser();
        changeUser();
        serviceUser.updateUser(user);
        serviceUser.closeConnection();
    }

    private void changeUser(){
        changeName();
        changeSurname();
        changeEmail();
    }
    private void changeName(){
        if(checkFieldWithName()){
            user.setName(name);
        }
    }
    private boolean checkFieldWithName(){
        return name.trim().length() != 0;
    }

    private void changeSurname(){
        if(checkFieldWithSurname()){
            user.setSurname(surname);
        }
    }
    private boolean checkFieldWithSurname(){
        return surname.trim().length() != 0;
    }

    private void changeEmail(){
        if(checkFieldWithEmail()) {
            user.setEmail(email);
        }
    }
    private void setRequestData(){
        if(!checkFieldWithEmail()){
            req.setAttribute("Busy", "Email busy, please enter another one");
        }
    }
    private boolean checkFieldWithEmail(){
        return email.trim().length() != 0;
    }

}
