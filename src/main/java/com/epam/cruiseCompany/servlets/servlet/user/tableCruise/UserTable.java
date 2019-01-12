package com.epam.cruiseCompany.servlets.servlet.user.tableCruise;


import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.service.workWithTables.UserCruiseTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UserTable")
public class UserTable extends HttpServlet {
    private String operationWithTable;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private int userId;
    private int ticketId;
    private UserCruiseTable userCruiseTable;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        getRequestData();
        moveToMenu();
    }

    private void getRequestData(){
        userId = ((User)req.getSession().getAttribute("User")).getId();
        ticketId = isExistTicketId();
        operationWithTable = req.getParameter("Operation");
    }
    private int isExistTicketId(){
        if(isExistFieldFind()){
            return Integer.valueOf(req.getParameter("OperationText"));
        }else if(isExistPressingButton()) {
            return Integer.valueOf(req.getParameter("TicketId"));
        }
        return 1;
    }
    private boolean isExistFieldFind(){
        return req.getParameter("OperationText") != null && req.getParameter("OperationText").trim().length() != 0;
    }
    private boolean isExistPressingButton(){
        return req.getParameter("TicketId") != null && req.getParameter("TicketId").trim().length() != 0;
    }
    private void moveToMenu() throws ServletException, IOException {
        userCruiseTable = new UserCruiseTable();
        switch (operationWithTable) {
            case "Refuse":
                refuseTicket();
                updateTable();
                req.getRequestDispatcher("/WEB-INF/view/userCruise.jsp").forward(req, resp);
                break;
            case "Previous 10":
                previousDateForTable();
                updateTable();
                req.getRequestDispatcher("/WEB-INF/view/userCruise.jsp").forward(req, resp);
                break;
            case "Find":
                findTicket();
                req.getRequestDispatcher("/WEB-INF/view/userCruise.jsp").forward(req, resp);
                break;
            case "Next 10":
                nextDateForTable();
                updateTable();
                req.getRequestDispatcher("/WEB-INF/view/userCruise.jsp").forward(req, resp);
                break;
            default:
                closeConnection();
                req.getRequestDispatcher("/WEB-INF/view/userCruise.jsp").forward(req, resp);

        }
    }
    private void refuseTicket(){
        userCruiseTable.returnPurchasedTicket(ticketId);
    }
    private void previousDateForTable(){
        userCruiseTable.decreaseCounterFirstTicket();
    }
    private void nextDateForTable(){
        userCruiseTable.increaseCounterFirstTicket();
    }
    private void closeConnection(){
        userCruiseTable.closeConnection();
    }
    private void updateTable(){
        List<UserCruiseTable> userCruises = userCruiseTable.getUserCruise(userId);
        req.setAttribute("tickets", userCruises);
        closeConnection();
    }
    private void findTicket(){
        List<UserCruiseTable> userCruise = new ArrayList<>();
        if(isExistUserCruise()) {
            userCruise.add(userCruiseTable.getFoundUserCruise(userId, ticketId));
            req.setAttribute("tickets", userCruise);
        }
        closeConnection();
    }
    private boolean isExistUserCruise(){
        return userCruiseTable.getFoundUserCruise(userId, ticketId) != null;
    }
}
