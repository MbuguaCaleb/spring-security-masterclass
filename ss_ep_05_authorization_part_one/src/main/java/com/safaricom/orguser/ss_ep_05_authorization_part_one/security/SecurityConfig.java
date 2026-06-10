package com.safaricom.orguser.ss_ep_05_authorization_part_one.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    //Bean that is run during Application startUp to do my security configurations
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        //endpoint level authorization
        /*used for web applications
         (a).anyRequest().authenticated(), for this one all the resources will be accessed as long as the user is authenticated.
            matcher method + authorization rule
          //1.Which matcher method i should use and how?
            2.How to apply different authorization rules.

          The only way you will get rejected is if you did not authenticate at all.

         (b)authorize.anyRequest().permitAll() // rule 2 (permits all, but when you decide to add a password that is wrong, it will not authenticate
           when you try to use a wrong password, the authentication filter will fail, wow.
           wrong auth will still reject,
           authorization is always after authorization

          (c)authorize.anyRequest().hasAuthority("read")
            Only when you have the authority read, you can access any endpoint.

          (d)  authorize.anyRequest().hasAnyAuthority("read","write") enumerates authorities

        Roles relate to a group of actions or permissions

          (e) configurations with the Spring Expression Language.
           authorize.anyRequest().access(new WebExpressionAuthorizationManager("isAuthenticated() and hasAuthority('read')") ))//SPEL----->authorization rules )

        todo  we should be careful when we use spring expression because it is much more difficult to debug
        */
        return http
                .authorizeHttpRequests((authorize) ->
                        authorize
                        .requestMatchers("/demo").hasAuthority("read")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .build();
    }

    //this is an interface
    //our user can be gotten from anywhere as long as it conforms with the structure.
    @Bean
    public UserDetailsService userDetailsService() {

        var uds = new InMemoryUserDetailsManager();

        //How i add permissions y
        var u1 = User
                .withUsername("caleb")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        var u2 = User
                .withUsername("mercy")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();


        //How to define roles
        //todo (roles are case sensitive)
//        var u1 = User
//                .withUsername("caleb")
//                .password(passwordEncoder().encode("12345"))
//                .authorities("ROLE_ADMIN")
//                .build();
//
//        var u2 = User
//                .withUsername("mercy")
//                .password(passwordEncoder().encode("12345"))
//                .authorities("ROLE_MANAGER")
//                .build();
//
//        var u3 = User
//                .withUsername("milka")
//                .password(passwordEncoder().encode("12345"))
//                .roles("ADMIN")
//                .build();

        uds.createUser(u1);
        uds.createUser(u2);

        return uds;
    }


    //this bean is used to encode my passwords
    //and also decode it for the pattern matching function
    //A has cannot be revefrsed but we do patterm marching
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
