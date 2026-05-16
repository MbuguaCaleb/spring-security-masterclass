package com.safaricom.orguser.lessontwo.security;

import com.safaricom.orguser.lessontwo.entities.User;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//todo(How i convert my User DB Object into a Spring Security User??
    /*wow,
    (a)the best way to convert my User Object into a Spring User is the adapter
    decorator pattern,
    (b)I can also use an object mapper-second best
    (c) My User class can implement the Spring UserDetails
    [Poor design wise, because now my class has more than one responsibility]
     */

//Decorator pattern class, what is returned here is a Spring Security User from My Class.
//Decorator pattern is where you convert an instance of your class to another type.
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private final User user;

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities()
                .stream().map(SecurityAuthority::new)
                .collect(Collectors.toList());
    }

}
