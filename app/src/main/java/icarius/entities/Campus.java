package icarius.entities;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import icarius.http.GetRequest;
import icarius.user.User;

public class Campus extends ServerEntity {

    // Create campus object from existing database entry
    public Campus(Long Id) {
        super(Id);
    }

    // Create campus object and create new database entry
    public Campus(String name, User user) {
        super(name, user);
    }

    @Override
    protected Long create(User user) {
        return create("/api/campus/new", user);
    }

    @Override
    protected String read() {
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/" + Id);
        String response = request.send();

        if (response == null) {
            return null;
        } else {
            // Fetch name from XML response
            try {
                Document document = DocumentHelper.parseText( response );
                response = document.selectSingleNode("//*[name()='title']").getText();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
    
            return response;
        }
    }

    @Override
    protected void update(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Boolean delete(User user) {
        return delete("/api/campus/remove", user);
    }

    @Override
    public String toString() {
        return "\nId: " + Id + "\t\tCampus: " + name;
    }
}
