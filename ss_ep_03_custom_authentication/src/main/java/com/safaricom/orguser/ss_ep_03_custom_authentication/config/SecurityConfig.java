package com.safaricom.orguser.ss_ep_03_custom_authentication.config;


import com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.filters.CustomAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    //I am overriding the default implementation of the security filter chain
    //i am adding a new filter at the position where the UserNamePasswordFilter would have stayed
    //i am adding a custom authentication filter at the position of the other filter.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
       return http
               .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .build();

    }
}
