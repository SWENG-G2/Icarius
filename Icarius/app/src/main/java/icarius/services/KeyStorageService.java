package icarius.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KeyStorageService {
    // store key where file name is identity
    // load key with identity
    // create key

    private static final String KEYS_FOLDER = "keys";

    public static boolean storeKey(String identity, String key) {
        try {
            // If folder does not exist, create it
            Files.createDirectories(Paths.get(KEYS_FOLDER));

            // Store key with name = identity
            Path destinationPath = Paths.get(KEYS_FOLDER, identity);
            Files.write(destinationPath, key.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] loadKey(String identity) {
        Path sourcePath = Paths.get(KEYS_FOLDER, identity);
        try {
            return Files.readAllBytes(sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static boolean removeKey(String identity) {
        Path sourcePath = Paths.get(KEYS_FOLDER, identity);

        try {
            Files.delete(sourcePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
