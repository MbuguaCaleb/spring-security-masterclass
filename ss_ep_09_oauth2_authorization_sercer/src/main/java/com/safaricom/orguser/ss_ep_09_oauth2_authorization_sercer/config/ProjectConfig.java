package com.safaricom.orguser.ss_ep_09_oauth2_authorization_sercer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
public class ProjectConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/demo")
                .access(new WebExpressionAuthorizationManager("isAuthenticated()"));


        return http.build();
    }
}
