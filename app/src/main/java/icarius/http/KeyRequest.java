package icarius.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class KeyRequest extends ServerRequest {
    public static final String keyEndPoint = "/key";

    public KeyRequest() {
        super(keyEndPoint);
    }

    @Override
    public String send() {
        Request request = new Request.Builder()
        .url(getUrl())
        .build();

        return execute(request);
    }

    @Override
    protected String execute(Request request) {
        // Execute Call
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            // read response (socket automatically closes after first read)
            String key = response.header("key");
            return key;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

}
