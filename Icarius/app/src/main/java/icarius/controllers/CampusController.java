package icarius.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import icarius.App;
import icarius.entities.Campus;
import icarius.services.HttpService;

public class CampusController {

    public static void createCampus(String name) {
        // create args list
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("name", name);

        // send post
        HttpService.post(App.BASE_URL + "api/campus/new", args);
    }

    public static void removeCampus(int id) {
        // create args list
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("id", Integer.toString(id));

        // send post
        HttpService.post(App.BASE_URL + "api/campus/new", args);
    }

    public static Campus getCampusById(int id) {
        // send and store GET request response
        String response = HttpService.get(App.BASE_URL + "campus/" + id);

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
        String response = HttpService.get(App.BASE_URL + "campus/list");

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
