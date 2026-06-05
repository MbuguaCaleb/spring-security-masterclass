package com.safaricom.orguser.ss_ep_04_multiple_authentication.config.manager;

import com.safaricom.orguser.ss_ep_04_multiple_authentication.providers.ApiKeyProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final String key;

    //for InBuild authentications, eg OAUTH2.0, ETC
    //We have one manager and based on the authtype, routes the reqiuest to the rigjht provider
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var provider = new ApiKeyProvider(key);
        if (provider.supports(authentication.getClass())) {
            return provider.authenticate(authentication);
        }

        return authentication;
    }
}
