package com.safaricom.orguser.lessontwo.repository;

import com.safaricom.orguser.lessontwo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    //will be fetching my DB user

    @Query("""  
            SELECT u from User u where u.username= :userName
            """)
    Optional<User> findUserByUserName(String userName);
}
