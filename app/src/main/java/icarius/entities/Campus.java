package icarius.entities;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import icarius.auth.User;
import java.util.HashMap;

@Data
@NoArgsConstructor
public class Campus implements ServerActions {
    private Long id;
    private String name;
    private List<Bird> birds;
    private List<Long> birdIds;

    @Override
    public Long create(User user, PostRequest request) {
        if (name == null) {
            throw new RuntimeException("Campus name not set");
        }

        // Send create campus request to server
        if (request == null) {
            request = new PostRequest();
        }
        request.setUrl("/api/campus/new");
        request.setUser(user);
        request.addParameter("name", name);
        String response =  request.send();

        // Print response and return created campus Id
        System.out.println(response);
        response = response.replaceAll("[^0-9]", "");
        this.id = Long.valueOf(response);
        return this.id;
    }

    @Override
    public String read(GetRequest request) {
        if (id == null) {
            throw new RuntimeException("Campus id not set");
        }

        if (request == null) {
            request = new GetRequest();
        }

        this.birdIds = new ArrayList<>();
        // send and store GET request response
        request.setUrl("/campus/" + id);
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
                    String birdId = slide.attributeValue("title");
                    birdIds.add(Long.parseLong(birdId));
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            
            return this.name;
        }
    }

    private void fetchBirds(GetRequest request) {
        if (request == null) {
            request = new GetRequest();
        }

        this.birds = new ArrayList<>();
        for (Long birdId : birdIds) {
            Bird newBird = new Bird();
            newBird.setId(birdId);
            newBird.read(request);
            this.birds.add(newBird);
        }
    }
    
    @Override
    public void update(User user, PatchRequest request) {
        if (id == null) {
            throw new RuntimeException("Campus id not set");
        }

        if (request == null) {
            request = new PatchRequest();
        }
        request.setUrl("/api/campus/update");
        request.setUser(user);

        HashMap<String, String> params = new HashMap<>();
        params.put("name", this.name);
        request.addParameters(params);

        request.send();
    }

    @Override
    public Boolean delete(User user, DeleteRequest request) {
        if (id == null) {
            throw new RuntimeException("Campus id not set");
        }

        if (request == null) {
            request = new DeleteRequest();
        }

        request.setUrl("/api/campus/remove");
        request.setUser(user);

        request.addParameter("id", String.valueOf(id));
        String response = request.send();

        System.out.println( response );
        return response.contains("removed") ? true : false;
    }

    @Override
    public String toString() {
        return "\nId: " + id + "\t\tCampus: " + name;
    }
}
