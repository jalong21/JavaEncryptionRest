package com.encryption.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;
    private static final int ITERATIONS = 65536;
    private static final int SALT_SIZE = 16;

    public static String encrypt(String input, String password) throws Exception {
        byte[] salt = generateSalt();
        SecretKeySpec key = createSecretKey(password.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
        byte[] encryptedText = cipher.doFinal(input.getBytes());
        byte[] encryptedTextWithSalt = new byte[salt.length + encryptedText.length];
        System.arraycopy(salt, 0, encryptedTextWithSalt, 0, salt.length);
        System.arraycopy(encryptedText, 0, encryptedTextWithSalt, salt.length, encryptedText.length);
        return Base64.getEncoder().encodeToString(encryptedTextWithSalt);
    }

    public static String decrypt(String encryptedText, String password) throws Exception {
        byte[] encryptedTextBytes = Base64.getDecoder().decode(encryptedText);
        byte[] salt = new byte[SALT_SIZE];
        System.arraycopy(encryptedTextBytes, 0, salt, 0, salt.length);
        SecretKeySpec key = createSecretKey(password.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
        byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes, salt.length, encryptedTextBytes.length - salt.length);
        return new String(decryptedTextBytes);
    }

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_SIZE);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return salt;
    }
}
