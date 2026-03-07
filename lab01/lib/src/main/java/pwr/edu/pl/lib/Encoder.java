package pwr.edu.pl.lib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {
    private Encoder() {}

    public static String sha256(File file) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[8192];
            int count;
            while ((count = bufferedInputStream.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }

            byte[] hash = digest.digest();
            return String.format("%064x", new BigInteger(1, hash));
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
