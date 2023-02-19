package icarius.gui.items;
import javax.swing.tree.DefaultMutableTreeNode;


public class TempBird {
    private  String name;
    //The image, sound and video variables store links to the image/sound/video stored in the server
    private String heroImage;
    private String listImage;
    private String sound;
    private String about;
    private String video;
    private String location;
    private String locationImage;
    private String diet;
    private String dietImage;
    private TempCampus campus;

    private DefaultMutableTreeNode node;

    //Temporary, this is being used to test adding birds to the campus tree in the bird tab
    public TempBird(String Name){
        super();
        name = Name;

        node = new DefaultMutableTreeNode(name);
    
        //TODO add in other variables once they're set up
    }

    public void addCampus(TempCampus newCampus){
        DefaultMutableTreeNode root = newCampus.getRoot();
        root.add(node);
        campus = newCampus;
    }



    public String getName(){
        return name;
    }

    public String getHeroImage() {
        return heroImage;
    }

    public String getListImage() {
        return listImage;
    }

    public String getSound(){
        return sound;
    }

    public String getAbout() {
        return about;
    }

    public String getVideo() {
        return video;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationImage() {
        return locationImage;
    }

    public String getDiet() {
        return diet;
    }

    public String getDietImage() {
        return dietImage;
    }
}
