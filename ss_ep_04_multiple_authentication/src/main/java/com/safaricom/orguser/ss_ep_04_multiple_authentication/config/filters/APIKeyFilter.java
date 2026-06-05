package com.safaricom.orguser.ss_ep_04_multiple_authentication.config.filters;


import com.safaricom.orguser.ss_ep_04_multiple_authentication.config.authentication.APIKeyAuthentication;
import com.safaricom.orguser.ss_ep_04_multiple_authentication.config.manager.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//this is my custom auth fil;ter that i will use together with the username and password filter.
//Basic
//My Filter Calls in My Custom Authentication Manager to Authenticate

@AllArgsConstructor
public class APIKeyFilter extends OncePerRequestFilter {

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //if the apiKey is null or empty it should call the basic auth filter
            //from my first filter, is where i know the current authentication and delegate

            var requestKey = request.getHeader("x-api-key");

            if("null".equals(requestKey) || requestKey == null){
                filterChain.doFilter(request, response);

            }
            //My Manager needs to take in an Instance of My Unauthenticated Authentication, then Authenticate
            //To update the authentication to a final value, authenticated or Not
            CustomAuthenticationManager manager = new CustomAuthenticationManager(key);
            APIKeyAuthentication auth = new APIKeyAuthentication(requestKey);

            Authentication a = manager.authenticate(auth);

            //If the authentication is successful place the object in the context
            if (a.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(a);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
