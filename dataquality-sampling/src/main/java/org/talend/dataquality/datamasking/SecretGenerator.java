package org.talend.dataquality.datamasking;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecretGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretGenerator.class);

    private static final String KEY_GEN_ALGO = "PBKDF2WithHmacSHA256";

    private SecretGenerator() {

    }

    /**
     * This method generates a secret Key using the key-stretching algorithm PBKDF2 of
     * <a href="https://docs.oracle.com/javase/7/docs/api/javax/crypto/package-summary.html">javax.crypto</a>.
     * It is basically a hashing algorithm slow by design, in order to increase the time
     * required for an attacker to try a lot of passwords in a bruteforce attack.
     * <br>
     * About the salt :
     * <ul>
     * <li>The salt is not secret, the use of Random is not critical.</li>
     * <li>The salt is important to avoid rainbow table attacks.</li>
     * <li>The salt should be generated with SecureRandom() in case the passwords are stored.</li>
     * <li>In that case the salt should be stored in plaintext next to the password and a unique user identifier.</li>
     * </ul>
     *
     * @param password a password given as a {@code String}.
     * @param keyLength key length to generate
     * @return a {@code SecretKey} securely generated.
     */
    public static SecretKey generateSecretKeyFromPassword(String password, int keyLength) {
        SecretKey secret = null;

        try {
            byte[] salt = new byte[keyLength];
            new Random(password.hashCode()).nextBytes(salt);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_GEN_ALGO);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, keyLength << 3);
            secret = factory.generateSecret(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOGGER.error("Invalid cipher or key algorithm", e);
        }

        if (secret == null) {
            throw new IllegalArgumentException("This password can't be used for Format-Preserving Encryption.");
        }

        return secret;
    }
}
