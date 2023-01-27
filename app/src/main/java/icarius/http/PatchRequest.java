package icarius.http;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PatchRequest extends ServerRequest {
    
    public PatchRequest(String urlPath, String identity, int campusId) {
        super(urlPath, identity, campusId);
    }

    @Override
    public String send() {
        RequestBody requestBody = new FormBody.Builder().build();
    
        Request request = new Request.Builder()
        .url(getUrl())
        .addHeader("IDENTITY", identity)
        .addHeader("KEY", getKey())
        .patch(requestBody)
        .build();

        return execute(request); 
    }
    
}
