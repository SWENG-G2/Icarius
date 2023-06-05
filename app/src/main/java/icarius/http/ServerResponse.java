package icarius.http;

import java.io.IOException;
import java.net.HttpURLConnection;

import lombok.Data;
import okhttp3.Headers;
import okhttp3.Response;

@Data
public class ServerResponse {
    private int code;
    private String body;
    private Headers headers;

    /**
     * Converts response to Server Response object
     * @param response
     */
    public ServerResponse(Response response) {
        // Save and convert response
        code = response.code();
        headers = response.headers();
        try {
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close response
        response.close();
    }

    /**
     * @param code
     * @param body
     * @param headers
     */
    public ServerResponse(int code, String body, Headers headers) {
        this.code = code;
        this.body = body;
        this.headers = headers;
    }

    /**
     * Get response header value
     * @param name - Response header name
     * @return Response header value
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * Add Header to response
     * @param key
     * @param value
     */
    public void addHeader(String key, String value) {
        if (headers == null) {
            Headers.Builder b = new Headers.Builder();
            headers = b.add(key, value).build();
        } else {
            headers = headers.newBuilder().add(key, value).build();
        }
    }

    /**
     * @return TRUE is request was successful, else FALSE
     */
    public Boolean isSuccessful() {
        return code == HttpURLConnection.HTTP_OK
                || code == HttpURLConnection.HTTP_ACCEPTED
                || code == HttpURLConnection.HTTP_NO_CONTENT;
    }
}
