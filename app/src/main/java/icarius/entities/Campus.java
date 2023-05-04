package icarius.entities;

import java.util.ArrayList;
import java.util.List;

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
import okhttp3.OkHttpClient;
import icarius.auth.User;
import java.util.HashMap;

@Data
public class Campus implements ServerActions {
    private Long id;
    private String name;
    private List<Bird> birds;

    private final OkHttpClient okHttpClient;

    public Campus(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Long create(User user, PostRequest request) {
        if (name == null) {
            throw new RuntimeException("Campus name not set");
        }

        // Send create campus request to server
        if (request == null) {
            request = new PostRequest("/api/campus/new", user);
        }
        request.addParameter("name", name);
        String response =  request.send().getBody();

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
            request = new GetRequest("/campus/" + id, okHttpClient);
        }

        this.birds = new ArrayList<>();
        // send and store GET request response
        String response = request.send().getBody();

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

                    // fetch bird
                    Bird bird = new Bird(okHttpClient);
                    bird.setId(Long.parseLong(birdId));
                    bird.setCampusId(id);
                    bird.read(null);
                    birds.add( bird );
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            
            return this.name;
        }
    }
    
    @Override
    public void update(User user, PatchRequest request) {
        if (id == null) {
            throw new RuntimeException("Campus id not set");
        }

        if (request == null) {
            request = new PatchRequest("/api/campus/edit", user, okHttpClient);
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("newName", this.name);
        params.put("id", this.getId()+"");
        request.addParameters(params);

        request.send();
    }

    @Override
    public Boolean delete(User user, DeleteRequest request) {
        if (id == null) {
            throw new RuntimeException("Campus id not set");
        }

        if (request == null) {
            request = new DeleteRequest("/api/campus/remove", user, okHttpClient);
        }

        request.addParameter("id", String.valueOf(id));
        String response = request.send().getBody();

        System.out.println( "deletion says: " + response );
        return (response != null) ? true : false;
    }

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
        return "\nId: " + id + "\t\tCampus: " + name;
    }
}
