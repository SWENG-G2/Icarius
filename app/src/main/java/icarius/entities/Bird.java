package icarius.entities;

import java.util.Date;

public class Bird {
    public Long id;

    // Bird information
    public String name;
    public String heroImageURL;
    public String soundURL;
    public String aboutMe;
    public String aboutMeVideoURL;
    public String location;
    public String locationImageURL;
    public String diet;
    public String dietImageURL;
    
    public Campus campus;

    public String author;
    public Date date;

    public Bird(Long id) {
        this.id = id;
        // get existing bird info using id
    }

    public Bird(String name) {
        // if no id passed, create new bird with name 
    }

    private void getBirdById(Long id) {
        // sends get request to server
        // populates bird entity with details
    }

    private void newBird(String name) {
        // sends request to server to make bird with name name
    }

    private void editBird() {
        // whenever a change is made to the bird object,
        // send a request to also update the bird on the server
    }
}
