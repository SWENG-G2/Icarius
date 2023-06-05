package icarius.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import icarius.App;
import icarius.auth.UserClient;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PatchRequestTest {
    private static final String TEST_PATH = "/test";

    private static final String RESPONSE_BODY = "https://www.youtube.com/shorts/VLkvr4XDTII";

    private static final String AUTH = "1234";

    /**
     * Test can execute PatchRequest with authorisation
     * 
     * @throws IOException
     */
    @Test
    public void canExecuteRequest() throws IOException {
        OkHttpClient clientMock = mock(OkHttpClient.class);
        UserClient userMock = mock(UserClient.class);
        Call callMock = mock(Call.class);

        RequestBody requestBody = new FormBody.Builder().build();
        Request expectedRequest = new Request.Builder()
                .url(App.BASE_URL + TEST_PATH)
                .addHeader("credentials", AUTH)
                .patch(requestBody)
                .build();

        Response mockResponse = new Response.Builder()
                .request(expectedRequest)
                .body(ResponseBody.create(RESPONSE_BODY, MediaType.get("text/plain; charset=UTF-8")))
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message("")
                .build();

        when(userMock.getOkHttpClient()).thenReturn(clientMock);
        when(clientMock.newCall(any(Request.class))).thenReturn(callMock);
        when(callMock.execute()).thenReturn(mockResponse);
        when(userMock.getAuth()).thenReturn(AUTH);

        // Conduct Test
        PatchRequest patchRequest = new PatchRequest(TEST_PATH, userMock);
        ServerResponse response = patchRequest.send();
        Request request = patchRequest.getRequest();

        assertEquals("PATCH", request.method());
        assertEquals(AUTH, request.header("credentials"));
        assertEquals(App.BASE_URL + TEST_PATH, request.url().toString());

        assertEquals(200, response.getCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }
}
