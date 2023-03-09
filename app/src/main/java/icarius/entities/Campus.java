package icarius.entities;

import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

import org.dom4j.Node;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.Element;

import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.auth.User;
import java.util.HashMap;


public class Campus extends ServerEntity {

    public List<Bird> birds;

    // Create campus object from existing database entry
    public Campus(Long Id, String name) {
        super(Id);
    }

    public Campus(Long id) {
        super(id);
    }

    protected Long create(User user) {
        return create("/api/campus/new", user);
    }

    protected void update(User user) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", this.name);
        PatchRequest request = new PatchRequest("/api/campus/update" , user);
        request.addParameters(params);
        request.send();
    }

    protected Boolean delete(User user) {
        return delete("/api/campus/remove", user);
    }

    @Override
    public String read() {
        this.birds = new ArrayList<>();
        String birdId;
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/" + Id);
        String response = request.send();

        if (response == null) {
            return null;
        } else {
            // Fetch name from XML response
            try {
                Document document = DocumentHelper.parseText(response);
                Element root = document.getRootElement();
                Element infoSlide = root.element("info");
                this.name = infoSlide.element("title").getData().toString();
                // iterate through child elements of presentation with element name "slide"
                for (Iterator<Element> it = root.elementIterator("slide"); it.hasNext();) {
                    Element slide = it.next();
                    birdId = slide.attributeValue("title");
                    Bird newBird = new Bird(Long.parseLong(birdId));
                    newBird.read();
                    this.birds.add(newBird);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            
            return this.name;
        }
    }

    public List<Bird> getBirds(){
        return this.birds;
    }

    // public class CampusList {
    //     private List<Campus> campusList = new ArrayList<Campus>();

    //     public CampusList() {
    //         // send and store GET request response
    //         GetRequest request = new GetRequest("/campus/list");
    //         String response = request.send();

    //         // Parse XML response into campus object list
    //         try {
    //             Document document = DocumentHelper.parseText(response);

    //             List<Node> nodes = document.selectNodes("//*[name()='slide']");
    //             for (Node node : nodes) {
    //                 int id = Integer.parseInt(node.valueOf("@title"));
    //                 String name = node.selectSingleNode("*[name()='text']").getText();
    //                 campusList.add(new Campus(Id, name));
    //             }
    //         } catch (DocumentException e) {
    //             e.printStackTrace();
    //         }
    //     }

    //     public List<Campus> getCampusList() {
    //         return campusList;
    //     }
    // }

    @Override
    public String toString() {
        return "\nId: " + Id + "\t\tCampus: " + name;
    }
}
