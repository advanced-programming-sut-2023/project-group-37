package model.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordHashing {
    private static final Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    public static String encode(String password){
        return passwordEncoder.encode(password);
    }

    public static boolean checkPassword(String password,String encodedPassword){
        return passwordEncoder.matches(password,encodedPassword);
    }
}
