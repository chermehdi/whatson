package com.mql.whatson.control.auth.social;

import com.github.scribejava.apis.FacebookApi;
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
@WebServlet(name = "FacebookLogin", urlPatterns = "/facebook")
public class FacebookLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = getServletContext();

        final String CLIENT_ID = context.getInitParameter("facebook.client.id");
        final String CLIENT_SECRET = context.getInitParameter("facebook.client.secret");
        final String CALLBACK_URL = context.getInitParameter("facebook.callback.url");
        final String STATE = "secret" + new BigInteger(31, new SecureRandom()).toString();
        //Configure
        final OAuth20Service service = new ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .state(STATE)
                .scope("email")
                .callback(CALLBACK_URL)
                .build(FacebookApi.instance());
        HttpSession session = request.getSession();
        // the object is stored in the session to be retrieved by the callback url
        session.setAttribute("oauth2Service", service);
        System.out.println("this is the refrech token " + CLIENT_SECRET);
        String url = service.getAuthorizationUrl(null);
        System.out.println("the redirect url is " + url);
        response.sendRedirect(url);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
    }
}
