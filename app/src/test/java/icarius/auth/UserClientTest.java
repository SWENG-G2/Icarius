package icarius.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import okhttp3.OkHttpClient;

public class UserClientTest {
    /**
     * Test whether key can be fetched from server by:
     * - Mocking request
     * - Return Mocked Response
     * - Test Correct Header value is retrieved from response
     */
    @Test
    public void canRefreshKey() {
        // Mock Server Request and Response
        GetRequest mockRequest = Mockito.mock(GetRequest.class);
        ServerResponse mockResponse = new ServerResponse(200, "", null);
        mockResponse.addHeader("key", "KEY_TEST");

        doReturn(mockResponse).when(mockRequest).send();

        // Mock user
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        UserClient userMock = new UserClient(clientMock);

        userMock.refreshKey(mockRequest);

        assertEquals("KEY_TEST", userMock.getPublicKey());
    }

    /**
     * Test whether User credentials can be validated by server
     * - Mock server request
     * - Mock server response
     * - Test correct header values retrieved from response
     * - Test method returns true if response successful
     */
    @Test
    public void canValidate() {
        // Mock Server Request and Response
        PostRequest mockRequest = Mockito.mock(PostRequest.class);
        ServerResponse mockResponse = new ServerResponse(200, "", null);
        mockResponse.addHeader("valid", "true");
        mockResponse.addHeader("admin", "false");

        doReturn(mockResponse).when(mockRequest).send();

        // Mock user
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        UserClient userMock = new UserClient(clientMock);

        assertTrue(userMock.validate(mockRequest));
        assertTrue(userMock.getValid());
        assertFalse(userMock.getAdmin());
    }
}
