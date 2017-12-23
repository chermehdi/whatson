package com.mql.whatson.control.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * @author Mehdi Maick
 */
@WebFilter(filterName = "Auth", urlPatterns = {"/*"})
public class Auth implements Filter {

    private final String LOGOUT = "logout";
    private final String IS_CONNECTED = "isConnected";
    private final String LOGIN_PAGE = "login";
    private final String HOME = "home";
    Logger log = LoggerFactory.getLogger(getClass());
    private String[] urls = {LOGIN_PAGE, "logout", "login", "signup", "google", "facebook", "facebook-callback", "google-callback"};
    private List<String> UNPROTECTED_URLS = new Vector<>(Arrays.asList(urls));// TODO: replace with a concurrent version

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        boolean isLoggedIn = request.getSession() != null &&
                request.getSession().getAttribute(IS_CONNECTED) != null;

        String url = request.getRequestURL().toString();
        if (!isPublicAsset(url)) {
            if (isLoggedIn) {
                if (!isLogoutRequest(url)) {
                    if (!isUrlUnprotected(url)) {
                        response.sendRedirect(urlJoiner(getRoot(request), HOME));
                        return;
                    }
                }
            } else {
                if (isUrlUnprotected(url)) {
                    response.sendRedirect(urlJoiner(getRoot(request), LOGIN_PAGE));
                    return;
                }
            }
        } else {
            log.info("public asset requested " + url);
        }
        chain.doFilter(req, res);
    }

    private boolean isUrlUnprotected(String url) {
        return UNPROTECTED_URLS.stream().filter(url::endsWith).count() == 0;
    }

    private boolean isLogoutRequest(String url) {
        return url.endsWith(LOGOUT);
    }

    private String urlJoiner(String... urls) {
        char last = ' ';
        String all = String.join("/", urls);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < all.length(); i++) {
            if (all.charAt(i) == '/' && last == '/') continue;
            sb.append(all.charAt(i));
            last = all.charAt(i);
        }
        if (sb.charAt(0) != '/') sb.insert(0, '/');
        return sb.toString();
    }

    private boolean isPublicAsset(String url) {
        return url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".map") || isImage(url);
    }

    private boolean isImage(String url) {
        return url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".svg");
    }

    private String getRoot(HttpServletRequest request) {
        return "/whatson";
    }
}
