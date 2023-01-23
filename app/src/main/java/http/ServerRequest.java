package http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import icarius.App;
import icarius.services.AuthenticationService;
import okhttp3.HttpUrl;

public class ServerRequest {
    private String url;
    protected String identity, campusID;
    protected String filePath, fileType;
    private HashMap<String, String> params = new HashMap<String, String>();
    
    // valid file types
    private static final List<String> validFileTypes = Collections.unmodifiableList(
        new ArrayList<String>() {{
            add("image");
            add("audio");
            add("video");
        }}
    );

    public ServerRequest(String urlPath) {
        this.url = App.BASE_URL + urlPath;
    }

    public void addAuth(String identity, String campusID) {
        this.identity = identity;
        this.campusID = campusID;
    }

    public void addSysAdminAuth(String identity) {
        this.identity = identity;
        campusID = "admin";
    }

    public void addParameter(String key, String value) {
        params.put(key, value);
    }

    public void addFile(String filePath, String fileType) {
        validateFileType(fileType);
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public String getUrl() {
        HttpUrl.Builder urlB = HttpUrl.parse(url).newBuilder();
        params.forEach((key, value) -> urlB.addQueryParameter(key, value));
        return urlB.build().toString();
    }

    public boolean containsFile() {
        return !(filePath == null || filePath.isBlank());
    }

    public String getKey() {
        return AuthenticationService.getAuth(identity, campusID);
    }

    private void validateFileType(String fileType) {
        if (!validFileTypes.contains(fileType)) {
            throw new InvalidFileTypeException("Invalid file type '" + fileType + "'");
        }
    }
}
