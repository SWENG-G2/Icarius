package icarius.http;

import icarius.auth.UserClient;
import okhttp3.Request;

public class GetRequest extends ServerRequest {

    public GetRequest(String urlPath, UserClient user) {
        super(urlPath, user);
    }

    @Override
    public ServerResponse send() {
        request = new Request.Builder()
                .url(getUrl())
                .build();
        return execute(request);
    }
}
