package Main.Controller.Customer;

import java.util.*;

import Main.Entity.MovieScreening;

public class GetCinemasController {
    MovieScreening movieScreening = new MovieScreening();

    public GetCinemasController() { 
    }

    public ArrayList<String> getCinemas() { 
        return movieScreening.getAllCinemas();
    }
}
