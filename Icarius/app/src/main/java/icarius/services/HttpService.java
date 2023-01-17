package icarius.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpService {
    private static OkHttpClient client = new OkHttpClient();

    // Send a POST request with parameters
    public static String post(String url, HashMap<String,String> parameters) {
        // Build Form Body
        FormBody.Builder body = new FormBody.Builder();
        parameters.forEach((key, value) -> body.add(key, value));

        return send(url, body.build(), "POST" );
    }
    
    // Send a POST request with file
    public static String postFile(String url, HashMap<String,String> parameters, String filePath) {
        File file = new File(filePath);
        MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");

        // Build Form Body
        MultipartBody.Builder body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.getName(), RequestBody.create(file, MEDIA_TYPE));
            parameters.forEach((key, value) -> body.addFormDataPart(key, value));

        // Send file and print server response
        return send(url, body.build(), "POST");
    }

    // Send GET request
    public static String get(String url) {
        return send(url, null, null);
    }

    // Send GET request with parameters
    public static String get(String url, HashMap<String,String> parameters) {
        // Reconfigure url
        HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
        parameters.forEach((key, value) -> urlB.addQueryParameter(key, value));
        url = urlB.build().toString();

        return send(url, null, null);
    }

    // Send DELETE request with parameters
    public static String delete(String url, HashMap<String,String> parameters) {
        // Reconfigure url
        HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
        parameters.forEach((key, value) -> urlB.addQueryParameter(key, value));
        url = urlB.build().toString();

        return send(url, null, "DELETE");
    }

    // Returns response from GET requests as string else null
    private static String send(String url, RequestBody requestBody, String requestType) {
        // Build basic request with url and authentication
        Request.Builder request = new Request.Builder().url(url)
        .addHeader("IDENTITY", AuthenticationService.identity)
        .addHeader("KEY", AuthenticationService.getAuth());

        switch (requestType) {
            case "POST":
                request.post(requestBody);
                break;
            case "DELETE":
                request.delete();
                break;
            case "PATCH":
                request.patch(requestBody);
                break;
            default:
                break;
        }

        // Execute Call
        Call call = client.newCall(request.build());
        try (Response response = call.execute()) {
            // read response (socket automatically closes after first read)
            return response.body().string();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }

        // NOTE: After first read, response source automatically closes
    }
}
