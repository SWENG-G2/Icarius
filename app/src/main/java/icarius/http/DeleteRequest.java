package icarius.http;

import icarius.auth.UserClient;
import okhttp3.Request;

public class DeleteRequest extends ServerRequest {

    public DeleteRequest(String urlPath, UserClient user) {
        super(urlPath, user);
    }

    @Override
    public ServerResponse send() {
        request = new Request.Builder()
                .url(getUrl())
                .addHeader("credentials", user.getAuth())
                .delete()
                .build();

        return execute(request);
    }

}
