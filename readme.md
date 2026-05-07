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



**Tips**

```
1.SpringBoot---->Convention over configuration apporoach
Spring Boot based on dependencies we add configures our applications somehow.

spring-web (will confugure a tomcat container, dispatcherservelet, view)
security

2.Spring Security, (Spring Boots
```
**Notes By**

```
Mbugua Caleb

```