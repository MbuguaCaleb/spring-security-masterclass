package com.safaricom.orguser.lessontwo.services;

import com.safaricom.orguser.lessontwo.repository.UserRepository;
import com.safaricom.orguser.lessontwo.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//TODO(Notes)
//Instead of adding this bean in the configuration, i can still annotate it with @Service, and
// it is still going to be in the context
@AllArgsConstructor
@Service
public class JPAUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //Spring security will get my User from this method
    //And return a userDetails Object and place it into the context.
    //from the below implementation i have provided my User to Spring Security.
    @Override
    public UserDetails loadUserByUsername(String username){
        var user = userRepository.findUserByUserName(username);
        return user.map(SecurityUser::new).orElseThrow(()->new UsernameNotFoundException("username not found" + username));
    }

}
