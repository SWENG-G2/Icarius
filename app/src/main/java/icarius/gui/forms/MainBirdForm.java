package icarius.gui.forms;

import java.util.HashMap;

import javax.swing.JPanel;

import icarius.entities.Bird;
import icarius.gui.forms.birdForms.AboutForm;
import icarius.gui.forms.birdForms.DietForm;
import icarius.gui.forms.birdForms.DietImageForm;
import icarius.gui.forms.birdForms.HeroImageForm;
import icarius.gui.forms.birdForms.ListImageForm;
import icarius.gui.forms.birdForms.LocationForm;
import icarius.gui.forms.birdForms.LocationImageForm;
import icarius.gui.forms.birdForms.SoundForm;
import icarius.gui.forms.birdForms.VideoForm;
import lombok.Getter;

public class MainBirdForm extends JPanel {
    @Getter
    private final HashMap<String, String> changeParams;

    public ListImageForm listImageForm;
    public HeroImageForm heroImageForm;
    public SoundForm soundForm;
    public AboutForm aboutForm;
    public VideoForm videoForm;
    public LocationForm locationForm;
    public LocationImageForm locationImageForm;
    public DietForm dietForm;
    public DietImageForm dietImageFrom;

    /**
     * Form for editing a bird
     * 
     * @param bird
     * @param selectedForm
     */
    public MainBirdForm(Bird bird, String selectedForm) {
        this.changeParams = bird.clearParams();
        initializeFields(bird);
        this.setForm(selectedForm);
    }

    /**
     * Form for creating a bird
     * 
     * @param selectedForm
     */
    public MainBirdForm(String selectedForm) {
        this.changeParams = null;
        initializeFields();
        this.setForm(selectedForm);
    }

    /**
     * selects the displayed form depending on what is selected in the combo box
     * 
     * @param selectedForm
     */
    public void setForm(String selectedForm) {
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

    /**
     * Initializes fields for the selected bird
     * 
     * @param bird
     */
    private void initializeFields(Bird bird) {
        listImageForm = new ListImageForm(bird, changeParams);
        heroImageForm = new HeroImageForm(bird, changeParams);
        soundForm = new SoundForm(bird, changeParams);
        aboutForm = new AboutForm(bird, changeParams);
        videoForm = new VideoForm(bird, changeParams);
        locationForm = new LocationForm(bird, changeParams);
        locationImageForm = new LocationImageForm(bird, changeParams);
        dietForm = new DietForm(bird, changeParams);
        dietImageFrom = new DietImageForm(bird, changeParams);
    }

    /**
     * Initializes fields for creating a bird
     */
    private void initializeFields() {
        listImageForm = new ListImageForm();
        heroImageForm = new HeroImageForm();
        soundForm = new SoundForm();
        aboutForm = new AboutForm();
        videoForm = new VideoForm();
        locationForm = new LocationForm();
        locationImageForm = new LocationImageForm();
        dietForm = new DietForm();
        dietImageFrom = new DietImageForm();
    }
}
