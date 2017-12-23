package com.mql.whatson.control.auth.social;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.mql.whatson.entity.RegisterationRequest;
import com.mql.whatson.entity.User;
import com.mql.whatson.service.AuthService;

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
@WebServlet(name = "CallbackGoogle", urlPatterns = "/google-callback")
public class CallbackGoogle extends HttpServlet {

    @Inject
    AuthService authService;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        authenticateUser(req, resp);
    }

    private boolean checkUserPermissionError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String error = req.getParameter("error");
        if ((null != error) && ("access_denied".equals(error.trim()))) {
            HttpSession session = req.getSession();
            session.invalidate();
            resp.sendRedirect(req.getContextPath());
            return true;
        }
        return false;
    }

    private void authenticateUser(HttpServletRequest req, HttpServletResponse res) {
        String GOOGLE_LOGIN_URL = req.getServletContext().getInitParameter("google.login.path");
        HttpSession session = req.getSession();
        OAuth20Service service = (OAuth20Service) session.getAttribute("oauth2Service");
        //Get the all important authorization code
        String code = req.getParameter("code");
        //Construct the access token
        try {
            final OAuth2AccessToken accessToken = service.getAccessToken(code);
            final OAuthRequest request = new OAuthRequest(Verb.GET, GOOGLE_LOGIN_URL);
            service.signRequest(accessToken, request);
            final Response serviceResponse = service.execute(request);
            JsonReader reader = Json.createReader(new ByteArrayInputStream(serviceResponse.getBody().getBytes()));
            JsonObject profile = reader.readObject();
            // TODO: call user factory to fabricate user object and test if it's login or register request
            System.out.println(profile);
            String email = profile.getJsonArray("emails").getJsonObject(0).getString("value");
            JsonObject nameObject = profile.getJsonObject("name");
            String firstName = nameObject.getString("givenName");
            String lastName = nameObject.getString("familyName");
            String picUrl = profile.getJsonObject("image").getString("url");
            User user = authService.registerUserGoogle(new RegisterationRequest(firstName, lastName, email, picUrl, 0));
            System.out.println("the user created is " + user);
            setSessionAttributes(req, user);
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