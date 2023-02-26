package icarius.controllers;

import icarius.http.DeleteRequest;
import icarius.http.PostRequest;
import icarius.services.UtilService;
import icarius.user.User;

public class KeyController {
    public static String generateKey(boolean admin, String ownerName, User user) {
        // Request and store new key from server        
        PostRequest request = new PostRequest("/api/apikeys/new", user);
        request.addParameter("admin", String.valueOf(admin));
        request.addParameter("ownerName", ownerName);
        String response = request.send();

        // Process response
        String[] temp = response.split(":");
        String identity = temp[0];
        String publicKey = UtilService.removeLastCharacter(temp[1]);
        
        //KeyStorageService.storeKey(identity, publicKey);

        System.out.println("New key generated:"
                            + "\n\tIdentity:     " + identity
                            + "\n\tAdmin:        " + String.valueOf(admin)
                            + "\n\tPublic Key:   " + publicKey);

        return identity;
    }

    public static void removeKey(String identity, User user) {
        // Remove key from local memory
        // if(KeyStorageService.removeKey(identity)) {
        //     System.out.println("Icarius: Key " + identity + " deleted.");
        // } else {
        //     System.out.println("Icarius: Key " + identity + " was not found...");
        // }

        // Request to remove key from server
        DeleteRequest request = new DeleteRequest("/api/apikeys/remove", user);
        request.addParameter("identity", identity);
        String response = request.send();

        System.out.println("SERVER: " + response);
    }
}
