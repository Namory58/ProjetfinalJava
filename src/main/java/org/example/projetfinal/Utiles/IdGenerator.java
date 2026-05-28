package org.example.projetfinal.Utiles;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class IdGenerator {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ID_LENGTH = 12;
    public static String generateId() {
        return RANDOM.ints(ID_LENGTH, 0, CHARS.length())
                .mapToObj(i -> String.valueOf(CHARS.charAt(i)))
                .collect(Collectors.joining());
    }
}
