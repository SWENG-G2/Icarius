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

    public Campus(UserClient user) {
        this.user = user;
    }

    /**
     * Creates a new post request, adds the campus name to the request,
     * sends the request and gets a response of the campus' id.
     */
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
        System.out.println(response.getBody());
        String idString = response.getBody().replaceAll("[^0-9]", "");
        this.id = Long.valueOf(idString);

        // Return TRUE if create success, else FALSE
        return response.isSuccessful();
    }

    /**
     * Creates a new get request, gets an xml response, parse it to then get the id
     * and
     * the list image of the birds in the campus. Then, BLBABLALBAL
     */
    @Override
    public Boolean read(GetRequest request) {
        System.out.println("request");
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

                    // Get Bird listImageUrl
                    for (Iterator<Element> it2 = slide.elementIterator(); it2.hasNext();) {
                        Element node = it2.next();

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
                    addBirdToBirds(Long.parseLong(birdId), listImageUrl, null);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    /**
     * Takes an id, list image url and a request, cretaes a bird, adds the id and
     * list image url to the parameters and adds that bird to the birds list.
     * 
     * @param birdId
     * @param listImageUrl
     * @param request
     */
    protected void addBirdToBirds(Long birdId, String listImageUrl, GetRequest request) {
        Bird bird = new Bird(user);
        bird.setId(birdId);
        bird.setCampusId(id);
        bird.setListImageURL(listImageUrl);
        bird.read(request);
        birds.add(bird);
    }

    /**
     * Puts all the current (updated) parameters of the campus into a patch request
     * then sends the request to the server.
     */
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

    /**
     * It gets the id of the campus off the server and sends a delete request.
     * Returns true if successful.
     */
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
