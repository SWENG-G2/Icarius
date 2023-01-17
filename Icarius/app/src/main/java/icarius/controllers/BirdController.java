package icarius.controllers;

import icarius.entities.Bird;
import icarius.services.HttpService;

import icarius.App;

public class BirdController {
    
    // BIRD CONTROLLER
    // Get all Ducks

    // update duck

    // new duck

    // remove duck




    // TODO: Complete function
    public static Bird getBirdById(int id) {
        String response = HttpService.get(App.BASE_URL + "campus/list");
        System.out.println(response);

        // parse xml
        // create bird object
        // return
        
        return new Bird();
    }

}
