package http;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpService {
    private static OkHttpClient client = new OkHttpClient();

    public static String post(ServerRequest serverRequest) {
        RequestBody requestBody;
        if ( serverRequest.containsFile() ) {
            requestBody = buildFileUpload(serverRequest);
        } else {
            requestBody = new FormBody.Builder().build();
        }
        
        Request request = new Request.Builder()
        .url(serverRequest.getUrl())
        .addHeader("IDENTITY", serverRequest.identity)
        .addHeader("KEY", serverRequest.getKey())
        .post(requestBody)
        .build();

        return send(request); 
    }

    public static String get(ServerRequest serverRequest) {
        Request request = new Request.Builder()
        .url(serverRequest.getUrl())
        .addHeader("IDENTITY", serverRequest.identity)
        .addHeader("KEY", serverRequest.getKey())
        .build();

        return send(request);
    }

    public static String delete(ServerRequest serverRequest) {
        Request request = new Request.Builder()
        .url(serverRequest.getUrl())
        .addHeader("IDENTITY", serverRequest.identity)
        .addHeader("KEY", serverRequest.getKey())
        .delete()
        .build();

        return send(request);
    }

    public static String patch(ServerRequest serverRequest) {
        RequestBody requestBody = new FormBody.Builder().build();
    
        Request request = new Request.Builder()
        .url(serverRequest.getUrl())
        .addHeader("IDENTITY", serverRequest.identity)
        .addHeader("KEY", serverRequest.getKey())
        .patch(requestBody)
        .build();

        return send(request); 
    }

    private static String send(Request request) {
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

    private static RequestBody buildFileUpload(ServerRequest serverRequest) {
        File file = new File(serverRequest.filePath);
        MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");

        // Build Form Body
        return new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.getName(), RequestBody.create(file, MEDIA_TYPE))
            .addFormDataPart("type", serverRequest.fileType)
            .build();
    }
}
