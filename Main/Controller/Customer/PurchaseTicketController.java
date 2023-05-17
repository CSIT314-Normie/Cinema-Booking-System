package Main.Controller.Customer;

import Main.Entity.Payment;

public class PurchaseTicketController {

    Payment payment = new Payment();

    public PurchaseTicketController() {
    }

    /**
     * To make payment
     * @param String userEmail 
     * @param String total price paid
     * @param String date
     * @return boolean payment status
     */
    public boolean makePayment(String userEmail, String totalPrice, String date) {
        boolean paymentStatus = payment.makePayment(userEmail, totalPrice, date);

        return paymentStatus;
    }
    
}
