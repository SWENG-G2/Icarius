package icarius.http;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import icarius.App;
import icarius.auth.User;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;

public abstract class ServerRequest {
    // Request properties
    private String url;

    @Setter
    protected User user;
    @Setter
    @Getter
    private HashMap<String, String> params;

    @Getter
    protected Request request;

    protected ServerRequest(String urlPath, User user) {
        this.url = App.BASE_URL + urlPath;
        this.user = user;
        this.params = new HashMap<>();
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
        Call call = user.getOkHttpClient().newCall(request);
        try {
            ServerResponse serverResponse = new ServerResponse(call.execute());
            // TODO - (CONNALL) once penelope modified to return decryption error, run test below
            // Test:
            //      Open Icarius and Log in
            //      Restart Penelope
            //      Log out and log back in
            //      Check correct creds aren't denied and that server does not have decryption error twice
            return serverResponse;
        } catch (ConnectException connectException) {
            throw new ConnectionException("Server is unreachable, please try again later.");
            //TODO - (CONNALL) - permission excemption
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    public abstract ServerResponse send();
}
