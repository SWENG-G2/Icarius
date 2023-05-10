package icarius.http;

import icarius.auth.User;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PatchRequest extends ServerRequest {

    public PatchRequest(String urlPath, User user) {
        super(urlPath, user);
    }

    @Override
    public ServerResponse send() {
        RequestBody requestBody = new FormBody.Builder().build();
    
        Request request = new Request.Builder()
        .url(getUrl())
        .addHeader("credentials", user.getAuth())
        .patch(requestBody)
        .build();

        return execute(request); 
    }
    
}
