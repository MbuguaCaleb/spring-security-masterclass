package com.safaricom.orguser.ss_ep_04_multiple_authentication.config;

import com.safaricom.orguser.ss_ep_04_multiple_authentication.config.filters.APIKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Value("${the.secret}")
    private String key;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        /*
        HttpSecurity Object is the one that defines the entire security configs
        for Our Application.
        When our applications are starting, they normally use this configuration

        The below creates an entire Http Basic configuration.
        (a)When HttpBasic is called, it creates a configurer.
        (b)When the application starts, it creates the Filter, Auth manager, provider, etc..
        (c)Anything we add here helps configure something in the entire architecture of Spring Security.
        */

        /*
        How do we override an authentication manager?
        {By design we should have a single authentication manager}
        (a) using .authenticationManager() or adding a Bean with type authenticationManager in the context
        (b) using .authenticationProvider () does not override the AP,it adds one more to the collection.

        */
        return http
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new APIKeyFilter(key), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .authenticationManager()
//                .authenticationProvider() it does
                .build();
    }


}
