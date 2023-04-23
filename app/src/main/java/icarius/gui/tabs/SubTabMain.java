package icarius.gui.tabs;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;

public class SubTabMain extends Tab{

    private JLabel response;

    private JLabel nameLabel;
    private JLabel heroImageLabel;
    private JLabel listImageLabel;
    private JLabel soundLabel;
    private JLabel aboutLabel;
    private JLabel videoLabel;
    private JLabel locationLabel;
    private JLabel locationImageLabel;
    private JLabel dietLabel;
    private JLabel dietImageLabel;
    private JLabel[] staticLabels={nameLabel, heroImageLabel, listImageLabel, soundLabel, aboutLabel,
                                videoLabel, locationLabel, locationImageLabel, dietLabel, dietImageLabel};

    private JLabel campusText;
    private JLabel nameText;
    private JLabel heroImageLink;
    private JLabel listImageLink;
    private JLabel soundLink;
    private JLabel aboutText;
    private JLabel videoLink;
    private JLabel locationText;
    private JLabel locationImageLink;
    private JLabel dietText;
    private JLabel dietImageLink;
    private JLabel[] birdLabels = {};

    private JTextField campusField;
    private JTextField nameField;
    private JButton heroImageUp;
    private JButton listImageUp;
    private JButton soundUp;
    private JTextField aboutField;
    private JButton videoUp;
    private JTextField locationField;
    private JButton locationImageUp;
    private JTextField dietField;
    private JButton dietImageUp;
    protected JButton addBirdButton;
    private JComponent[] components = {};

    private JButton editBirdButton;
    protected JButton saveBirdButton;
    private JButton cancelBirdButton;

    private JButton editCampusButton;
    protected JButton saveCampusButton;
    private JButton cancelCampusButton;

    private String selectedBird="";
    private String selectedCampus="";

    private JButton[] uploadButtons = {};

    
    private JButton[] addEditOrSaveButtons = {};

    protected JButton createCampusButton;
//    private JButton removeCampusButton;

    private String[] labelNames = {"Bird Name:    ", "Hero Image:    ", "List Image:    ",
    "Sound:    ", "About:    ", "Video:    ", "Location:    ", "Location Image:    ", "Diet:    ", "Diet Image:    "};

    protected SubTabMain(){
        initialiseInfoLabels();
        initialiseComponents();
        initialiseStaticLabels();
        setupEditButtons();
        c.anchor=GridBagConstraints.LINE_END;


        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Campus Name:    "), c);

        int i = 1;
        for (JLabel label : staticLabels){
            addComponent(label, 0, i);
            i++;
        }

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = i+1;
        panel.add(new JLabel("Response:    "), c);

        c.fill = GridBagConstraints.HORIZONTAL;

        i=0;
        addComponent(campusField, 1, i);
        for (JComponent component : components){
            i++;
            addComponent(component, 1, i);
            component.setVisible(false);
        }


        //By default add campus is selected
        
        addComponent(cancelBirdButton, 0, i+1);
        cancelBirdButton.setVisible(false);

        addComponent(cancelCampusButton, 0, i+1);
        cancelCampusButton.setVisible(false);

        for(JButton button : addEditOrSaveButtons){
            addComponent(button, 1, i+1);
            button.setVisible(false);
        }

        createCampusButton.setVisible(true);


        addComponent(campusText, 1, 0);
        campusText.setVisible(false);

        i=1;
        for (JLabel label: birdLabels){
            addComponent(label, 1, i);
            label.setVisible(false);
            i++;
        }

        addComponent(addBirdButton, 1, i);
        addBirdButton.setVisible(false);

        c.gridwidth=3;
        addComponent(response, 1, i+1);
        c.gridwidth=1;

        showStaticLabels(false);

    }




    private void initialiseComponents(){
        //Initialises all of the JComponents
        campusField = new JTextField("");
        nameField = new JTextField("");
        heroImageUp = new JButton("Upload Hero Image");
        listImageUp = new JButton("Upload List Image");
        soundUp = new JButton("Upload Sound");
        aboutField = new JTextField("");
        videoUp = new JButton("Upload Video");
        locationField = new JTextField("");
        locationImageUp = new JButton("Upload Location Image");
        dietField = new JTextField("");
        dietImageUp = new JButton("Upload Diet Image");
        addBirdButton = new JButton("Add Bird");

        createCampusButton = new JButton("Add campus");
        editBirdButton = new JButton("Edit");
        saveBirdButton = new JButton("Save Changes");
        cancelBirdButton = new JButton("Cancel");
        editCampusButton = new JButton("Edit");
        saveCampusButton = new JButton("Save Changes");
        cancelCampusButton = new JButton("Cancel");

        JButton[] tempUploadButtons={heroImageUp, listImageUp, soundUp,
                    videoUp, locationImageUp, dietImageUp};

        uploadButtons=Arrays.copyOf(tempUploadButtons, tempUploadButtons.length);

        setupUploadButtons();

        JComponent[] tempComponents={nameField, heroImageUp, listImageUp, soundUp, aboutField,
            videoUp, locationField, locationImageUp, dietField, dietImageUp};


        components=Arrays.copyOf(tempComponents, tempComponents.length);

        JButton[] tempAESButtons = {createCampusButton, addBirdButton, 
            editBirdButton, editCampusButton, saveBirdButton, saveCampusButton};

        addEditOrSaveButtons = Arrays.copyOf(tempAESButtons, tempAESButtons.length);
    }

    private void initialiseInfoLabels(){
        campusText = new JLabel("");
        nameText = new JLabel("");
        heroImageLink = new JLabel("");
        listImageLink = new JLabel("");
        soundLink = new JLabel("");
        aboutText = new JLabel("");
        videoLink = new JLabel("");
        locationText = new JLabel("");
        locationImageLink = new JLabel("");
        dietText = new JLabel("");
        dietImageLink = new JLabel("");

        JLabel[] tempLabels = {nameText, heroImageLink, listImageLink, soundLink,
                            aboutText, videoLink, locationText, locationImageLink, dietText, dietImageLink};
        
        birdLabels = Arrays.copyOf(tempLabels, tempLabels.length);

        response = new JLabel("");
    }

    private void initialiseStaticLabels(){
        int i=0;
        JLabel[] tempLabels = {};
        for (JLabel label : staticLabels){
            label = new JLabel(labelNames[i]);
            tempLabels = Arrays.copyOf(tempLabels, tempLabels.length+1);
            tempLabels[tempLabels.length-1]=label;
            i++;
        }
        staticLabels=Arrays.copyOf(tempLabels, tempLabels.length);
    }

    private void openDirectory() throws IOException{
        File directory = new File("C://Program Files//");
        Desktop.getDesktop().open(directory);
    }

    //TODO - make sure this is nio and not io
    private void setupUploadButtons(){
        for (JButton button : uploadButtons){
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    try{
                        openDirectory();
                    } catch(Exception IOException){
                        System.out.println("ERROR OPENING FILE EXPLORER");
                    }
                }
             });
        }
    }

    private void addComponent(JComponent component, int x, int y){
        c.weightx = 0.8;
        c.gridx = x;
        c.gridy = y;
        panel.add(component, c);
    }

    protected void addCampusNodePressed(boolean bool){
        if (bool==true){
            for(JComponent comp : components){
                comp.setVisible(false);
            }
            for(JLabel label : birdLabels){
                label.setVisible(false);
            }
            createCampusButton.setVisible(true);
            campusField.setVisible(true);
            campusText.setVisible(false);
            addBirdButton.setVisible(false);
        }else{
            createCampusButton.setVisible(false);
            campusField.setVisible(false);
            campusText.setVisible(true);
            campusField.setText("");
        }
    }

    protected void addBirdNodePressed(boolean bool){
        if (bool==true){
            for(JComponent comp : components){
                comp.setVisible(true);
            }
            for(JLabel label : birdLabels){
                label.setVisible(false);
            }
            addBirdButton.setVisible(true);
        }else{
            for(JComponent comp : components){
                comp.setVisible(false);
            }
            for(JLabel label : birdLabels){
                label.setVisible(true);
            } 
            addBirdButton.setVisible(false);
        }
    }

    protected void setResponse(String text){
        response.setText(text);
    }

    protected String nameFieldText(){
        return nameField.getText();
    }

    protected String getCampusFieldValue(){
        return campusField.getText();
    }
    
    protected void setCampusText(String text){
        campusText.setText(text);
    }

    protected void setNameText(String text){
        nameText.setText(text);
    }

    protected JButton[] uploadButtons(){
        return uploadButtons;
    }

    protected void setBirdLabels(String[] birdInfo){
        int i = 0;
        for (String info : birdInfo){
            birdLabels[i].setText(info);
            i++;
        }
    }

    protected void editBirdSelected(boolean bool){
        if (bool == true){
            selectedBird = nameText.getText();
            int i = 0;
            
            for (JLabel label : birdLabels){
                label.setVisible(false);
            }

            for (JComponent comp : components){
              if (comp instanceof JTextField){
                JTextField field = (JTextField)comp;
                field.setText(birdLabels[i].getText());
              } else if (comp instanceof JButton){
                JButton button = (JButton)comp;
                button.setText("Upload Different Image");
              } else{
                System.out.println("GUI ERROR: IN editSelected FUNC IN SubTabMain\n bool is true, i = "+i);
              }
              comp.setVisible(true);
              i++;
            }
        }else{
            for (JLabel label : birdLabels){
                label.setVisible(true);
            }
            int i=0;
            for (JComponent comp : components){
              if (comp instanceof JTextField){
                JTextField field = (JTextField)comp;
                field.setText("");
              }  else if (comp instanceof JButton){
                JButton button = (JButton)comp;
                button.setText("Press to Upload");    
              } else{
                System.out.println("GUI ERROR: IN editSelected FUNC IN SubTabMain\n bool is false, i = "+i);
              }
              comp.setVisible(false);
              i++;
            } 
        }
    }

    protected void visibleEditBirdButton(boolean bool){
        editBirdButton.setVisible(bool);
    }

    protected void visibleEditCampusButton(boolean bool){
        editCampusButton.setVisible(bool);
    }

    private void setupEditButtons(){
        editBirdButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                editBirdSelected(true);
                editBirdButton.setVisible(false);
                cancelBirdButton.setVisible(true);
                saveBirdButton.setVisible(true);
                //TODO - work on commit changes button
            }
        });

        cancelBirdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                editBirdSelected(false);
                editBirdButton.setVisible(true);
                cancelBirdButton.setVisible(false);
                saveBirdButton.setVisible(false);
            }
        });

        editCampusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                selectedCampus=campusText.getText();
                campusField.setText(campusText.getText());
                campusField.setVisible(true);
                campusText.setVisible(false);
                editCampusButton.setVisible(false);
                cancelCampusButton.setVisible(true);
                saveCampusButton.setVisible(true);
                //TODO - work on commit changes button
            }
        });

        cancelCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                editCampusClosed(selectedCampus);
            }
        });
    }

    public String getNameFieldText() {
        return nameField.getText();
    }

    public String aboutFieldText(){
        return aboutField.getText();
    }

    public String locationFieldText(){
        return locationField.getText();
    }
    public String dietFieldText(){
        return dietField.getText();
    }

    protected String getCampusText(){
        return campusText.getText();
    }

    protected String getSelectedBird(){
        return selectedBird;
    }

    protected String getSelectedCampus(){
        return selectedCampus;
    }

    protected void campusNodePressed(){
        for (JLabel label : birdLabels){
            label.setText("");
            label.setVisible(true);
        }
        for (JComponent comp : components){
            comp.setVisible(false);
        }
    }

    protected void editCampusClosed(String campusName){
        campusText.setText(campusName);
        campusField.setText("");
        campusField.setVisible(false);
        campusText.setVisible(true);
        editCampusButton.setVisible(true);
        cancelCampusButton.setVisible(false);
        saveCampusButton.setVisible(false);
    }

    protected void editBirdClosed(){
        nameField.setText("");
        saveBirdButton.setVisible(false);
        editBirdButton.setVisible(true);
        cancelBirdButton.setVisible(false);
        //TODO - When the othe fields are set up this should also change for them
        editBirdSelected(false);
    }

    protected void showStaticLabels(boolean bool){
        for (JLabel label : staticLabels){
            label.setVisible(bool);
        }
    }


    public String[] getEditedBirdInfo(){
        //TODO - Replace the URL's with actual URL's
        String[] birdInfo = {nameField.getText(), "URL", "URL", "URL", aboutField.getText(),
                            "URL", locationField.getText(),"URL",dietField.getText(),"URL"};
        return birdInfo;
    }
}
