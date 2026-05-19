package com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.managers;


import com.safaricom.orguser.ss_ep_03_custom_authentication.config.security.providers.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final CustomAuthenticationProvider provider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //an authentication must be supported by a Provider
        //todo(if we had may providers we would have iterated through many then throw and exception at the end
        if(provider.supports(authentication.getClass())){
            return provider.authenticate(authentication);
        }
        throw new BadCredentialsException("Oh No!");
    }

}
