package com.mql.whatson.control.auth;

import com.mql.whatson.entity.User;
import com.mql.whatson.service.AuthService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class SimpleLogin
 */
@SuppressWarnings("serial")
@WebServlet("/login")
public class SimpleLogin extends HttpServlet {

    @Inject
    AuthService authService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User u = null;
        if ((u = authService.authenticateUser(email, password)) != null) {
            setSessionAttributes(request, u);
            response.sendRedirect("/whatson/home");
        } else {
            // TODO: should add flash message
            response.sendRedirect("/whatson/login");
        }
    }

    private void setSessionAttributes(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute("username", user.getEmail());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        session.setAttribute("isConnected", true);
    }
}
