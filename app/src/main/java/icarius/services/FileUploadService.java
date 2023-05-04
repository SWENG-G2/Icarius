package icarius.services;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileUploadService {
    public static File selectLocalFile(String type) {
        final JFileChooser fc = new JFileChooser();

        // Configure Filter
        FileNameExtensionFilter filter;
        switch (type) {
            case "Image":
                fc.setDialogTitle("Select an Image file");
                filter = new FileNameExtensionFilter("PNG, JPEG, JPG and GIF images", "png", "gif", "jpeg","JPG");
                break;
            case "Audio":
                fc.setDialogTitle("Select an Audio file");
                filter = new FileNameExtensionFilter("MP3, MP4 and WAV files", "MP3", "MP4", "WAV");
                break;
            case "Video":
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

        // TODO - (HARRY) investigate following warning that appears when i click file upload button
        // WARNING: An illegal reflective access operation has occurred 
        // WARNING: Illegal reflective access by com.formdev.flatlaf.ui.FlatFileChooserUI$FlatShortcutsPanel (file:/C:/Users/Connall/.gradle/caches/modules-2/files-2.1/com.formdev/flatlaf/3.0/b5410f3f9137febc7d916ca4e0a7e9f6ddeb5b9a/flatlaf-3.0.jar) to method sun.awt.shell.ShellFolder.get(java.lang.String)
        // WARNING: Please consider reporting this to the maintainers of com.formdev.flatlaf.ui.FlatFileChooserUI$FlatShortcutsPanel
        // WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
        // WARNING: All illegal access operations will be denied in a future release
    }
}
