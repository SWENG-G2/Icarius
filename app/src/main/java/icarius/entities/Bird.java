package icarius.entities;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    public Bird(Long Id, User user) {
        super(Id);
    }

    public long getId() {
        return Id;
    }

    @Override
    protected Long create(User user) {
        return create("/api/birds/" + user.getCampusIdString() + "/new", user);
    }

    @Override
    public String read() {
        GetRequest request = new GetRequest("/bird/" + Id);
        String response = request.send();
        List birdInformationValues = new ArrayList(); // stores values read from XML

        if (response == null) {
            return null;
        } else {
            // Fetch bird information from response
            try {
                Document document = DocumentHelper.parseText(response);
                Element root = document.getRootElement();
                // iterate through child elements of presentation with element name "slide"
                for (Iterator<Element> it = root.elementIterator("slide"); it.hasNext();) {
                    Element slide = it.next();
                    // iterate through child elements of each slide
                    for (Iterator<Element> it2 = slide.elementIterator(); it2.hasNext();) {
                        Element node = it2.next();
                        // picks out the text from 'text' nodes
                        if (node.getName().equals("text")) {
                            birdInformationValues.add(node.getData());
                        }
                        // iterate through attributes of each node
                        for (Iterator<Attribute> it3 = node.attributeIterator(); it3.hasNext();) {
                            Attribute attribute = it3.next();
                            // picks out the url's from relevant nodes
                            if (attribute.getName().equals("url")) {
                                birdInformationValues.add(attribute.getData());
                            }
                        }
                    }
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            // adds all elements to the object
            this.name = birdInformationValues.get(0).toString();
            this.soundURL = birdInformationValues.get(1).toString();
            this.heroImageURL = birdInformationValues.get(2).toString();
            this.aboutMeVideoURL = birdInformationValues.get(3).toString();
            this.aboutMe = birdInformationValues.get(4).toString();
            this.dietImageURL = birdInformationValues.get(5).toString();
            this.diet = birdInformationValues.get(6).toString();
            this.locationImageURL = birdInformationValues.get(7).toString();
            this.location = birdInformationValues.get(8).toString();
            
            return this.name;
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
        return "\nId: " + Id + "\t\t Bird: " + name
                // + "\n\t\t Campus: " + campus.name
                + "\n\t\t heroImageURL: " + heroImageURL
                + "\n\t\t soundUrl: " + soundURL
                + "\n\t\t aboutMe: " + aboutMe
                + "\n\t\t aboutMeVideoURL: " + aboutMeVideoURL
                + "\n\t\t location: " + location
                + "\n\t\t locationImageURL: " + locationImageURL
                + "\n\t\t diet: " + diet
                + "\n\t\t dietImageURL: " + dietImageURL;
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
