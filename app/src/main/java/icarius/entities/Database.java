package icarius.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import icarius.auth.User;
import icarius.http.GetRequest;
import lombok.Getter;

public class Database {
    private @Getter List<Campus> database;

    public Database(User user) {
        refresh(user);
    }

    public void refresh(User user) {
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/list", user.getOkHttpClient());
        String response = request.send().getBody();

        if (response != null) {
            String campusId;
            List<Campus> updatedList = new ArrayList<>();
            // Fetch name from XML response
            try {
                Document document = DocumentHelper.parseText(response);
                Element root = document.getRootElement();
                // iterate through child elements of presentation with element name "slide"
                for (Iterator<Element> it = root.elementIterator("slide"); it.hasNext();) {
                    Element slide = it.next();
                    Campus newCampus = new Campus(user.getOkHttpClient());
                    campusId = slide.attributeValue("title");
                    newCampus.setId(Long.parseLong(campusId));
                    newCampus.read(null);
                    updatedList.add(newCampus);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            
            this.database = updatedList;
        }
    }

    public Campus getCampus(String campusName) {
        for (Campus campus : database) {
            if (campus.getName().equals(campusName)) {
                return campus;
            }
        }
        return null;
    }

    
}