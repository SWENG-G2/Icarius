package icarius.entities;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Node;

import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.auth.User;
import java.util.HashMap;


public class Campus extends ServerEntity {

    // Create campus object from existing database entry
    public Campus(Long Id, String name) {
        super(Id);
    }

    // Create campus object and create new database entry
    public Campus(String name, User user) {
        super(name, user);
    }

    public Campus(Long id) {
        super(id);
    }

    @Override
    protected Long create(User user) {
        return create("/api/campus/new", user);
    }

    @Override
    protected String read() {
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/" + Id);
        String response = request.send();

        if (response == null) {
            return null;
        } else {
            // Fetch name from XML response
            try {
                Document document = DocumentHelper.parseText( response );
                response = document.selectSingleNode("//*[name()='title']").getText();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
    
            return response;
        }
    }

    @Override
    protected void update(User user) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", this.name);
        PatchRequest request = new PatchRequest("/api/campus/update" , user);
        request.addParameters(params);
        request.send();
    }

    @Override
    protected Boolean delete(User user) {
        return delete("/api/campus/remove", user);
    }

    public class CampusList {
        private List<Campus> campusList = new ArrayList<Campus>();

        public CampusList() {
            // send and store GET request response
            GetRequest request = new GetRequest("/campus/list");
            String response = request.send();

            // Parse XML response into campus object list
            try {
                Document document = DocumentHelper.parseText(response);

                List<Node> nodes = document.selectNodes("//*[name()='slide']");
                for (Node node : nodes) {
                    int id = Integer.parseInt(node.valueOf("@title"));
                    String name = node.selectSingleNode("*[name()='text']").getText();
                    campusList.add(new Campus(Id, name));
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        public List<Campus> getCampusList() {
            return campusList;
        }
    }

    @Override
    public String toString() {
        return "\nId: " + Id + "\t\tCampus: " + name;
    }
}
