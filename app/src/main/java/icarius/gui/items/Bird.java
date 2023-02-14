package icarius.gui.items;

public class Bird {
    private  String name;
    //The image variables store links to the image stored in the server
    private String heroImage;
    private String listImage;
    private String sound;
    private String about;
    private String video;
    private String location;
    private String locationImage;
    private String diet;
    private String dietImage;

    public Bird(String Name){
        super();
        name = Name;
        //TODO add in other variables once they're set up
    }

    protected String getName(){
        return name;
    }
}
