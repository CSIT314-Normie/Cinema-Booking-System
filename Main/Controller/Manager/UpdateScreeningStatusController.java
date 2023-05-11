package Main.Controller.Manager;

import java.util.*;

import Main.Entity.*;

public class UpdateScreeningStatusController {
    MovieScreening movieScreening = new MovieScreening();

    public UpdateScreeningStatusController() {}

    /**
     * To handle the update of screening status - CINEMA MANAGER
     * @param screeningID
     * @param newScreeningStatus
     * @return boolean whether the update is successful
     */
    public boolean updateScreeningStatus(String screeningID, String newScreeningStatus) {
        return movieScreening.updateScreeningStatus(screeningID, newScreeningStatus);
    }
}
