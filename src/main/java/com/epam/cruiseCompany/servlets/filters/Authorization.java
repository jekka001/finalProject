package com.epam.cruiseCompany.servlets.filters;

import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.service.singIn.ServiceUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/Authorization")
public class Authorization implements Filter {
    private HttpServletRequest req;
    private HttpServletResponse res;
    private String login;
    private String password;
    private HttpSession session;
    private User user;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        req = (HttpServletRequest) servletRequest;
        res = (HttpServletResponse) servletResponse;

        getRequestData();
        getUserData();


        if (checkUserSession()) {
            user = (User)session.getAttribute("User");
            moveToMenu();
        }else if(isExistUser()){
            req.getSession().setAttribute("User", user);
            moveToMenu();
        } else{
            req.setAttribute("wrong", "User does not have a database");
            req.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, res);
        }
    }
    private void getRequestData(){
        login = req.getParameter("Email");
        password = req.getParameter("Password");
        session = req.getSession();
    }
    private void getUserData(){
        ServiceUser serviceUser = new ServiceUser();
        user = serviceUser.getUser(login, password);
        serviceUser.closeConnection();
    }
    private boolean checkUserSession(){
        return session != null && session.getAttribute("User") != null;
    }
    private boolean isExistUser(){
        return user != null;
    }
    private void moveToMenu() throws ServletException, IOException {
        switch (user.getRole()){
            case ADMIN:
                req.getRequestDispatcher("/WEB-INF/view/adminMenu.jsp").forward(req, res);
                break;
            case CLIENT:
                req.getRequestDispatcher("UserMenu").forward(req, res);

        }
    }
    @Override
    public void destroy() {

    }
}
