package icarius.http;

import java.io.File;
import java.util.List;

import icarius.auth.UserClient;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostRequest extends ServerRequest {
    protected String filePath, fileType;

    // valid file types
    private static final List<String> validFileTypes = Collections.unmodifiableList(
            new ArrayList<String>() {
                {
                    add("image");
                    add("audio");
                    add("video");
                }
            });

    /**
     * @param urlPath
     * @param user
     */
    public PostRequest(String urlPath, UserClient user) {
        super(urlPath, user);
    }

    @Override
    public ServerResponse send() {
        RequestBody requestBody;
        if (containsFile()) {
            requestBody = buildFileUpload();
        } else {
            requestBody = new FormBody.Builder().build();
        }

        request = new Request.Builder()
                .url(getUrl())
                .addHeader("credentials", user.getAuth())
                .post(requestBody)
                .build();

        return execute(request);
    }

    /**
     * Adds a local file to the Server Request
     * @param filePath path to local file
     * @param fileType file type (image, audio, video)
     */
    public void addFile(String filePath, String fileType) {
        validateFileType(fileType);
        this.filePath = filePath;
        this.fileType = fileType;
    }

    /**
     * @return TRUE if Server Request contains a file, else FALSE
     */
    public boolean containsFile() {
        return !(filePath == null || filePath.isBlank());
    }

    /**
     * Throws InvalidFileTypeException if file type is invalid
     * @param fileType
     */
    private void validateFileType(String fileType) {
        if (!validFileTypes.contains(fileType)) {
            throw new InvalidFileTypeException("Invalid file type '" + fileType + "'");
        }
    }

    /**
     * Builds a MultipartBody for the Server Request containing a file
     * @return RequestBody of the Server Request
     */
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
