package icarius.http;

import icarius.auth.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DeleteRequest extends ServerRequest {

    public DeleteRequest(String urlPath, User user, OkHttpClient okHttpClient) {
        super(urlPath, user, okHttpClient);
    }

    @Override
    public ServerResponse send() {
        Request request = new Request.Builder()
        .url(getUrl())
        .addHeader("credentials", user.getAuth())
        .delete()
        .build();

        return execute(request);
    }
    
}
