package com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.providers;

import com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.authentication.CustomAuthentication;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Value("${our.very.very.very.secret.key}")
    private String key;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //My authentication provider is taking my custom authentication
        CustomAuthentication ca = (CustomAuthentication) authentication;
        var headerKey = ca.getKey();

        if(key.equals(headerKey)){
            return new CustomAuthentication(true, null);
        }

        throw new BadCredentialsException("Oh No!");

    }

    //if i have another authentication provider,this is what differences them
    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
