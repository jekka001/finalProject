package com.epam.cruiseCompany.servlets.servlet.user;

import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.service.workWithTables.SelectionTable;
import com.epam.cruiseCompany.service.workWithTables.UserCruiseTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserMenu")
public class UserMenu extends HttpServlet {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private String menuItem;
    private int userId;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;

        getRequestData();
        moveToMenu();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        menuItem = "Profile";
        this.req = req;
        this.resp = resp;
        moveToMenu();
    }

    private void moveToMenu() throws ServletException, IOException {
        switch(menuItem){
            case "My cruise":
                setDataForUserCruiseTable();
                req.getRequestDispatcher("/WEB-INF/view/userCruise.jsp").forward(req, resp);
                break;
            case "Choose a cruise":
                setDataForCruiseTable();
                req.getRequestDispatcher("/WEB-INF/view/chooseCruise.jsp").forward(req, resp);
                break;
            case "My bonus":
                req.getRequestDispatcher("/WEB-INF/view/bonus.jsp").forward(req, resp);
                break;
            case "Sign out":
                req.getRequestDispatcher("Logout").forward(req, resp);
                break;
                default:
                    req.getRequestDispatcher("/WEB-INF/view/userProfile.jsp").forward(req, resp);
        }
    }
    private void getRequestData(){
        menuItem = req.getParameter("UserMenu");
        userId = ((User) req.getSession().getAttribute("User")).getId();
    }
    private void setDataForUserCruiseTable(){
        UserCruiseTable userCruiseTable = new UserCruiseTable();
        List<UserCruiseTable> userCruises = userCruiseTable.getUserCruise(userId);
        req.setAttribute("tickets", userCruises);
        userCruiseTable.closeConnection();
    }
    private void setDataForCruiseTable(){
        SelectionTable cruiseTable = new SelectionTable();
        List<SelectionTable> cruiseTables = cruiseTable.getCruise();
        req.setAttribute("tickets", cruiseTables);
        cruiseTable.closeConnection();
    }
}
