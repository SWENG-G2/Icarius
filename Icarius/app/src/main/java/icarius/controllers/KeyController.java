package icarius.controllers;

import java.util.HashMap;

import icarius.App;
import icarius.services.HttpService;
import icarius.services.KeyStorageService;

public class KeyController {
    public static String generateKey(boolean admin, String ownerName) {
        // Request and store new key from server
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("admin", String.valueOf(admin));
        parameters.put("ownerName", ownerName);

        String response = HttpService.post(App.BASE_URL + "api/apikeys/new", parameters);

        // Process response
        String[] temp = response.split(":");
        String identity = temp[0];
        String publicKey = removeLastCharacter(temp[1]);
        
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
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("identity", identity);
        String response = HttpService.delete(App.BASE_URL + "api/apikeys/remove", parameters);

        System.out.println("SERVER: " + response);
    }

    private static String removeLastCharacter(String s) {
        if (s == null || s.length() == 0) {
            return null;
        } else {
            return s.substring(0, s.length() - 1);
        }
    }
}
