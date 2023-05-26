package icarius.http;

import icarius.auth.UserClient;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PatchRequest extends ServerRequest {

    public PatchRequest(String urlPath, UserClient user) {
        super(urlPath, user);
    }

    @Override
    public ServerResponse send() {
        RequestBody requestBody = new FormBody.Builder().build();

        request = new Request.Builder()
                .url(getUrl())
                .addHeader("credentials", user.getAuth())
                .patch(requestBody)
                .build();

        return execute(request);
    }

}
