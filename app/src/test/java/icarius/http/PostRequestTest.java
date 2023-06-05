package icarius.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.App;
import icarius.auth.UserClient;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PostRequestTest {
    private static final String TEST_PATH = "/test";
    private static final String TEST_FILE_PATH = "/Desktop/tastefulLongBoiPics";
    private static final String RESPONSE_BODY = "https://www.youtube.com/shorts/VLkvr4XDTII";
    private static final String AUTH = "1234";

    /**
     * Test can execute PostRequest with authorisation
     * 
     * @throws IOException
     */
    @Test
    public void canExecuteRequest() throws IOException {
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        UserClient userMock = Mockito.mock(UserClient.class);
        Call callMock = Mockito.mock(Call.class);

        RequestBody requestBody = new FormBody.Builder().build();
        Request expectedRequest = new Request.Builder()
                .url(App.BASE_URL + TEST_PATH)
                .addHeader("credentials", AUTH)
                .post(requestBody)
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
        PostRequest postRequest = new PostRequest(TEST_PATH, userMock);
        ServerResponse response = postRequest.send();
        Request request = postRequest.getRequest();

        assertEquals("POST", request.method());
        assertEquals(AUTH, request.header("credentials"));
        assertEquals(App.BASE_URL + TEST_PATH, request.url().toString());

        assertEquals(200, response.getCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    /**
     * Test can execute PostRequest containing a file with authorisation
     * @throws IOException
     */
    public void canExecuteRequestWithFile() throws IOException {
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        UserClient userMock = Mockito.mock(UserClient.class);
        Call callMock = Mockito.mock(Call.class);

        File file = new File(TEST_FILE_PATH);
        MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");
        RequestBody expectedRequestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(file, MEDIA_TYPE))
                .addFormDataPart("type", "image")
                .build();

        Request expectedRequest = new Request.Builder()
                .url(App.BASE_URL + TEST_PATH)
                .addHeader("credentials", AUTH)
                .post(expectedRequestBody)
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
        PostRequest postRequest = new PostRequest(TEST_PATH, userMock);
        postRequest.addFile(TEST_FILE_PATH, "image");
        ServerResponse response = postRequest.send();
        Request request = postRequest.getRequest();

        assertEquals("POST", request.method());
        assertEquals(AUTH, request.header("credentials"));
        assertEquals(App.BASE_URL + TEST_PATH, request.url().toString());
        assertEquals(TEST_FILE_PATH, postRequest.filePath);
        assertEquals("image", postRequest.fileType);

        assertEquals(200, response.getCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }
}
