package icarius.http;

import lombok.Data;
import okhttp3.Headers;

@Data
public class ServerResponse {
    private int code;
    private String body;
    private Headers headers;

    public ServerResponse(int code, String body, Headers headers) {
        this.code = code;
        this.body = body;
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Boolean isSuccessful() {
        return this.code == 200 ? true : false;
    }
}
