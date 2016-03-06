package com.volodymyr.notecase.util;

import com.google.api.client.http.HttpStatusCodes;
import com.volodymyr.notecase.entity.User;
import com.volodymyr.notecase.manager.UserManager;
import com.volodymyr.notecase.manager.UserManagerImpl;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by volodymyr on 04.03.16.
 */
public class RestAuthenticationFilter implements Filter{
    private static Logger log = Logger.getLogger(RestAuthenticationFilter.class.getName());
    private static final String AUTHENTICATION_TOKEN = "AuthToken";
    private static final String URL_FOR_AUTHENTICATION = "/rest/user/authenticate";

    private UserManager userManager = new UserManagerImpl();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().contains(URL_FOR_AUTHENTICATION) && request.getMethod().equals("POST")){
            log.info("Allowed " + URL_FOR_AUTHENTICATION + " url without AuthToken for user authentication (idToken required)");
            chain.doFilter(request, response);
        }

        String clientAuthToken = request.getHeader(AUTHENTICATION_TOKEN);
        User existingUser = userManager.getUserByAuthToken(clientAuthToken);
        if (existingUser == null){
            log.warn("Cannot authenticate user with authToken: " +clientAuthToken);
            response.setStatus(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
        }else {
            log.debug("User authenticated. Email: " + existingUser.getEmail() + ", Name: " + existingUser.getName());
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
