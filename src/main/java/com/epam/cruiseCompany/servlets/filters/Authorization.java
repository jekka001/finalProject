package com.epam.cruiseCompany.servlets.filters;

import com.epam.cruiseCompany.model.entity.people.Role;
import com.epam.cruiseCompany.model.entity.people.User;
import com.epam.cruiseCompany.service.ServiceUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/Authorization")
public class Authorization implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String login = req.getParameter("Email");
        String password = req.getParameter("Password");
        HttpSession session = req.getSession();

        ServiceUser serviceUser = new ServiceUser();
        User user = serviceUser.isEmpty(login, password);
        serviceUser.closeConnection();

        if (session != null && session.getAttribute("Email") != null && session.getAttribute("Password") != null) {
            Role role = (Role)session.getAttribute("Role");

            moveToMenu(req, res, role);
        }else if(user != null){
            Role role = user.getRole();
            req.getSession().setAttribute("Email", login);
            req.getSession().setAttribute("Password", password);
            req.getSession().setAttribute("Role", role);

            moveToMenu(req, res, role);
        } else{
            moveToMenu(req, res, Role.NO_ROLE);
        }
    }

    private void moveToMenu(HttpServletRequest req, HttpServletResponse res, Role role) throws ServletException, IOException {
        if(role.equals(Role.ADMIN)){
            req.getRequestDispatcher("/WEB-INF/view/adminMenu.jsp").forward(req, res);
        }else if(role.equals(Role.CLIENT)){
            req.getRequestDispatcher("/WEB-INF/view/userProfile.jsp").forward(req, res);
        }else{
            req.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
