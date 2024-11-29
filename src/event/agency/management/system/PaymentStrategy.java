package event.agency.management.system;

import java.sql.SQLException;

public interface PaymentStrategy {
    String getName();
    boolean makePayment(int personid,int amount) throws SQLException;
    void refundPayment(int personid,int ticketId) throws SQLException;
}
