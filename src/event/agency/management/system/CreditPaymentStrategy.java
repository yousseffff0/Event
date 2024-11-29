package event.agency.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class CreditPaymentStrategy implements PaymentStrategy{
    private String name = "credit";
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private Connection conn;
    private static admin singleInstance = null;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";
    public String getName() {
        return name;
    }
    public Connection connectToDatabasee() {
        Connection conn = null;
        final String userName = "root";
        final String password = "";
        final String dbName = "eventagency";
        try {
            // Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
        return conn;
    }
    public CreditPaymentStrategy() {

    }

    public boolean makePayment(int personids,int amount) throws SQLException {
        conn = connectToDatabasee();
        Scanner scanner = new Scanner(System.in);

        // Get credit card information from user and validate it
        String cardNumber;
        String cvv;
        LocalDate expiryDate;
        boolean validCard = false;

        do {
            System.out.print("Enter your 16-digit credit card number: ");
            cardNumber = scanner.nextLine().trim();

            System.out.print("Enter the 3-digit CVV code: ");
            cvv = scanner.nextLine().trim();

            System.out.print("Enter the expiry date in yyyy-mm-dd format: ");
            expiryDate = LocalDate.parse(scanner.nextLine().trim());

            if (cardNumber.length() != 16) {
                System.out.println("Invalid card number. Please enter a 16-digit number.");
                continue;
            }

            if (cvv.length() != 3) {
                System.out.println("Invalid CVV. Please enter a 3-digit number.");
                continue;
            }

            if (expiryDate.isBefore(LocalDate.now())) {
                System.out.println("Invalid expiry date. Please enter a valid date.");
                continue;
            }

            validCard = true;

        } while (!validCard);

        // retrieve the current balance from the database
        String sql = "SELECT balance FROM customer WHERE personid = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, personids);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int currentBalance = rs.getInt("balance");
        rs.close();
        pstmt.close();

        // check if balance is sufficient for the transaction
        if (currentBalance < amount) {
            System.out.println("Insufficient balance.");
            return false;
        }

        // subtract the amount from the current balance
        int newBalance = currentBalance - amount;

        // update the balance in the database
        sql = "UPDATE customer SET balance = " + newBalance + " WHERE personid = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, personids);
        pstmt.executeUpdate();
        pstmt.close();
        ;

        System.out.println(amount + " EGP has been deducted from your balance.");
        return true;
    }



    public void refundPayment( int personids,int bookingid) throws SQLException {
        conn = connectToDatabasee();
        String sql = "SELECT * FROM booking WHERE bookingid= ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookingid);
        ResultSet rs = pstmt.executeQuery();
        sql = "SELECT balance FROM customer WHERE personid= ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, personids);
        ResultSet bs = pstmt.executeQuery();
        if (rs.next()&&bs.next()) {
            int price = rs.getInt("cost");
            int balance=bs.getInt("balance");
            int total=price+balance;
            sql = "UPDATE customer SET balance ="+total+" WHERE personid= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, personids);

            pstmt.executeUpdate();
            System.out.println("Ticket with ID " + bookingid + " added to customer balance.");
        } else {
            System.out.println("Ticket with ID " + bookingid + " not found.");
        }

        rs.close();
        pstmt.close();
    }
    
    
    public boolean updateBalance(int personids, int amount) throws SQLException {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
        // retrieve the current balance from the database
        String sql = "SELECT balance FROM customer WHERE personid = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, personids);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int currentBalance = rs.getInt("balance");
        rs.close();
        pstmt.close();

        // check if balance is sufficient for the transaction
        if (currentBalance < amount) {
            System.out.println("Insufficient balance.");
            return false;
        } else {

            // subtract the amount from the current balance
            int newBalance = currentBalance - amount;

            // update the balance in the database
            sql = "UPDATE customer SET balance = " + newBalance + " WHERE personid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, personids);
            pstmt.executeUpdate();
            pstmt.close();

            return true;

        }
    }
    
}
