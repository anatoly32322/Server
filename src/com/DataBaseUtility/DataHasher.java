package com.DataBaseUtility;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataHasher {
    public static String get_SHA512_password(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger numRepresentation = new BigInteger(1, digest);
            String hashedString = numRepresentation.toString(16);
            while (hashedString.length() < 32) {
                hashedString = "0" + hashedString;
            }
            return hashedString;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such algorithm.");
        }
        return null;
    }
}
