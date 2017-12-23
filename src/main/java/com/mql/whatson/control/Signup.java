package com.mql.whatson.control;

import com.mql.whatson.entity.RegisterationRequest;
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
 * @author Mehdi Maick
 */
@WebServlet(name = "Signup", urlPatterns = {"/signup"})
public class Signup extends HttpServlet {

    @Inject
    AuthService authService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterationRequest registerationRequest = createRegistrationRequest(request);
        boolean registered = authService.registerUser(registerationRequest);
        if (registered) {
            HttpSession session = request.getSession(true);
            session.setAttribute("isConnected", true);
            session.setAttribute("email", registerationRequest.getEmail());
            response.sendRedirect("/whatson/home");
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/signup.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/signup.jsp").forward(request, response);
    }

    private RegisterationRequest createRegistrationRequest(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        return new RegisterationRequest(firstName, lastName, email, password);
    }
}
