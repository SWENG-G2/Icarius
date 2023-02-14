package icarius.gui.items;

import java.util.Arrays;

public class campus {
    private String name;
    private int ID;
    private Bird[] birds;
    public campus(String Name, int id){
        super();
        name = Name;
        ID=id;
    }
    public String getName(){
        return name;
    }
    public int getID(){
        return ID;
    }

    public void addBird(Bird newBird){
        birds=Arrays.copyOf(birds, birds.length+1);
        birds[birds.length-1]=newBird;
    }

    public String[] getBirdNames(){
        String[] birdNames={};
        for (Bird b : birds){
            birdNames=Arrays.copyOf(birdNames, birdNames.length+1);
            birdNames[birdNames.length-1]=b.getName();
        }
        return birdNames;
    }

    public Bird getBird(String birdName){
        for (Bird b : birds){
            if(b.getName()==birdName){
                return b;
            }
        }
        return null;
    }
}
