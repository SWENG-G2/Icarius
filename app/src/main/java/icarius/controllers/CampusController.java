package icarius.controllers;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import http.ServerRequest;
import http.HttpService;
import icarius.App;
import icarius.entities.Campus;

public class CampusController {

    public static void createCampus(String name) {
        ServerRequest request = new ServerRequest("/api/campus/new");
        request.addSysAdminAuth(App.currentIdentity);
        request.addParameter("name", name);
        System.out.println( HttpService.post(request) );
    }

    public static void removeCampus(int id) {        
        ServerRequest request = new ServerRequest("/api/campus/new");
        request.addSysAdminAuth(App.currentIdentity);
        request.addParameter("id", Integer.toString(id));
        System.out.println( HttpService.post(request) );
    }

    public static Campus getCampusById(int id) {
        // send and store GET request response
        ServerRequest request = new ServerRequest("/campus/" + id);
        String response = HttpService.get(request);

        if (response == null) {
            return null;
        } else {
            // Parse XML response into campus object
            try {
                Document document = DocumentHelper.parseText( response );
                response = document.selectSingleNode("//*[name()='title']").getText();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
    
            return new Campus(id, response);
        }
    }

    public static List<Campus> getCampusList() {
        List<Campus> campusList = new ArrayList<Campus>();

        // send and store GET request response
        ServerRequest request = new ServerRequest("/campus/list");
        String response = HttpService.get(request);

        // Parse XML response into campus object list
        try {
            Document document = DocumentHelper.parseText( response );

            List<Node> nodes = document.selectNodes("//*[name()='slide']");
            for (Node node : nodes) {
                int id = Integer.parseInt(node.valueOf("@title"));
                String name = node.selectSingleNode("*[name()='text']").getText();
                campusList.add(new Campus(id, name));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return campusList;
    }
    
}
