package com.safaricom.orguser.ss_ep_04_multiple_authentication.providers;

import com.safaricom.orguser.ss_ep_04_multiple_authentication.config.authentication.APIKeyAuthentication;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@AllArgsConstructor
public class ApiKeyProvider implements AuthenticationProvider {

    private final String key;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

        APIKeyAuthentication auth = (APIKeyAuthentication) authentication;
        if(key.equals(auth.getKey())){
            auth.setAuthenticated(true);
            return auth;
        }
        throw new BadCredentialsException("ooops");
    }

    //the supports identify the authentication type.
    //if i have many providers the right one will be called based on the supports type
    @Override
    public boolean supports(Class<?> authentication) {
        return APIKeyAuthentication.class.equals(authentication);
    }
}
