package com.safaricom.orguser.ss_ep_08_oauth2authorizationserver.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    //2 filters needed

    //authorization filter
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http)
            throws Exception {

        //default config
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        //configuring the default authorization server.
        //if you don't want the defaults
        http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        //configuring for  the auth code grant type
        //if user tries to access page with invalid token, they are redirected to the login page
        http.exceptionHandling(
                e -> e.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")
                )
        );

        return http.build();
    }


    //application Filter
    //login page on the authorization sever configured by Spring Security
    @Bean
    @Order(2)
    public SecurityFilterChain applicationServerFilterChain(HttpSecurity http)
            throws Exception {

        http.formLogin().and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();

        return http.build();
    }


    //CONFIGURING MU uSEERS
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails u1 = User.withUsername("caleb")
                .password("password")
                .authorities("read")
                .build();

        return new InMemoryUserDetailsManager(u1);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //registered client repository
    //the authorization server must know my clients
    // must be registered in the authorization server
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient r1 = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret("secret")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .redirectUri("https://springone.io/authorized")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build();

        return new InMemoryRegisteredClientRepository(r1);

    }


    //using the defaults
    //you can use this to change the URLS.
//there are so many endpoints that OAUT2.0 COMES With
    // return (new Builder()).authorizationEndpoint("/oauth2/authorize").tokenEndpoint("/oauth2/token").jwkSetEndpoint("/oauth2/jwks").tokenRevocationEndpoint("/oauth2/revoke").tokenIntrospectionEndpoint("/oauth2/introspect").oidcClientRegistrationEndpoint("/connect/register").oidcUserInfoEndpoint("/userinfo");
    //if we need to change any of the defaults, this is where we do it
    //MY AUTHORIZATION Server will produce an access token of type JWT a the end
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    //todo(authorization_page)
    //TODO N/B (always Note that the authorization code is only usable once)
   // http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://springone.io/authorized&code_challenge=ZsGt1qiRwBK43Rfvq4zDDbfjCU7IEQPs_j_mDZJjvX8&code_challenge_method=S256


    //todo(the authirization server is the one that genrates the JWT Token in the second successfull handhake,
    // with the client and the auth_code
    //for me to use JWT i must have a pair of public and private Keys

    //This class is the one that creates the JWT
    //THE Client normally has the public key well stored.
    //in a good implementation, we can even rotate keys
    //the resource server used the Public Key to Validate the Token
    //rememember-->A JWT Has got three parts, hEADER. Body, Signature
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey key = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet set = new JWKSet(key);
        return new ImmutableJWKSet<>(set);
    }


}
