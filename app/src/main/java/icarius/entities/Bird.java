package icarius.entities;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import icarius.http.PatchRequest;
import icarius.http.GetRequest;
import icarius.user.User;

public class Bird extends ServerEntity {
    // Bird information
    public String heroImageURL;
    public String soundURL;
    public String aboutMe;
    public String aboutMeVideoURL;
    public String location;
    public String locationImageURL;
    public String diet;
    public String dietImageURL;
    
    public Campus campus;

    public Bird(Long Id, String name, User user) {
        super(Id);
    }

    public Bird(String name, User user) {
        super(name, user);
    }

    @Override
    protected Long create(User user) {
        return create("/api/birds/" + user.getCampusIdString() + "/new", user);
    }

    @Override
    protected String read() {
        GetRequest request = new GetRequest("/api/birds/" + Id);
        String response = request.send();

        if (response == null) {
            return null;
        } else {
            // Fetch bird information from response
            try {
                Document document = DocumentHelper.parseText(response);
                heroImageURL = document.selectSingleNode("//*[name()='heroImageURL']").getText();
                soundURL = document.selectSingleNode("//*[name()='soundURL']").getText();
                aboutMe = document.selectSingleNode("//*[name()='aboutMe']").getText();
                aboutMeVideoURL = document.selectSingleNode("//*[name()='aboutMeVideoURL']").getText();
                location = document.selectSingleNode("//*[name()='location']").getText();
                locationImageURL = document.selectSingleNode("//*[name()='locationImageURL']").getText();
                diet = document.selectSingleNode("//*[name()='diet']").getText();
                dietImageURL = document.selectSingleNode("//*[name()='dietImageURL']").getText();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            return response;
        }
    }

    @Override
    protected void update(User user) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("heroImageURL", heroImageURL);
        parameters.put("soundURL", soundURL);
        parameters.put("aboutMe", aboutMe);
        parameters.put("aboutMeVideoURL", aboutMeVideoURL);
        parameters.put("location", location);
        parameters.put("locationImageURL", locationImageURL);
        parameters.put("diet", diet);
        parameters.put("dietImageURL", dietImageURL);

        PatchRequest request = new PatchRequest("/api/birds/" + campus.Id + "/edit", user);
        request.addParameters(parameters);
        request.send();
    }

    @Override
    protected Boolean delete(User user) {
        return delete("/api/birds/" + campus.Id + "/remove", user);
    }

    @Override
    public String toString() {
        return "\nId: " + Id + "\t\tBird: " + name
                    + "\n\t Campus: " + campus.name
                    + "\n\t heroImageURL: " + heroImageURL
                    + "\n\t soundUrl: " + soundURL
                    + "\n\t aboutMe: " + aboutMe
                    + "\n\t aboutMeVideoURL: " + aboutMeVideoURL
                    + "\n\t location: " + location
                    + "\n\t locationImageURL: " + locationImageURL
                    + "\n\t diet: " + diet
                    + "\n\t dietImageURL: " + dietImageURL;
    }

    public class BirdList {
        private List<Bird> birdList = new ArrayList<Bird>();

        public BirdList(User user) {
            // send and store GET request response
            GetRequest request = new GetRequest("/birds/list/" + user.getCampusIdString());
            String response = request.send();

            // Parse XML response into bird object list
            try {
                Document document = DocumentHelper.parseText(response);

                List<Node> nodes = document.selectNodes("//*[name()='bird']");
                for (Node node : nodes) {
                    int id = Integer.parseInt(node.valueOf("@title"));
                    String name = node.selectSingleNode("*[name()='text']").getText();
                    birdList.add(new Bird(Id, name, user));
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        public List<Bird> getBirdList() {
            return birdList;
        }
    }

}
