package icarius.http;

import okhttp3.Request;

public class GetRequest extends ServerRequest {
    public GetRequest(String urlPath) {
        super(urlPath);
    }

    @Override
    public String send() {
        Request request = new Request.Builder()
        .url(getUrl())
        .build();

        return execute(request);
    }
}
