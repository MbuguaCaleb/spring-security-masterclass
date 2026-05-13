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