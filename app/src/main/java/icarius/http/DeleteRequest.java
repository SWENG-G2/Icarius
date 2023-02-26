package icarius.http;

import icarius.auth.User;
import okhttp3.Request;

public class DeleteRequest extends ServerRequest {

    public DeleteRequest(String urlPath, User user) {
        super(urlPath, user);
    }

    @Override
    public String send() {
        Request request = new Request.Builder()
        .url(getUrl())
        .addHeader("CREDENTIALS", user.getAuth())
        .delete()
        .build();

        return execute(request);
    }
    
}
