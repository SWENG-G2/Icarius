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
import okhttp3.ResponseBody;

public class HttpService {
    private static OkHttpClient client = new OkHttpClient();

    public static void post(String url, HashMap<String,String> args) {
        // Build Form Body
        FormBody.Builder body = new FormBody.Builder();
        args.forEach((key, value) -> body.add(key, value));

        send(url, body.build());
    }

    public static ResponseBody get(String url) {
        return send(url, null);
    }

    public static ResponseBody get(String url, HashMap<String,String> args) {
        // Reconfigure url
        HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
        args.forEach((key, value) -> urlB.addQueryParameter(key, value));
        url = urlB.build().toString();

        return get(url);
    }

    // upload a file
    public static void uploadFile(String url, HashMap<String,String> args, String filePath) {
        File file = new File(filePath);
        MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");

        // Build Form Body
        MultipartBody.Builder body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.getName(), RequestBody.create(file, MEDIA_TYPE));
            args.forEach((key, value) -> body.addFormDataPart(key, value));

        send(url, body.build());
    }

    private static ResponseBody send(String url, RequestBody requestBody) {
        // Build Request
        Request.Builder request = new Request.Builder().url(url);
        if (requestBody != null) {
            // if POST request
            request.post(requestBody);
        }

        // Execute Call
        Call call = client.newCall(request.build());
        try (Response response = call.execute()) {
            System.out.println(response.body().string());
            return response.body();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
}
