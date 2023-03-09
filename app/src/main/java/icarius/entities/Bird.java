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
import icarius.auth.User;

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

    public Bird(Long Id) {
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
        return create("/api/birds/" + campus.getID() + "/new", user);
    }

    @Override
    public String read() {
        String slideTitle;
        String nodeTitle;
        GetRequest request = new GetRequest("/bird/" + Id);
        String response = request.send();

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
                    slideTitle = slide.attributeValue("title");
                    // iterate through child elements of each slide
                    for (Iterator<Element> it2 = slide.elementIterator(); it2.hasNext();) {
                        Element node = it2.next();
                        // picks out the text from 'text' nodes
                        if (node.getName().equals("text")) {
                            switch (slideTitle) {
                                case "heroSlide":
                                    this.name = node.getData().toString();
                                    break;
                                case "About me":
                                    this.aboutMe = node.getData().toString();
                                    break;
                                case "Diet":
                                    this.diet = node.getData().toString();
                                    break;
                                case "Location":
                                    this.location = node.getData().toString();
                                    break;
                                default:
                                    System.out.println("xml problem");
                                    break;
                            }
                        }
                        // iterate through attributes of each node
                        for (Iterator<Attribute> it3 = node.attributeIterator(); it3.hasNext();) {
                            Attribute attribute = it3.next();
                            // picks out the url's from relevant nodes
                            nodeTitle = node.getName();
                            if (attribute.getName().equals("url")) {
                                switch (slideTitle) {
                                    case "heroSlide":
                                        if (nodeTitle.equals("audio")) {
                                            this.soundURL = attribute.getData().toString();
                                        } else if (nodeTitle.equals("image")) {
                                            this.heroImageURL = attribute.getData().toString();
                                        }
                                        break;
                                    case "About me":
                                        this.aboutMeVideoURL = attribute.getData().toString();
                                        break;
                                    case "Diet":
                                        this.dietImageURL = attribute.getData().toString();
                                        break;
                                    case "Location":
                                        this.locationImageURL = attribute.getData().toString();
                                        break;
                                    default:
                                        System.out.println("xml problem");
                                        break;
                                }
                            }
                        }
                    }
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }

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

        PatchRequest request = new PatchRequest("/api/birds/" + campus.getID() + "/edit", user);
        request.addParameters(parameters);
        request.send();
    }

    @Override
    protected Boolean delete(User user) {
        return delete("/api/birds/" + campus.getID() + "/remove", user);
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
}
