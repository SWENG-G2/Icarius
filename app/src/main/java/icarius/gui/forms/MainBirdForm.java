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
    private Bird bird;

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
        this.bird = bird;
        //initializeFields(bird);
        this.setForm(selectedForm);
    }

    public MainBirdForm(String selectedForm){
        initializeFields();
        this.setForm(selectedForm);
    }

    public void setForm(String selectedForm) {
        removeAll();

        switch (selectedForm) {
            case "List Image":
                if (listImageForm == null) listImageForm = new ListImageForm(bird);
                add(listImageForm);
                break;
            case "Hero Image":
                if (heroImageForm == null) heroImageForm = new HeroImageForm(bird);
                add(heroImageForm);
                break;
            case "Sound":
                if (soundForm == null) soundForm = new SoundForm(bird);
                add(soundForm);
                break;
            case "About":
                if (aboutForm == null) aboutForm = new AboutForm(bird);
                add(aboutForm);
                break;
            case "Video":
                if (videoForm == null) videoForm = new VideoForm(bird);
                add(videoForm);
                break;
            case "Location":
                if (locationForm == null) locationForm = new LocationForm(bird);
                add(locationForm);
                break;
            case "Location Image":
                if (locationImageForm == null) locationImageForm = new LocationImageForm(bird);
                add(locationImageForm);
                break;
            case "Diet":
                if (dietForm == null) dietForm = new DietForm(bird);
                add(dietForm);
                break;
            case "Diet Image":
                if (dietImageFrom == null) dietImageFrom = new DietImageFrom(bird);
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

    private void initializeFields(){
        listImageForm = new ListImageForm();
        heroImageForm = new HeroImageForm();
        soundForm = new SoundForm();
        aboutForm = new AboutForm();
        videoForm = new VideoForm();
        locationForm = new LocationForm();
        locationImageForm = new LocationImageForm();
        dietForm = new DietForm();
        dietImageFrom = new DietImageFrom();
    }
}
