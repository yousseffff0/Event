package event.agency.management.system;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PaymentTest {
        private CreditPaymentStrategy creditPaymentStrategy;
        private final String userName = "root";
        private final String password = "";
        private final String dbName = "eventagency";

        @Test
        public void testMakePayment() throws SQLException {
            Connection conn = null;
            try {
                //Loading the jdbc driver
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                //Get a connection to database
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
            } catch (Exception e) {
                System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
            }

            creditPaymentStrategy = new CreditPaymentStrategy();
            creditPaymentStrategy.updateBalance(1, 100);

            String sql = "SELECT balance FROM customer WHERE personid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int balance = rs.getInt("balance");
            rs.close();
            pstmt.close();

            assertEquals(2350, balance);
        }
}