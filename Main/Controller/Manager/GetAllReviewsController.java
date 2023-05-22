package Main.Controller.Manager;

import java.util.ArrayList;

import Main.Entity.Reviews;

public class GetAllReviewsController {
    Reviews review = new Reviews();

    public GetAllReviewsController() { 
    }

    public ArrayList<String> getAllReviews() { 
        return review.getAllReviews();
    }
}
