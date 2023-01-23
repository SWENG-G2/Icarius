package icarius;

import http.ServerRequest;
import http.HttpService;
import icarius.controllers.KeyController;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public static final String currentIdentity = "a";

    public App() {
    }

    public static void main(String[] args) {
       App app = new App();
       //testPOST();
       //testGET();
       //testFileUpload();
       //testKeyGen();
    }

    private static void testGET() {
        ServerRequest test = new ServerRequest("/api/campus/all");
        test.addSysAdminAuth(currentIdentity);
        System.out.println( HttpService.get(test) );
    }

    private static void testPOST() {
        ServerRequest test = new ServerRequest("/api/campus/new");
        test.addSysAdminAuth(currentIdentity);
        test.addParameter("name", "testnumber1mil");
        System.out.println( HttpService.post(test) );
    }

    private static void testFileUpload() {
        ServerRequest test = new ServerRequest("/api/file/1/new");
        test.addAuth(currentIdentity, "1");
        test.addFile("app/src/main/resources/test.jpg", "image");
        System.out.println( HttpService.post(test) );
    }

    private static void testKeyGen() {
        KeyController.generateKey(true, "OWNER_NAME");
        KeyController.removeKey("identity of generated key here");
    }
}
