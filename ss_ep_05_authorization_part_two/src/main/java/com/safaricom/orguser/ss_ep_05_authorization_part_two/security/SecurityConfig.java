package com.safaricom.orguser.ss_ep_05_authorization_part_two.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    /*
    (a) the minute/ lower level rule overrides the higher level one
    (b) "/api/v1/** (these expressions are called Java Ant Expressions
    (c)/demo/anything/start/somehting
    (d) Never Disable CSRF on a Production APPLICATION
    (e)With regex matchers they are very powerful and we can be able to match any type of String
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        return httpSecurity
                .httpBasic(withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAuthority("read")
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAuthority("write")
                        .requestMatchers("/test3").permitAll()
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/v1/test[A-Za-z0-9]+")).hasAuthority("update")

                )
                .csrf(AbstractHttpConfigurer::disable) //do not do this on PROD, it is just for my POST Request to Work
                .build();
    }


    @Bean
    public UserDetailsService userDetailsService(){

        InMemoryUserDetailsManager user = new InMemoryUserDetailsManager();

        //How i add permissions y
        var u1 = User
                .withUsername("Caleb")
                .password(passwordEncoder().encode("12345"))
                .authorities("read", "delete")
                .build();

        var u2 = User
                .withUsername("mercy")
                .password(passwordEncoder().encode("12345"))
                .authorities("write", "update")
                .build();

        user.createUser(u1);
        user.createUser(u2);

        return user;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }
}
