package com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//Todo
/*
* It is not a guarantee that a typical filter will be called only once.
* For a filter we want to be only called once we should use the onceperrequest filter to write our filter implementation
* */
@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        //propagate the request to the next filter only when the authentication works.
        filterChain.doFilter(request,response);
    }
}
