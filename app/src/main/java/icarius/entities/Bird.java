package icarius.entities;

import icarius.user.User;

public class Bird extends ServerEntity {
    // Bird information
    public String heroImageURL;
    public String soundURL;
    public String aboutMe;
    public String aboutMeVideoURL;
    public String location;
    public String locationImageURL;
    public String diet;
    public String dietImageURL;
    
    public Campus campus;

    public Bird(Long Id) {
        super(Id);
    }

    public Bird(String name, User user) {
        super(name, user);
    }

    @Override
    protected Long create(User user) {
        return create("/api/birds/" + user.getCampusIdString() + "/new", user);
    }

    @Override
    protected String read() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Boolean delete(User user) {
        return delete("/api/birds/" + campus.Id + "/remove", user);
    }

    @Override
    public String toString() {
        return "\nId: " + Id + "\t\tBird: " + name
                    + "\n\t Campus: " + campus.name
                    + "\n\t heroImageURL: " + heroImageURL
                    + "\n\t soundUrl: " + soundURL
                    + "\n\t aboutMe: " + aboutMe
                    + "\n\t aboutMeVideoURL: " + aboutMeVideoURL
                    + "\n\t location: " + location
                    + "\n\t locationImageURL: " + locationImageURL
                    + "\n\t diet: " + diet
                    + "\n\t dietImageURL: " + dietImageURL;
    }
}
