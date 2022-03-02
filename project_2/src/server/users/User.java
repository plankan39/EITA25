package server.users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class User {
    private static final Random RANDOM = new SecureRandom();
    private String userName;
    private int ssn;
    private byte[] salt;
    private byte[] hashedPW;
    private int failedLoginAttempts;

    public User(String uName, int ssn, String pw) {
        this.userName = uName;
        this.ssn = ssn;
        this.salt = newSalt();
        this.hashedPW = generateHash(pw);
        this.failedLoginAttempts = 0;

    }

    private static byte[] newSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    private byte[] generateHash(String pw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            System.out.println(pw);
            byte[] hash = md.digest(pw.getBytes());
            System.out.println(hash.toString());
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkPassword(String pw) {
        boolean correct = Arrays.equals(generateHash(pw), hashedPW);
        failedLoginAttempts = correct ? 0 : failedLoginAttempts + 1;
        return correct;
    }

    public int getSSN() {
        return ssn;
    }

    public String getUserName() {
        return userName;
    }

    public String toString() {
        String s = "";
        s = userName + ":" + ssn + ":" + salt.toString() + ":" + hashedPW.toString() + ":";
        return s;
    }
}
