package com.montrealcollege.projman.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {
    // Encrypt Password with BCryptPasswordEncoder
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean checkPassword(String raw, String encoded) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(raw, encoded);
    }
}
