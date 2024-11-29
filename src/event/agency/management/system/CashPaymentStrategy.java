package event.agency.management.system;

public class CashPaymentStrategy implements PaymentStrategy{
    public String getName() {
        return "cash";
    }

    public boolean makePayment(int personid,int amount) {
        System.out.println("Please go to a Virgin store in Egypt and pay " + amount + " EGP to complete your transaction. After paying, you will receive a ticket.");
        return false;
    }

    public void refundPayment(int personid,int ticketId) {
        System.out.println("Please go to a Virgin store in Egypt with your ticket to get  your refund");
    }
}
