package icarius.http;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import icarius.App;
import icarius.auth.UserClient;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;

public abstract class ServerRequest {
    // Request properties
    private String url;

    @Setter
    protected UserClient user;
    @Setter
    @Getter
    private HashMap<String, String> params;

    @Getter
    protected Request request;

    /**
     * Abstract ServerRequest Constructor
     * @param urlPath
     * @param user
     */
    protected ServerRequest(String urlPath, UserClient user) {
        this.url = App.BASE_URL + urlPath;
        this.user = user;
        this.params = new HashMap<>();
    }

    /**
     * Set endpoint in server for request
     * Server Base URL is automatically added
     * @param urlPath
     */
    public void setUrl(String urlPath) {
        this.url = App.BASE_URL + urlPath;
    }

    /**
     * Add Parameter to Server Request
     * @param key
     * @param value
     */
    public void addParameter(String key, String value) {
        params.put(key, value);
    }

    /**
     * Add multiple parameters to request
     * @param parameters
     */
    public void addParameters(Map<String, String> parameters) {
        params.putAll(parameters);
    }

    /**
     * Build URL by adding request parameters to request URL
     * @return Complete Request URL
     */
    public String getUrl() {
        HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
        params.forEach((key, value) -> urlB.addQueryParameter(key, value));
        return urlB.build().toString();
    }

    /**
     * Send request to server and return response
     * @param request
     * @return ServerResponse response
     */
    protected ServerResponse execute(Request request) {
        // Execute Call
        Call call = user.getOkHttpClient().newCall(request);
        try {
            ServerResponse serverResponse = new ServerResponse(call.execute());
            return serverResponse;
        } catch (ConnectException connectException) {
            throw new ConnectionException("Server is unreachable, please try again later.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    /**
     * Send Server Request to server
     * @return ServerResponse response
     */
    public abstract ServerResponse send();
}
