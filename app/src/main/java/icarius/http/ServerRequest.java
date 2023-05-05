package icarius.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import icarius.App;
import icarius.auth.User;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public abstract class ServerRequest {
    // Request properties
    private String url;

    @Setter
    protected User user;
    @Setter
    private HashMap<String, String> params;

    @Setter
    protected OkHttpClient client;

    @Getter
    protected Request request;

    protected ServerRequest(String urlPath, OkHttpClient client) {
        this.url = App.BASE_URL + urlPath;
        this.user = null;
        this.params = new HashMap<>();
        this.client = client;
    }

    protected ServerRequest(String urlPath, User user) {
        this.url = App.BASE_URL + urlPath;
        this.user = user;
        this.params = new HashMap<>();
        this.client = user.getOkHttpClient();
    }

    public void setUrl(String urlPath) {
        this.url = App.BASE_URL + urlPath;
    }


    public void addParameter(String key, String value) {
        params.put(key, value);
    }

    public void addParameters(Map<String, String> parameters) {
        params.putAll(parameters);
    }

    public String getUrl() {
        HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
        params.forEach((key, value) -> urlB.addQueryParameter(key, value));
        return urlB.build().toString();
    }

    protected ServerResponse execute(Request request) {
        // Execute Call
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            return new ServerResponse(response);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    public abstract ServerResponse send();
}
