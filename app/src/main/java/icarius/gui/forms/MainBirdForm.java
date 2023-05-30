package icarius.gui.forms;

import javax.swing.JPanel;
import javax.swing.JTextField;

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
    
    public JTextField aboutField;
    public JTextField locationField;
    public JTextField dietField;
    

    public String listImageUrlPath;
    public String heroImageUrlPath;
    public String soundURLPath;
    public String videoUrlPath;
    public String locationImageUrlPath;
    public String dietImageUrlPath;

    public MainBirdForm(Bird bird, String selectedForm){
        initializeFields(bird);
        this.setForm(bird, selectedForm);
    }

    public void setForm(Bird bird, String selectedForm) {
        removeAll();
        
        if(selectedForm.equals("List Image")){
            ListImageForm listImageForm = new ListImageForm(bird);
            listImageUrlPath = listImageForm.UrlPath;
            add(listImageForm);
        } else if (selectedForm.equals("Hero Image")){
            HeroImageForm heroImageForm = new HeroImageForm(bird);
            heroImageUrlPath = heroImageForm.UrlPath;
            add(heroImageForm);
        } else if (selectedForm.equals("Sound")){
            SoundForm soundForm = new SoundForm(bird);
            soundURLPath = soundForm.UrlPath;
            add(soundForm);
        } else if (selectedForm.equals("About")){
            AboutForm aboutForm = new AboutForm(bird);
            aboutField = aboutForm.textField;
            add(aboutForm);
        } else if (selectedForm.equals("Video")){
            VideoForm videoForm = new VideoForm(bird);
            videoUrlPath = videoForm.UrlPath;
            add(videoForm);
        } else if (selectedForm.equals("Location")){
            LocationForm locationForm = new LocationForm(bird);
            locationField = locationForm.textField;
            add(locationForm);
        } else if (selectedForm.equals("Location Image")){
            LocationImageForm locationImageForm = new LocationImageForm(bird);
            locationImageUrlPath = locationImageForm.UrlPath;
            add(locationImageForm);
        } else if (selectedForm.equals("Diet")){
            DietForm dietForm = new DietForm(bird);
            dietField = dietForm.textField;
            add(dietForm);
        } else if (selectedForm.equals("Diet Image")){
            DietImageFrom dietImageFrom = new DietImageFrom(bird);
            dietImageUrlPath = dietImageFrom.UrlPath;
            add(dietImageFrom);
        } else{
            System.out.println("ERROR in gui>forms>birdForms>MainBirdForm");
        }
        revalidate();
        repaint();
    }

    private void initializeFields(Bird bird){
        aboutField = new JTextField(bird.getAboutMe());
        locationField = new JTextField(bird.getLocation());
        dietField = new JTextField(bird.getDiet());
    }
}
