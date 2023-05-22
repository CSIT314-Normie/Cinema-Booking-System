package Main.Controller.Manager;

import Main.Entity.MovieScreening;

public class SuspendScreeningController {
    MovieScreening movieScreening = new MovieScreening();
    
    public SuspendScreeningController() {
    }

    /**
     * This method is used to suspend a screening
     * @param screeningID is the ID of the screening
     * @return true if the screening is suspended, false otherwise
     */
    public boolean suspendScreening(String screeningID) {
        return movieScreening.suspendScreening(screeningID);
    }

}
