package icarius.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import icarius.App;

public class FileUploadService {
    // Declare valid file types
    private static final List<String> validFileTypes = Collections.unmodifiableList(
        new ArrayList<String>() {{
            add("image");
            add("audio");
            add("video");
        }}
    );

    /**
     * Validate file type
     * Throws InvalidFileTypeException if file type invalid
     * @param fileType - String containing file type
     */
    private static void validateFileType(String fileType) {
        if (!validFileTypes.contains(fileType)) {
            throw new InvalidFileTypeException("Invalid file type '" + fileType + "'");
        }
    }

    /**
     * Upload file to server
     * @param filePath - The path to the file to upload
     * @param fileType - The file type
     * @return
     */
    public static void uploadFile(String filePath, String fileType) {
        validateFileType(fileType);

        // create args list
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("type", fileType);

        HttpService.postFile(App.BASE_URL + "api/file/new", args, filePath);
    }
}
