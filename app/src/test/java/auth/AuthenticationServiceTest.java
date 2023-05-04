package auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.auth.AuthenticationService;
import icarius.auth.User;
import okhttp3.OkHttpClient;

public class AuthenticationServiceTest {
    private String EXPECTED_RESPONSE = "";

    @Test
    void getAuthTest() {
        // Create mock use
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        User user = new User(clientMock);

        assertEquals(EXPECTED_RESPONSE, AuthenticationService.getAuth(user));
    }
}
