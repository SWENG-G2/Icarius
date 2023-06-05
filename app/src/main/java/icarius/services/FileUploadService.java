package icarius.services;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import icarius.auth.UserClient;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;

import static icarius.services.FileDetails.*;

public class FileUploadService {
    /**
     * Opens File Browser
     * 
     * @param type
     * @return Local path file of selected file
     */
    public static File selectLocalFile(String type) {
        final JFileChooser fc = new JFileChooser();

        // Configure Filter
        FileNameExtensionFilter filter;
        switch (type) {
            case "image":
                fc.setDialogTitle("Select an Image file");
                filter = new FileNameExtensionFilter("PNG, JPEG, JPG and GIF images", "png", "gif", "jpeg","JPG", "webp");
                break;
            case "audio":
                fc.setDialogTitle("Select an Audio file");
                filter = new FileNameExtensionFilter("MP3, MP4 and WAV files", "MP3", "MP4", "WAV");
                break;
            case "video":
                fc.setDialogTitle("Select a Video file");
                filter = new FileNameExtensionFilter("MP4, MOV, WMV and AVI files", "MP4", "MOV", "WMV","AVI");
                break;
            default:
                filter = null;
                break;
        }
        fc.addChoosableFileFilter(filter);

        // Return Chosen File
        return (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null;
    }

    /**
     * Identify attributes of file parameter
     * 
     * @param fileReqParam
     * @return FileDetails object
     */
    private static FileDetails getFileDetails(String fileReqParam) {
        switch(fileReqParam) {
            case HERO_IMAGE_URL:
                return new FileDetails(IMAGE, true);
            case LIST_IMAGE_URL:
            case DIET_IMAGE_URL:
            case LOCATION_IMAGE_URL:
                return new FileDetails(IMAGE, false);
            case VIDEO_URL:
                return new FileDetails(VIDEO, false);
            case SOUND_URL:
                return new FileDetails(AUDIO, false);
            default:
                return null;
        }
    } 

    /**
     * Upload a file to the server
     * 
     * @param user
     * @param campusId
     * @param localFilePath
     * @param fileReqParam
     * @param request
     * @return URL of uploaded file on server
     */
    public static String uploadFile(UserClient user, Long campusId, String localFilePath, String fileReqParam, PostRequest request) {
        // Create request
        FileDetails fileDetails = getFileDetails(fileReqParam);

        if (request == null) request = new PostRequest("/api/file/" + campusId + "/new", user);
        if (localFilePath == null || localFilePath.isEmpty()) return null;
        request.addFile(localFilePath, fileDetails.getType());
        if (fileDetails.getProcess()) {
            request.addParameter("process", "true");
        }
        ServerResponse response = request.send();
        return response.isSuccessful() ? response.getBody() : null;
    }


}
