package icarius.services;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
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
    // string in form a:b where
    // a = user identity
    // b = encrypted public key with RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING RSA parameters
    
    public static String campus = "admin";
    public static String identity = "a";
    
    private static final String ALGORITHM = "RSA";
    private static final String CYPHER = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    public static String getAuth() {
        try {
            // Get time
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/London"));

            // load and generate RSA public key 
            byte[] Base64publicKey = KeyStorageService.loadKey(identity);
            PublicKey publicKey = generatePublicKey(Base64publicKey);

            // format auth
            String auth = identity + "_" + campus + "=" + now;

            // Encrypt and return auth encoded to base64
            byte[] key = encrypt(publicKey, auth);
            return Base64.getEncoder().encodeToString(key);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static byte[] encrypt(PublicKey publicKey, String input) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        // Create cipher
        Cipher cipher = Cipher.getInstance(CYPHER);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, 
            new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
    
        // Convert input to Cipher text
        byte[] encryptedKey = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        return encryptedKey;
    }

    private static PublicKey generatePublicKey(byte[] base64EncodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Decode from Base 64
        byte[] base64DecodedKey = Base64.getDecoder().decode(base64EncodedKey);

        // Generate RSA Public key
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec  X509publicKey = new X509EncodedKeySpec(base64DecodedKey);
        return keyFactory.generatePublic(X509publicKey);
    }
}
