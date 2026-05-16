package com.safaricom.orguser.lessontwo.security;

import com.safaricom.orguser.lessontwo.entities.Authority;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;


//todo
/*
* Remember my custom DB authorities in DB must be mapped into granted authorities,
* which spring boot understands
*
* I will use the decorator pattern
*
* The below will return authorities in my DB to a granted authority object.
*
* */
@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final Authority authority;

    @Override
    public @Nullable String getAuthority() {
        return  authority.getName();
    }
}
