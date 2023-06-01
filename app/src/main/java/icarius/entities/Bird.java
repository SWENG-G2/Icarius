package icarius.entities;

import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import icarius.auth.UserClient;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import lombok.Data;

@Data
public class Bird implements ServerActions {
    // Bird information
    private Long id;
    private Long campusId;
    private String name;
    private String listImageURL;
    private String heroImageURL;
    private String soundURL;
    private String aboutMe;
    private String aboutMeVideoURL;
    private String location;
    private String locationImageURL;
    private String diet;
    private String dietImageURL;

    private UserClient user;

    public Bird(UserClient user) {
        this.user = user;
    }

    @Override
    public Boolean create(UserClient user, PostRequest request) {
        // If required field not set, throw exception
        if (name == null)
            throw new RuntimeException("Bird name not set");

        // Send create bird request to server
        if (request == null)
            request = new PostRequest("/api/birds/" + campusId + "/new", user);

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("listImageURL", listImageURL);
        parameters.put("heroImageURL", heroImageURL);
        parameters.put("soundURL", soundURL);
        parameters.put("aboutMe", aboutMe);
        parameters.put("aboutMeVideoURL", aboutMeVideoURL);
        parameters.put("location", location);
        parameters.put("locationImageURL", locationImageURL);
        parameters.put("diet", diet);
        parameters.put("dietImageURL", dietImageURL);
        request.addParameters(parameters);

        ServerResponse response = request.send();

        // Print response and return created bird Id
        String responseBody = response.getBody().replaceAll("[^0-9]", "");
        this.id = Long.valueOf(responseBody);
        return response.isSuccessful();
    }

    @Override
    public Boolean read(GetRequest request) {
        // If required field not set, throw exception
        if (id == null) {
            throw new RuntimeException("Bird id not set");
        }

        // Send read bird request to server
        if (request == null)
            request = new GetRequest("/bird/" + id, user);

        String slideTitle;
        String nodeTitle;
        ServerResponse response = request.send();
        String responseBody = response.getBody();

        if (responseBody == null || responseBody.isEmpty() || !response.isSuccessful()) {
            return false;
        } else {
            // Fetch bird information from response
            try {
                Document document = DocumentHelper.parseText(response.getBody());
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

            return true;
        }
    }

    @Override
    public Boolean update(UserClient user, PatchRequest request) {
        // If required field not set, throw exception
        if (id == null)
            throw new RuntimeException("Bird id not set");

        // Send update bird request to server
        if (request == null)
            request = new PatchRequest("/api/birds/" + campusId + "/edit", user);

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", this.name);
        parameters.put("heroImageURL", heroImageURL);
        parameters.put("listImageURL", listImageURL);
        parameters.put("soundURL", soundURL);
        parameters.put("aboutMe", aboutMe);
        parameters.put("aboutMeVideoURL", aboutMeVideoURL);
        parameters.put("location", location);
        parameters.put("locationImageURL", locationImageURL);
        parameters.put("diet", diet);
        parameters.put("dietImageURL", dietImageURL);
        request.addParameters(parameters);

        // Return TRUE if update request success, else FALSE
        return request.send().isSuccessful();
    }

    @Override
    public Boolean delete(UserClient user, DeleteRequest request) {
        // If required field not set, throw exception
        if (id == null)
            throw new RuntimeException("Bird id not set");

        // Send delete bird request to server
        if (request == null)
            request = new DeleteRequest("/api/birds/" + campusId + "/remove", user);

        request.addParameter("id", String.valueOf(id));
        ServerResponse response = request.send();

        // Return TRUE if delete request success, else FALSE
        return response.isSuccessful();
    }

    @Override
    public String toString() {
        return "\nId: " + id + "\t\t Bird: " + name
                + "\n\t\t campusId: " + campusId
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
