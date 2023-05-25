package icarius.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import icarius.App;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ServerResponseTest {
    private static final String TEST_PATH = "/test";
    private static final String RESPONSE_BODY = "https://www.youtube.com/shorts/VLkvr4XDTII";
    private static final String HEADER_KEY = "HEADER_KEY";
    private static final String HEADER_VAL = "HEADER_VAL";
    private static final int RESPONSE_CODE = 200;

    @Test
    public void canParseResponse() {
        Request mockRequest = new Request.Builder()
                .url(App.BASE_URL + TEST_PATH)
                .build();
                
        Response mockResponse = new Response.Builder()
                .request(mockRequest)
                .body(ResponseBody.create(RESPONSE_BODY, MediaType.get("text/plain; charset=UTF-8")))
                .code(RESPONSE_CODE)
                .protocol(Protocol.HTTP_2)
                .message("")
                .addHeader(HEADER_KEY, HEADER_VAL)
                .build();

        ServerResponse responseClass = new ServerResponse(mockResponse);

        assertEquals(RESPONSE_CODE, responseClass.getCode());
        assertEquals(RESPONSE_BODY, responseClass.getBody());
        assertEquals(HEADER_VAL, responseClass.getHeader(HEADER_KEY));
    }

    @Test
    public void canAddHeader() {
        ServerResponse responseClass = new ServerResponse(200, "", null);
        responseClass.addHeader(HEADER_KEY, HEADER_VAL);
        assertEquals(HEADER_VAL, responseClass.getHeader(HEADER_KEY));
    }
}
