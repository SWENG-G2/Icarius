package icarius.auth;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class AuthenticationService {
    private static final int KEY_SIZE = 2048;
    private static final String ALGORITHM = "RSA";
    private static final String CYPHER = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    /**
     * Creates an authorisation keyword encrypted with the public key contained in the userClient
     * 
     * @param userClient
     * @param time
     * @return Encrypted keyword
     */
    public static String getAuth(UserClient userClient, ZonedDateTime time) {
        try {
            if (time == null || time.equals(null)) {
                // Get time
                time = ZonedDateTime.now(ZoneId.of("Europe/London"));
            }

            // format credentials string
            String username = userClient.getCredentials().getUsername();
            String password = userClient.getCredentials().getPassword();
            String keyWord = username + "=" + password + "=" + time;

            // load and generate RSA public key
            byte[] Base64publicKey = userClient.getPublicKey().getBytes(StandardCharsets.UTF_8);
            PublicKey RSAPublicKey = generatePublicKey(Base64publicKey);

            // Encrypt and return auth encoded to base64
            return encrypt(RSAPublicKey, keyWord);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Encrypts input string using input public key
     * 
     * @param publicKey
     * @param input
     * @return Encrypted input
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    public static String encrypt(PublicKey publicKey, String input)
            throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        // Create cipher
        Cipher cipher = Cipher.getInstance(CYPHER);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey,
                new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));

        // Convert input to Cipher text
        byte[] encryptedString = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptedString);
    }

    /**
     * Generate RSA Public Key object from base64 encoded Key
     * 
     * @param base64EncodedKey
     * @return  PublicKey key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static PublicKey generatePublicKey(byte[] base64EncodedKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Decode from Base 64
        byte[] base64DecodedKey = Base64.getDecoder().decode(base64EncodedKey);

        // Generate RSA Public key
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(base64DecodedKey);
        return keyFactory.generatePublic(X509publicKey);
    }

    /**
     * Decrypts input string using input private key
     * 
     * @param privateKey
     * @param encryptedInput
     * @return Decrypted input string
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    public static String decrypt(PrivateKey privateKey, String encryptedInput)
            throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {

        byte[] base64DecodedInput = Base64.getDecoder().decode(encryptedInput);

        // Create cipher
        Cipher cipher = Cipher.getInstance(CYPHER);
        cipher.init(Cipher.DECRYPT_MODE, privateKey,
                new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));

        // Decrypt Cipher text
        byte[] decryptedString = cipher.doFinal(base64DecodedInput);

        return new String(decryptedString, StandardCharsets.UTF_8);
    }

    /**
     * Generates a Public/Private Key Pair
     * 
     * @return Public, Private key pair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }
}
