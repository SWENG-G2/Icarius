package icarius.http;

import java.io.IOException;
import java.util.HashMap;

import icarius.App;
import icarius.services.AuthenticationService;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class ServerRequest {
    // Request properties
    private final String url;
    protected final String identity, campusId;
    private HashMap<String, String> params = new HashMap<String, String>();
    
    // Client
    private static OkHttpClient client = new OkHttpClient();

    public ServerRequest(String urlPath) {
        this.url = App.BASE_URL + urlPath;
        identity = null;
        campusId = null;
    }

    public ServerRequest(String urlPath, String identity, int campusId) {
        this.url = App.BASE_URL + urlPath;
        this.identity = identity;
        this.campusId = setCampusId(campusId);
    }

    private String setCampusId(int campusId) {
        return campusId == 0 ? "admin" : String.valueOf(campusId);
    }

    public void addParameter(String key, String value) {
        params.put(key, value);
    }

    public void addParameters(HashMap<String, String> parameters) {
        params.putAll(parameters);
    }

    public String getUrl() {
        HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
        params.forEach((key, value) -> urlB.addQueryParameter(key, value));
        return urlB.build().toString();
    }

    public String getKey() {
        return AuthenticationService.getAuth(identity, campusId);
    }

    protected String execute(Request request) {
        // Execute Call
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            // read response (socket automatically closes after first read)
            return response.body().string();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    public abstract String send();
}
