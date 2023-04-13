package icarius.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetRequest extends ServerRequest {

    public GetRequest(String urlPath, OkHttpClient okHttpClient) {
        super(urlPath, okHttpClient);
    }

    @Override
    public String send() {
        request = new Request.Builder()
                .url(getUrl())
                .build();
        return execute(request);
    }
}
