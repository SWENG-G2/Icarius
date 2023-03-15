package icarius.entities;

import icarius.http.DeleteRequest;
import icarius.http.PostRequest;
import icarius.auth.User;

public abstract class ServerEntity {
    Long Id;
    String name;

    // If constructed with id, read from server if exists
    public ServerEntity(Long Id) {
        this.Id = Id;
        this.name = read();
    }

    // If constructed with name, create on server
    public ServerEntity(String name) {
        this.name = name;
    }

    // Abstract Functions to implement server CRUD operations
    // protected abstract Long create(User user);
    // protected abstract Long create(User user, Long Id);
    protected abstract String read();
    // protected abstract void update(User user);
    // protected abstract Boolean delete(User user);




    // Creates a new entity on server with title name
    // Entity identity depends on url
    // Returns Id of created entity
    protected Long create(String url, User user) {
        // Send create campus request to server
        PostRequest request = new PostRequest(url, user);
        request.addParameter("name", name);
        String response =  request.send();

        // Print response and return created campus Id
        System.out.println(response);
        response = response.replaceAll("[^0-9]", "");
        return Long.valueOf(response);
    }

    // Delete entity from server with id Id
    // returns true if removed from server, else false
    protected Boolean delete(String url, User user) {
        DeleteRequest request = new DeleteRequest(url, user);
        request.addParameter("id", String.valueOf(Id));
        String response = request.send();

        System.out.println( response );
        return response.contains("removed") ? true : false;
    }

    public int getID(){
        int integerID = Id.intValue();
        return integerID;
    }

    public String getName(){
        return name;
    }
}
