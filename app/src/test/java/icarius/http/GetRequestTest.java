    package icarius.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.App;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetRequestTest {
    private static final String TEST_PATH = "/test";

    private static final String RESPONSE_BODY = "https://www.youtube.com/shorts/VLkvr4XDTII";

    @Test
    void canExecuteRequest() throws IOException {
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        Call callMock = Mockito.mock(Call.class);

        Request expectedRequest = new Request.Builder()
                .url(App.BASE_URL + TEST_PATH)
                .build();

        Response response = new Response.Builder()
                .request(expectedRequest)
                .body(ResponseBody.create(RESPONSE_BODY, MediaType.get("text/plain; charset=UTF-8")))
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message("")
                .build();

        doReturn(callMock).when(clientMock).newCall(any(Request.class));
        doReturn(response).when(callMock).execute();

        GetRequest getRequest = new GetRequest(TEST_PATH, clientMock);

        String result = getRequest.send();

        Request request = getRequest.getRequest();

        assertEquals("GET", request.method());
        assertEquals(App.BASE_URL + TEST_PATH, request.url().toString());

        assertEquals(RESPONSE_BODY, result);
    }
}
