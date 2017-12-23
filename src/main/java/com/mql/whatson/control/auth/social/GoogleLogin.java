package com.mql.whatson.control.auth.social;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Mehdi Maick
 */
@WebServlet(name = "GoogleLogin", urlPatterns = "/google")
public class GoogleLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = getServletContext();

        final String CLIENT_ID = context.getInitParameter("google.client.id");
        final String CLIENT_SECRET = context.getInitParameter("google.client.secret");
        final String CALLBACK_URL = context.getInitParameter("google.callback.url");
        final String STATE = "secret" + new BigInteger(31, new SecureRandom()).toString();
        //Configure
        final OAuth20Service service = new ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .scope("profile email")
                .state(STATE)
                .callback(CALLBACK_URL)
                .build(GoogleApi20.instance());
        HttpSession session = request.getSession();
        // the object is stored in the session to be retrieved by the callback url
        session.setAttribute("oauth2Service", service);
        response.sendRedirect(service.getAuthorizationUrl(null));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }
}
