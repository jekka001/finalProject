package com.epam.cruiseCompany.servlets.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/Register")
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/register.jsp");
//        dispatcher.forward(request, response);
        PrintWriter pw = response.getWriter();
        pw.println(request.getParameter("NameRegist"));
        pw.println(request.getParameter("SurNameRegist"));
        pw.println(request.getParameter("EmailRegist"));
    }
}