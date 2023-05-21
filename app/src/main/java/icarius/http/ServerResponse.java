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

    public ServerResponse(Response response) {
        code = response.code();
        headers = response.headers();
        try {
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.close();
    }

    public ServerResponse(int code, String body, Headers headers) {
        this.code = code;
        this.body = body;
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Boolean isSuccessful() {
        return code == HttpURLConnection.HTTP_OK 
            || code == HttpURLConnection.HTTP_ACCEPTED
            || code == HttpURLConnection.HTTP_NO_CONTENT;
    }
}
