package icarius.http;

import okhttp3.Request;

public class DeleteRequest extends ServerRequest {

    public DeleteRequest(String urlPath, String identity, int campusId) {
        super(urlPath, identity, campusId);
    }

    @Override
    public String send() {
        Request request = new Request.Builder()
        .url(getUrl())
        .addHeader("IDENTITY", identity)
        .addHeader("KEY", getKey())
        .delete()
        .build();

        return execute(request);
    }
    
}
