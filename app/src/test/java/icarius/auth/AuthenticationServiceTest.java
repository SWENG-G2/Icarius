package icarius.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.ZonedDateTime;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import okhttp3.OkHttpClient;

public class AuthenticationServiceTest {
    private String MOCK_USERNAME = "testUsername";
    private String MOCK_PASSWORD = "testPassword";
    private String MOCK_TIME = "2023-05-21T20:52:24.193223100+01:00[Europe/London]";

    @Test
    public void canEncryptDecrypt()
            throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        // Generate Key Pair
        KeyPair keyPair = AuthenticationService.generateKeys();
        String base64EncodedPublicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        // Create mock user
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        User userMock = new User(clientMock);
        userMock.setCredentials(new Credentials(MOCK_USERNAME, MOCK_PASSWORD));
        userMock.setPublicKey(base64EncodedPublicKey);

        // Get encrypted auth, then decrypt it
        String encryptedKeyWord = AuthenticationService.getAuth(userMock, ZonedDateTime.parse(MOCK_TIME));
        String decryptedKeyWord = AuthenticationService.decrypt(keyPair.getPrivate(), encryptedKeyWord);

        // Check output
        String expectedKeyword = MOCK_USERNAME + "=" + MOCK_PASSWORD + "=" + MOCK_TIME;
        assertEquals(expectedKeyword, decryptedKeyWord);
    }

}
