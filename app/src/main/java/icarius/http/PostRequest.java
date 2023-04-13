package icarius.http;

import java.io.File;
import java.util.List;

import icarius.auth.User;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostRequest extends ServerRequest{
    protected String filePath, fileType;
    
    // valid file types
    private static final List<String> validFileTypes = Collections.unmodifiableList(
        new ArrayList<String>() {{
            add("image");
            add("audio");
            add("video");
        }}
    );

    public PostRequest(String urlPath, User user, OkHttpClient okHttpClient) {
        super(urlPath, user, okHttpClient);
    }

    @Override
    public String send() {
        RequestBody requestBody;
        if ( containsFile() ) {
            requestBody = buildFileUpload();
        } else {
            requestBody = new FormBody.Builder().build();
        }
        
        Request request = new Request.Builder()
        .url(getUrl())
        .addHeader("credentials", user.getAuth())
        .post(requestBody)
        .build();

        return execute(request); 
    }
    
    public void addFile(String filePath, String fileType) {
        validateFileType(fileType);
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public boolean containsFile() {
        return !(filePath == null || filePath.isBlank());
    }

    private void validateFileType(String fileType) {
        if (!validFileTypes.contains(fileType)) {
            throw new InvalidFileTypeException("Invalid file type '" + fileType + "'");
        }
    }

    private RequestBody buildFileUpload() {
        File file = new File(filePath);
        MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");

        // Build Form Body
        return new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.getName(), RequestBody.create(file, MEDIA_TYPE))
            .addFormDataPart("type", fileType)
            .build();
    }
}
