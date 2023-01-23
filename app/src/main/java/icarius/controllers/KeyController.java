package icarius.controllers;

import http.ServerRequest;
import http.HttpService;
import icarius.App;
import icarius.services.KeyStorageService;
import icarius.services.UtilService;

public class KeyController {
    public static String generateKey(boolean admin, String ownerName) {
        // Request and store new key from server        
        ServerRequest request = new ServerRequest("/api/apikeys/new");
        request.addSysAdminAuth(App.currentIdentity);
        request.addParameter("admin", String.valueOf(admin));
        request.addParameter("ownerName", ownerName);
        String response = HttpService.post( request );

        // Process response
        String[] temp = response.split(":");
        String identity = temp[0];
        String publicKey = UtilService.removeLastCharacter(temp[1]);
        
        KeyStorageService.storeKey(identity, publicKey);

        System.out.println("New key generated:"
                            + "\n\tIdentity:     " + identity
                            + "\n\tAdmin:        " + String.valueOf(admin)
                            + "\n\tPublic Key:   " + publicKey);

        return identity;
    }

    public static void removeKey(String identity) {
        // Remove key from local memory
        if(KeyStorageService.removeKey(identity)) {
            System.out.println("Icarius: Key " + identity + " deleted.");
        } else {
            System.out.println("Icarius: Key " + identity + " was not found...");
        }

        // Request to remove key from server
        ServerRequest request = new ServerRequest("/api/apikeys/remove");
        request.addSysAdminAuth(App.currentIdentity);
        request.addParameter("identity", identity);
        String response = HttpService.delete(request);

        System.out.println("SERVER: " + response);
    }
}
