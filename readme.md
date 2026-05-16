**Spring Security Intro**
```
It is about application level security

(a)Authentication

(b)Authorization

security is large. it can start from the network to the infastructure layer.

It is the logic we write in our application that is related to security.

Authentication-->Logic in my application that decides who is the user trying 
to do something.

Can be achieved By: [ UserName & Password, Hashed Fingerprint, or Some Complex 
Certificate]

Authentication is where the application tries to establish who you are.
Authorization...the application decides if you are allowed or not allowed to do something.

we configure the authorization rules.
In a web application i protect access to  my resources based on the
authorization rules.

We have specific authorities or roles.
Authority---? is something you have. An action i can do.
Role--------? is something you are.It is a Badge. Admin, Manager, Etc

They have the same interface behinds the scenes, (Both Authentication and Authorization)
GrantedAuthority.

Spring security provides varoius authentcation mechanisims

(a)Http Basic
(b)Cert
(c)Jwt/Oauth2 flows

```

| Authentication | Authorization Rules     |
|----------------|-------------------------|
| Http Basic     | WEB Apps  (Http Filters) | 
| Certificate    | Non Web Apps (Aspects)  | 
| JWT/Oauth2.0   |                         | 

![lesson_one.png](lesson_one.png)

**Spring Security Config**

```
By default all resources in my application when i have spring security are secured.
With the randomly generated credentials.

They are secured by Http Basic Authentication.

```
**Encoding, Encryption and Hash Functions**


| Encoding                                                                                 | Encryption                                                                              | Hash functions                                                                                                                             |
|------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| It is function that is always pssible to <br/>revert somehow.                            | You are transforming an input into an output<br/>                                       | From and input you can get the output.<br> but from the output you cannever by any means find the input.                                   |
| it may be a mathematical computation that does not <br/> need even a secret to decode it | But to go back to the input you always need a secret.                                   | 2nd rule of a hash function is <br/> if you have an input for a hash function you can be able to test if it corresponds to the output<br/> |
| example 345 --> reversing it to 541 is an encoding<br/>                                  | Not everyone is able to find waht was the input after an<br/> encryption functioin<br/> | wow, this is how padsswords work, I.e you can do an equality check, but you can never revert back.                                         |
 | In an encoding, you can always revert the output to find the input if you know the rule  | It is still a trandfomation but it implies you need a secret to go back to the input.   | if one stoled hashed password, they cannever find the input.                                                                               |
 | In an Ecoding you do not even need to have a secret.                                     | Without a secret you cannot go back.                                                    |                                                                                                                                            |
 | Can be always reversed                                                                   | A secret is also known as a key and it can be symetric or assymeric,                   |                                                                                                                                            |
      
  ![lesson_two.png](lesson_two.png)


**Creating UserDetails Service**

```
it is that compoment that manages my details.
it tells spring security where to get credentials from.
Meaning i can have my custom HttpBasic password.
if i have users, i can store their passwords.

```

**Spring Security Architecture (Behind the SCENES)**

![lesson_three.png](lesson_three.png)

![image_four.png](image_four.png)

```

Spring Security especially in WebApps is a sequence of filters.

When a HTTP request comes, before it goes to the controller, we start with the  {Authentication Filter} first of all

(a)Authentication Filter---> Authentication Manager
(b) Authentication Manager------->Authentication Provider
(c)Authentication Provider-------> UserDetails Service
(d)User details service, if password is hashed calls---> Password Encoder
(e)Once the Match is successful, we put the UserDetails in the security context.
   (With the Role and Permission..
(f)After this we can go to the authorization Filter.

Contract between Spring security and your application  bu which your app knows how
to obtain user details.

UserDetails is an interface, all we need is to implement it and spring secuerity
will know where to get user credentials...

including the DataBase or even a web service

(g)After a successful authentication, the User Object is stored in the context.

```

**Adding User Details to context**

```
Method One (Configuration Way.........
@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new JPAUserDetailsService();
    }
}

Method Two (StereoType Way............

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

N/B
My Default Implementation of Authentication in Spring Security is HttpBasic.
UserName & Password

```

**(Granted Authority Interface) -->Implementation Roles and Authrorities**

![granted_authority_interface.png](granted_authority_interface.png)

```
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

        return List.of(() -> "read");
    }

}

A spring security User has what we call granted authority, which is the interface beneath
which roles and authorities are implemented.

An authority is an action which a user can do. (read, write, delete)
A role is a Badge represented by a subject, admin, manager,client,visitor
It depends on how you want to implement  authorities in your app.
If you want granular actions, Authortities
I may also want to badge my users, eg Admin, and all the actions they can do.
(Role-->Groups Actions)

```

**Tips**
```
1.SpringBoot---->Convention over configuration apporoach
Spring Boot based on dependencies we add configures our applications somehow.

spring-web (will confugure a tomcat container, dispatcherservelet, view)
security

2.Spring Security, (Spring Boots)

3.Spring Security Starts with a Filter Just before your request.

when using a web app

security filter---->endpoint


```

**Eager Fetching vs Lazy Fetching**

```
1. EAGER Fetching

With FetchType.EAGER, related data is loaded immediately together with the parent entity.

@ManyToOne(fetch = FetchType.EAGER)
private Customer customer;

When you fetch an Order, the Customer is also fetched automatically.

2.Lazy Fetch

With FetchType.LAZY, related data is loaded only when accessed.

@OneToMany(fetch = FetchType.LAZY)
private List<OrderItem> items;
```
**Notes By**

```
Mbugua Caleb

```