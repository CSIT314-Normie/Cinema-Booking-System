package Main.Controller.Manager;

import Main.Entity.Reviews;

public class DeleteReviewController {
    Reviews review = new Reviews();

    public DeleteReviewController() { 
    }

    public boolean deleteAReview(String reviewID) { 
        return review.deleteReview(reviewID);
    }
    
}
