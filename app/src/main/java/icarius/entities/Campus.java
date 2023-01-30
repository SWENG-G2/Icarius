package icarius.entities;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.user.User;

public class Campus {
    public long id;
    public String name;

    public Campus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Campus(long id) {
        this.id = id;
        getCampusNameById();
    }

    public Campus(String name, User user) {
        this.name = name;
        createCampus(user);
    }

    public void removeCampus(User user) {
        PostRequest request = new PostRequest("/api/campus/new", user);
        request.addParameter("id", Long.toString(id));
        System.out.println( request.send() );

        // TODO: Warning before removal, should require pressing ok to a confirmation message
        // TODO: if delete successful, set object properties to null
    }

    private void getCampusNameById() {
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/" + id);
        String response = request.send();

        if (response == null) {
            throw new RuntimeException("Campus with id '" + id + "' not found.");
        } else {
            // Parse XML response and find campus name
            try {
                Document document = DocumentHelper.parseText( response );
                this.name = document.selectSingleNode("//*[name()='title']").getText();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void createCampus(User user) {
        PostRequest request = new PostRequest("/api/campus/new", user);
        request.addParameter("name", this.name);
        System.out.println( request.send() );

        // TODO: get returned id and save to object
    }

    @Override
    public String toString() {
        return "\nId: " + id + "\t\tCampus: " + name;
    }
}
