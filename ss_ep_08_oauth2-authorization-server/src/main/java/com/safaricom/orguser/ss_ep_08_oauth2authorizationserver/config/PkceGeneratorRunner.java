package com.safaricom.orguser.ss_ep_08_oauth2authorizationserver.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PkceGeneratorRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {

        String codeVerifier = PCKEUtil.generateCodeVerifier();
        String codeChallenge = PCKEUtil.generateCodeChallenge(codeVerifier);

        System.out.println("==========================================");
        System.out.println("PKCE VALUES");
        System.out.println("==========================================");
        System.out.println("code_verifier  : " + codeVerifier);
        System.out.println("code_challenge : " + codeChallenge);
        System.out.println("method         : S256");
        System.out.println("==========================================");
    }
}