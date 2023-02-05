package icarius.controllers;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.user.User;
import icarius.entities.Campus;

public class CampusController {

    public static void createCampus(String name, User user) {
        PostRequest request = new PostRequest("/api/campus/new", user);
        request.addParameter("name", name);
        System.out.println( request.send() );
    }

    public static void removeCampus(int id, User user) {        
        PostRequest request = new PostRequest("/api/campus/new", user);
        request.addParameter("id", Integer.toString(id));
        System.out.println( request.send() );
    }

    public static Campus getCampusById(Long id) {
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/" + id);
        String response = request.send();

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
    
            return new Campus(id);
        }
    }

    public static List<Campus> getCampusList() {
        List<Campus> campusList = new ArrayList<Campus>();

        // send and store GET request response
        GetRequest request = new GetRequest("/campus/list");
        String response = request.send();

        // Parse XML response into campus object list
        try {
            Document document = DocumentHelper.parseText( response );

            List<Node> nodes = document.selectNodes("//*[name()='slide']");
            for (Node node : nodes) {
                Long id = Long.parseLong(node.valueOf("@title"));
                String name = node.selectSingleNode("*[name()='text']").getText();
                campusList.add(new Campus(id));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return campusList;
    }
    
}
