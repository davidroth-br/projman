package com.montrealcollege.projman.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean checkPassword(String raw, String encoded) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(raw, encoded);
    }
//
//    public static void main(String[] args) {
//        String password = "123";
//        String encryptedPassword = encryptPassword(password);
//
//        System.out.println("Encrypted Password: " + encryptedPassword);
//    }
}
