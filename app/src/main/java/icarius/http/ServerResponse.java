package icarius.http;

import java.io.IOException;

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

    public String getHeader(String name) {
        return headers.get(name);
    }
}
