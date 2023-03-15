package icarius.http;

import icarius.auth.User;
import okhttp3.Request;

public class DeleteRequest extends ServerRequest {

    public DeleteRequest() {
    }

    public DeleteRequest(String urlPath, User user) {
        super(urlPath, user);
    }

    @Override
    public String send() {
        Request request = new Request.Builder()
        .url(getUrl())
        .addHeader("credentials", user.getAuth())
        .delete()
        .build();

        return execute(request);
    }
    
}
