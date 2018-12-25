package com.epam.cruiseCompany.servlets.servlet;

import com.epam.cruiseCompany.dao.factory.DaoFactory;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.AbstractDao;
import com.epam.cruiseCompany.model.entity.people.Client;
import com.epam.cruiseCompany.service.ServiceClient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/SignIn")
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        authentication(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        perFormTask(request,response);
    }

    private void perFormTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        switch (request.getParameter("Menu")){
            case "Sign in":
                dispatcher = request.getRequestDispatcher("/view/main.jsp");
                dispatcher.forward(request, response);
                break;
            case "Register":
                dispatcher = request.getRequestDispatcher("/view/register.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    private void authentication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String userLogin = request.getParameter("Email");
        String userPassword = request.getParameter("Password");
        Client client = new ServiceClient().isEmpty(userLogin,userPassword);
        if(client != null) {
            request.getSession().setAttribute("user", client);
            response.sendRedirect(request.getContextPath() + "/userProfile");
        }else{
            request.setAttribute("error", "Unknown login, try again");
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}
