package com.epam.cruiseCompany.servlets.servlet.main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/MainMenu")
public class MainMenu extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String menuItem = req.getParameter("Menu");
        moveToMenu(req, resp, menuItem);
    }

    private void moveToMenu(HttpServletRequest req, HttpServletResponse resp, String menuItem) throws ServletException, IOException {
        if(menuItem != null && menuItem.equals("Register")){
            req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
        }
    }
}