package event.agency.management.system;


import java.sql.SQLException;

public class payment {
    private  int Paymentid;
    private PaymentStrategy paymentStrategy;

    public payment() {
        this.Paymentid = Paymentid;
    }

    public int getPaymentid() {
        return Paymentid;
    }

    public void setpaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void makePayment(int personid,int amount) throws SQLException {
        System.out.println("Making payment of " + amount + " EGP using " + paymentStrategy.getName() + "...");
        paymentStrategy.makePayment(personid,amount);
    }

    public void refundPayment(int personid,int ticketId) throws SQLException {

        paymentStrategy.refundPayment(personid,ticketId);
    }
}



