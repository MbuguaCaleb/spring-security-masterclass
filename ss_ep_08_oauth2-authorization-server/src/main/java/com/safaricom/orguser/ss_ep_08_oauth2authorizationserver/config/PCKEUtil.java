package com.safaricom.orguser.ss_ep_08_oauth2authorizationserver.config;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PCKEUtil {
    public static String generateCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();

        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    public static String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    codeVerifier.getBytes(StandardCharsets.US_ASCII)
            );

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException("Unable to generate code challenge", e);
        }
    }
}
