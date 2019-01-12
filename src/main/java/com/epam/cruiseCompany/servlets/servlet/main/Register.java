package com.epam.cruiseCompany.servlets.servlet.main;

import com.epam.cruiseCompany.service.singIn.ServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Register")
public class Register extends HttpServlet {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String repeatPassword;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getRequestData(req);

        ServiceUser serviceUser = new ServiceUser();

        if(!equalsPasswordAndRepeatPassword()){
            setDataForRegistrationFields(req);
            req.setAttribute("Error", "Passwords do not match");
        }else if (!serviceUser.isExistEmail(email)) {
            serviceUser.makingUser(name, surname, email, password);
            serviceUser.closeConnection();
            resp.sendRedirect("/");
        } else{
            setDataForRegistrationFields(req);
            req.setAttribute("Error", "Email busy, please enter another one");
        }
        //req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
    }

    private void getRequestData(HttpServletRequest req){
        name = req.getParameter("NameRegister");
        surname = req.getParameter("SurnameRegister");
        email = req.getParameter("EmailRegister");
        password = req.getParameter("PasswordRegister");
        repeatPassword = req.getParameter("RepeatPasswordRegister");
    }
    private boolean equalsPasswordAndRepeatPassword(){
        return password.equals(repeatPassword);
    }
    private void setDataForRegistrationFields(HttpServletRequest req){
        req.setAttribute("NameRegister", name);
        req.setAttribute("SurnameRegister", surname);
        req.setAttribute("EmailRegister", email);
    }
}
