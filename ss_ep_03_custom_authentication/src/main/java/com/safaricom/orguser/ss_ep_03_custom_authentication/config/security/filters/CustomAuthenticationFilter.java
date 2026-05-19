package com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.filters;

import com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.authentication.CustomAuthentication;
import com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//Todo
/*
* It is not a guarantee that a typical filter will be called only once.
* For a filter we want to be only called once we should use the onceperrequest filter to write our filter implementation
* */
@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        //pseudocode
        /*
        * 1.Create an Authentication Object which is not yet Authenticated.
        * 2.Delegate the authentication Object to the Manager.
        * 3.Get Back the Authentication from the Manager
        * 4.If the object is authenticated,send the request to the next filter in the chain.
        */
        //propagate the request to the next filter only when the authentication works.

        var key = String.valueOf(request.getHeader("key"));
        CustomAuthentication customAuthentication = new CustomAuthentication(false, key);
        var a = customAuthenticationManager.authenticate(customAuthentication);

        if(a.isAuthenticated()){

            //on successful authentication i need to set the authentication object in the context.
            //Authorization relies on the authentication object
            SecurityContextHolder.getContext().setAuthentication(a);
            filterChain.doFilter(request,response);

        }
    }
}
