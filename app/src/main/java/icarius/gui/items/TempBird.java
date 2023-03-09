package icarius.gui.items;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JTree;





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
    public TempBird(String Name, TempCampus Campus){
        super();
        name = Name;
        node = new DefaultMutableTreeNode(name);
        campus = Campus;
        //TODO add in other variables once they're set up
    }

    public void addCampus(TempCampus newCampus){
        JTree tree = newCampus.getTree();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        model.insertNodeInto(node, root, 0);

        campus = newCampus;
    }

    public TempCampus getCampus(){
        return campus;
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
