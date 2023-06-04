package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameGrabber.Exception;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;

public class SoundForm extends BirdFieldForm{
    public SoundForm(Bird bird){
        UrlPath = bird.getSoundURL();

        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.REMAINDER;

        UploadButton = addFileUploadField("Sound:", bird.getSoundURL(), c, uploadAudio());

        c.gridx = 2;
        c.gridy = 2;
        JButton audioButton = new JButton("Play audio");
        add(audioButton, c);

        audioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (UrlPath!= null){
                    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(UrlPath);     
                    try {
                        grabber.start();
                        System.out.println("grabber start");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    if (grabber.getAudioChannels() > 0) {
                        /* Not currently working
                        final AudioFormat audioFormat = new AudioFormat(grabber.getSampleRate(), 16, grabber.getAudioChannels(), true, true);
                        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                        final SourceDataLine soundLine;
                        */
                        try {
                            //soundLine = (SourceDataLine) AudioSystem.getLine(info);
                            //soundLine.open(audioFormat);
                            //soundLine.start();
                            //Only works with wav:
                            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new URL("file:///" + UrlPath));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioIn);
                            clip.start();
                            
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedAudioFileException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }       
                    }                
                }
            }
        });
    }

    public SoundForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 0;
        c.gridy = 1;

        UploadButton = addFileUploadField("Sound:", "", c, uploadAudio());

        JButton audioButton = new JButton("Play audio");
        c.gridx = 2;
        c.gridy = 2;
        add(audioButton, c);

        audioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (UrlPath!= null){
                    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(UrlPath);     
                    try {
                        grabber.start();
                        System.out.println("grabber start");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    if (grabber.getAudioChannels() > 0) {
                        /* Not currently working
                        final AudioFormat audioFormat = new AudioFormat(grabber.getSampleRate(), 16, grabber.getAudioChannels(), true, true);
                        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                        final SourceDataLine soundLine;
                        */
                        try {
                            //soundLine = (SourceDataLine) AudioSystem.getLine(info);
                            //soundLine.open(audioFormat);
                            //soundLine.start();
                            //Only works with wav:
                            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new URL("file:///" + UrlPath));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioIn);
                            clip.start();
                            grabber.close();  
                            
                        } catch (java.lang.Exception e) {
                            e.printStackTrace();
                        }     
                    }
                         
                    try {
                        grabber.close();
                        System.out.println("grabber close");
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public ActionListener uploadAudio() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Configure Layout
                GridBagConstraints c = configure();
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 2;
                c.fill = GridBagConstraints.HORIZONTAL;

                // Upload file
                File file = selectLocalFile("Audio");
                if (file == null) return;
                UploadButton.setText("File selected: " + file.getName());
                UrlPath = file.getPath();
            }
        };
    } 
}
