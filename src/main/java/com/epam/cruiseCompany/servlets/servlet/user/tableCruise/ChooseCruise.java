package com.epam.cruiseCompany.servlets.servlet.user.tableCruise;

import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.service.workWithTables.SelectionTable;
import com.epam.cruiseCompany.service.workWithTables.UserCruiseTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ChooseCruise")
public class ChooseCruise extends HttpServlet{
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private int ticketId;
    private int userId;
    private String nameCruise;
    private String operationWithTable;
    private SelectionTable selectionTable;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        getRequestData();
        moveToMenu();
    }
    private void getRequestData(){
        userId = ((User)req.getSession().getAttribute("User")).getId();
        nameCruise = isExistNameCruise();
        ticketId = isExistTicketId();
        operationWithTable = req.getParameter("Operation");
    }
    private int isExistTicketId(){
        if(isExistPressingButton()) {
            return Integer.valueOf(req.getParameter("TicketId"));
        }
        return 1;
    }
    private boolean isExistPressingButton(){
        return req.getParameter("TicketId") != null && req.getParameter("TicketId").trim().length() != 0;
    }
    private String isExistNameCruise(){
        if(isExistFieldFind()){
            return req.getParameter("OperationText");
        }
        return "";
    }
    private boolean isExistFieldFind(){
        return req.getParameter("OperationText") != null && req.getParameter("OperationText").trim().length() != 0;
    }
    private void moveToMenu() throws ServletException, IOException {
        selectionTable = new SelectionTable();
        switch (operationWithTable) {
            case "Buy":
                buyTicket();
                updateTable();
                req.getRequestDispatcher("/WEB-INF/view/chooseCruise.jsp").forward(req, resp);
                break;
            case "Previous 10":
                previousDateForTable();
                updateTable();
                req.getRequestDispatcher("/WEB-INF/view/chooseCruise.jsp").forward(req, resp);
                break;
            case "Find":
                findTicket();
                req.getRequestDispatcher("/WEB-INF/view/chooseCruise.jsp").forward(req, resp);
                break;
            case "Next 10":
                nextDateForTable();
                updateTable();
                req.getRequestDispatcher("/WEB-INF/view/chooseCruise.jsp").forward(req, resp);
                break;
            default:
                closeConnection();
                req.getRequestDispatcher("/WEB-INF/view/chooseCruise.jsp").forward(req, resp);

        }
    }

    private void buyTicket(){
        selectionTable.buyTicket(ticketId, userId);
    }
    private void previousDateForTable(){
        selectionTable.decreaseCounterFirstTicket();
    }
    private void nextDateForTable(){
        selectionTable.increaseCounterFirstTicket();
    }
    private void closeConnection(){
        selectionTable.closeConnection();
    }
    private void updateTable(){
        List<SelectionTable> userCruises = selectionTable.getCruise();
        req.setAttribute("tickets", userCruises);
        closeConnection();
    }
    private void findTicket(){
        List<SelectionTable> cruise;
        if(isExistUserCruise()) {
            cruise = selectionTable.getFoundCruise(nameCruise);
            req.setAttribute("tickets", cruise);
        }
        closeConnection();
    }
    private boolean isExistUserCruise(){
        return !selectionTable.getFoundCruise(nameCruise).isEmpty();
    }
}
