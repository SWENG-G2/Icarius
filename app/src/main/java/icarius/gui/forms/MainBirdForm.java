package icarius.gui.forms;

import javax.swing.JPanel;

import icarius.entities.Bird;
import icarius.gui.forms.birdForms.AboutForm;
import icarius.gui.forms.birdForms.DietForm;
import icarius.gui.forms.birdForms.DietImageFrom;
import icarius.gui.forms.birdForms.HeroImageForm;
import icarius.gui.forms.birdForms.ListImageForm;
import icarius.gui.forms.birdForms.LocationForm;
import icarius.gui.forms.birdForms.LocationImageForm;
import icarius.gui.forms.birdForms.SoundForm;
import icarius.gui.forms.birdForms.VideoForm;

public class MainBirdForm extends JPanel{
    

    public ListImageForm listImageForm;
    public HeroImageForm heroImageForm;
    public SoundForm soundForm;
    public AboutForm aboutForm;
    public VideoForm videoForm;
    public LocationForm locationForm;
    public LocationImageForm locationImageForm;
    public DietForm dietForm;
    public DietImageFrom dietImageFrom;

    public MainBirdForm(Bird bird, String selectedForm){
        initializeFields(bird);
        this.setForm(bird, selectedForm);
    }

    public void setForm(Bird bird, String selectedForm) {
        removeAll();

        switch (selectedForm) {
            case "List Image":
                add(listImageForm);
                break;
            case "Hero Image":
                add(heroImageForm);
                break;
            case "Sound":
                add(soundForm);
                break;
            case "About":
                add(aboutForm);
                break;
            case "Video":
                add(videoForm);
                break;
            case "Location":
                add(locationForm);
                break;
            case "Location Image":
                add(locationImageForm);
                break;
            case "Diet":
                add(dietForm);
                break;
            case "Diet Image":
                add(dietImageFrom);
                break;
            default:
                System.out.println("ERROR in gui>forms>birdForms>MainBirdForm");
                break;
        }
        
        revalidate();
        repaint();
    }

    private void initializeFields(Bird bird){
        listImageForm = new ListImageForm(bird);
        heroImageForm = new HeroImageForm(bird);
        soundForm = new SoundForm(bird);
        aboutForm = new AboutForm(bird);
        videoForm = new VideoForm(bird);
        locationForm = new LocationForm(bird);
        locationImageForm = new LocationImageForm(bird);
        dietForm = new DietForm(bird);
        dietImageFrom = new DietImageFrom(bird);
    }
}
