package icarius.http;

import java.io.IOException;
import java.util.HashMap;

import icarius.App;
import icarius.auth.User;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class ServerRequest {
    // Request properties
    private String url;
    protected User user;
    private HashMap<String, String> params = new HashMap<String, String>();

    // Client
    protected static final OkHttpClient client = new OkHttpClient();

    public ServerRequest() {}

    public ServerRequest(String urlPath) {
        this.url = App.BASE_URL + urlPath;
        user = null;
    }

    public ServerRequest(String urlPath, User user) {
        this.url = App.BASE_URL + urlPath;
        this.user = user;
    }

    public void setUrl(String urlPath) {
        this.url = App.BASE_URL + urlPath;
    }

    public void setUser(User user) {
        this.user = user;
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

    protected String execute(Request request) {
        // Execute Call
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            // read response (socket automatically closes after first read)
            String body = response.body().string();
            return body;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    public abstract String send();
}
