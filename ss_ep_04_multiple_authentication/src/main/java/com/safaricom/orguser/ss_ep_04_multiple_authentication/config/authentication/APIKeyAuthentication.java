package com.safaricom.orguser.ss_ep_04_multiple_authentication.config.authentication;


import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

//Even My Custom Authentication must implement the Spring Security authentication structure
//An authentication is what my manager will get after calling the provider in the end.
//then based on the result of the authentication, store in the context or return invalid
@RequiredArgsConstructor
public class APIKeyAuthentication implements Authentication {

    private final String key;
    private boolean authenticated;

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getKey() {
        return key;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
   this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getDetails() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return null;
    }


    @Override
    public String getName() {
        return "";
    }
}
