package icarius.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import icarius.App;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ServerRequestTest {
    private static final String TEST_PATH = "/test";
    private static final String[] parameters = { "a", "b", "c" };
    private static final String[] parametersValues = { "1", "BatMan", "/69/bird.png" };

    private static final HashMap<String, String> requestParams = new HashMap<>();

    @BeforeAll
    static void setUp() {
        IntStream.range(0, parameters.length).forEach(idx -> requestParams.put(parameters[idx], parametersValues[idx]));
    }

    @Test
    public void canFormatNoAuthURL() {
        ServerRequest classUnderTest = Mockito.mock(ServerRequest.class, Answers.CALLS_REAL_METHODS);

        classUnderTest.setUrl(TEST_PATH);
        classUnderTest.setUser(null);
        classUnderTest.setParams(new HashMap<>());

        classUnderTest.addParameters(requestParams);

        String generatedUrl = classUnderTest.getUrl();

        // Base request path
        String basePath = App.BASE_URL + TEST_PATH + "?";
        // Generate expected url
        StringBuilder expectedUrl = IntStream.range(0, parameters.length)
                .mapToObj( // Loop through indexes
                        idx -> parameters[idx] + "=" + URLEncoder.encode(parametersValues[idx], StandardCharsets.UTF_8)
                                + (idx != (parameters.length - 1) ? "&" : "")) // Format request parameters. Value needs
                                                                               // encoding cause of special chars (/)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append) // Concat with string builder
                .insert(0, basePath); // Unshift base url path

        assertEquals(expectedUrl.toString(), generatedUrl.toString());
    }

    @Test
    public void canHandleIOExceptionInRequestExecution() throws IOException {
        ServerRequest classUnderTest = Mockito.mock(ServerRequest.class, Answers.CALLS_REAL_METHODS);

        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        Call callMock = Mockito.mock(Call.class);

        doReturn(callMock).when(clientMock).newCall(any(Request.class));
        doThrow(new IOException()).when(callMock).execute();

        Request expectedRequest = new Request.Builder()
                .url(App.BASE_URL + TEST_PATH)
                .build();

        classUnderTest.setClient(clientMock);

        String result = classUnderTest.execute(expectedRequest);

        assertNull(result);
    }
}
