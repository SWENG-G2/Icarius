package icarius.entities;

import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import lombok.Data;
import icarius.auth.UserClient;
import java.util.HashMap;

@Data
public class Campus implements ServerActions {
    private Long id;
    private String name;
    public List<Bird> birds;

    private UserClient user;

    /**
     * @param user
     */
    public Campus(UserClient user) {
        this.user = user;
    }

    @Override
    public Boolean create(UserClient user, PostRequest request) {
        // If required field not set, throw exception
        if (name == null)
            throw new RuntimeException("Campus name not set");

        // Send create campus request to server
        if (request == null)
            request = new PostRequest("/api/campus/new", user);

        request.addParameter("name", name);
        ServerResponse response = request.send();

        // Print response and return created campus Id
        // System.out.println(response.getBody());
        String idString = response.getBody().replaceAll("[^0-9]", "");
        this.id = Long.valueOf(idString);

        // Return TRUE if create success, else FALSE
        return response.isSuccessful();
    }

    @Override
    public Boolean read(GetRequest request) {
        // If required field not set, throw exception
        if (id == null)
            throw new RuntimeException("Campus id not set");

        // Send read campus request to server
        if (request == null)
            request = new GetRequest("/campus/" + id, user);

        // send and store GET request response
        this.birds = new ArrayList<>();
        ServerResponse response = request.send();
        String responseBody = response.getBody();

        if (responseBody == null || responseBody.isEmpty() || !response.isSuccessful()) {
            // If no response or response failed
            return false;
        } else {
            // Fetch name from XML response
            try {
                Document document = DocumentHelper.parseText(responseBody);
                Element root = document.getRootElement();
                Element infoSlide = root.element("info");
                this.name = infoSlide.element("title").getData().toString();

                // iterate through child elements of presentation with element name "slide"
                for (Iterator<Element> it = root.elementIterator("slide"); it.hasNext();) {
                    Element slide = it.next();
                  
                    // Bird info
                    String birdId = slide.attributeValue("title");
                    String listImageUrl = "";
                    String birdName = "";

                    // Get Bird listImageUrl
                    for (Iterator<Element> it2 = slide.elementIterator(); it2.hasNext();) {
                        Element node = it2.next();

                        // Get Bird name
                        if (slide.indexOf(node) == 1) {
                            birdName = node.getData().toString();
                        }

                        // Get bird list image URL
                        if (node.getName().equals("image")) {
                            for (Iterator<Attribute> it3 = node.attributeIterator(); it3.hasNext();) {
                                Attribute attribute = it3.next();
                                if (attribute.getName().equals("url")) {
                                    listImageUrl = attribute.getData().toString();
                                }
                            }
                        }
                    }

                    // fetch and add bird
                    Bird bird = new Bird(user);
                    bird.setName(birdName);
                    bird.setId(Long.parseLong(birdId));
                    bird.setCampusId(id);
                    bird.setListImageURL(listImageUrl);
                    birds.add(bird);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    @Override
    public Boolean update(UserClient user, PatchRequest request) {
        // If required field not set, throw exception
        if (id == null)
            throw new RuntimeException("Campus id not set");

        // Send update campus request to server
        if (request == null)
            request = new PatchRequest("/api/campus/edit", user);

        HashMap<String, String> params = new HashMap<>();
        params.put("newName", this.name);
        params.put("id", this.getId() + "");
        request.addParameters(params);

        // Return TRUE if update request success, else FALSE
        return request.send().isSuccessful();
    }

    @Override
    public Boolean delete(UserClient user, DeleteRequest request) {
        // If required field not set, throw exception
        if (id == null)
            throw new RuntimeException("Campus id not set");

        // Send delete campus request to server
        if (request == null)
            request = new DeleteRequest("/api/campus/remove", user);

        request.addParameter("id", String.valueOf(id));
        ServerResponse response = request.send();

        // Return TRUE if delete request success, else FALSE
        return response != null ? response.isSuccessful() : false;
    }

    /**
     * Finds a bird from its name and returns it.
     * 
     * @param birdName
     * @return
     */
    public Bird getBird(String birdName) {
        for (Bird bird : birds) {
            if (bird.getName().equals(birdName)) {
                return bird;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
