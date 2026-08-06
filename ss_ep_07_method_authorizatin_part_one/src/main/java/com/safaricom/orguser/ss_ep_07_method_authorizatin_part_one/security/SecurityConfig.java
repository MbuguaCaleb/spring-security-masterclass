package com.safaricom.orguser.ss_ep_07_method_authorizatin_part_one.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
//@PreAuthorize, @PostAuthorize, @PreFilter @PostFilter
//More Aspects we can Use:
//@Secured
//@RolesAllowed
public class SecurityConfig {

    //todo(notes)
    //i start with a security filter chain
    //when using method security to enforce authorization rules, all the requests must be authenticated
    //otherwise it will fail since it depends on the security context
    //when there is nothing in security context authorization will fail
    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) {
        return http.httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        requests -> requests.anyRequest().authenticated()
                ).build();
    }


    //there will be this user in the context
    //that can authorize with http basic and log in with their username and password
    @Bean
    protected UserDetailsService userDetailsService() {
        var userDetails = User.withUsername("caleb")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        var userDetailsTwo = User.withUsername("mercy")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(userDetails);
        inMemoryUserDetailsManager.createUser(userDetailsTwo);
        return inMemoryUserDetailsManager;
    }

    //this bean encrypts my password
    //and also helps in the decryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }


}
