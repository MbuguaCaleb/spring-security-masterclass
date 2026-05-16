package com.safaricom.orguser.lessontwo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

//Everytime I fetch my user I will be fetching it with the authorities
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    //i am relating User & Authorities
    //The table that stores the relationship between the two is:user_authorities & join column name is userId
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

}
