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
        FormBody.Builder test = new FormBody.Builder();
        for (String arg : args.keySet()) {
            test.add(arg, args.get(arg));
        }
        RequestBody body = test.build();

        // Build Request
        Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

        // Send Request
        send(request, "POST");
    }

    public static ResponseBody get(String url) {
        // Build Request
        Request request = new Request.Builder()
                            .url(url)
                            .build();
        
        // Send Request
        return send(request, "GET");
    }

    public static ResponseBody get(String url, HashMap<String,String> args) {
        // Rebuild url
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (String arg : args.keySet()) {
            urlBuilder.addQueryParameter(arg, args.get(arg));
        }
        url = urlBuilder.build().toString();

        // Call basic get method
        return get(url);
    }

    // upload a text file
    public static void uploadFile(String url) {
        // Build Form Body
        RequestBody body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "file.txt",
            RequestBody.create(
                new File("src/main/resources/test.txt"),
                MediaType.parse("application/octet-stream")))
            .build();

        // Build Request
        Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
        
        // Send Request
        send(request, "FILE UPLOAD");
    }

    private static ResponseBody send(Request request, String requestType) {
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            System.out.println(response.body().string());
            return response.body();
        } catch (IOException ioe) {
            System.out.println("ERROR: IOException caught during " + requestType + " Request \n");
            return null;
        }
    }
}
