package com.mql.whatson.control.auth.social;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.mql.whatson.entity.RegisterationRequest;
import com.mql.whatson.entity.User;
import com.mql.whatson.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Mehdi Maick
 */
@WebServlet(name = "FacebookCallback", urlPatterns = "/facebook-callback", asyncSupported = true)
public class CallbackFacebook extends HttpServlet {

    @Inject
    AuthService authSerice;

    Logger log = LoggerFactory.getLogger(CallbackFacebook.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        //if (checkUserPermissionError(req, resp)) return;
        //OK the user have consented so lets find out about the user
        authenticateUser(req, res);
    }

    private void authenticateUser(HttpServletRequest req, HttpServletResponse res) {
        String FACEBOOK_LOGIN_URL = req.getServletContext().getInitParameter("facebook.login.path");
        HttpSession session = req.getSession();
        OAuth20Service service = (OAuth20Service) session.getAttribute("oauth2Service");
        //Get the all important authorization code
        String code = req.getParameter("code");
        //Construct the access token
        try {
            final OAuth2AccessToken accessToken = service.getAccessToken(code);
            final OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_LOGIN_URL);
            service.signRequest(accessToken, request);
            final Response serviceResponse = service.execute(request);
            JsonReader reader = Json.createReader(new ByteArrayInputStream(serviceResponse.getBody().getBytes()));
            JsonObject profile = reader.readObject();
            // TODO: call user factory to fabricate user object and test if it's login or register request
            String[] nameToken = profile.getString("name").split("\\s+");
            String firstName = nameToken[0];
            String lastName = nameToken[1];
            String picUrl = profile.getJsonObject("picture").getJsonObject("data").getString("url");
            String email = profile.getString("email");
            System.out.println("picUrl is " + picUrl);
            System.out.println("email is " + email);
            User user = authSerice.registerUserFacebook(new RegisterationRequest(firstName, lastName, email, picUrl, 1));
            setSessionAttributes(req, user);
            System.out.println("user is " + user);
            System.out.println("facebook " + serviceResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.sendRedirect("/whatson/home");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: refactor this
    private void setSessionAttributes(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute("username", user.getEmail());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        session.setAttribute("isConnected", true);
    }
}