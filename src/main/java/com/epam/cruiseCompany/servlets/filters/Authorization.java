package com.epam.cruiseCompany.servlets.filters;

import com.epam.cruiseCompany.dao.connection.ConnectionPoolHolder;
import com.epam.cruiseCompany.dao.factory.impl.MySqlDaoFactory;
import com.epam.cruiseCompany.dao.impl.AbstractDao;
import com.epam.cruiseCompany.dao.impl.ClientDao;
import com.epam.cruiseCompany.model.entity.people.Client;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

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
        AbstractDao<Client> clientDao = MySqlDaoFactory.getInstance().createClientDao(new ConnectionPoolHolder().getConnection());

        HttpSession session = req.getSession();

        if (session != null && session.getAttribute("Email") != null && session.getAttribute("Password") != null) {
            // moveToMenu(req, res);
        }
    }
    @Override
    public void destroy() {

    }
}
