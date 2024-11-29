package event.agency.management.system;
import java.sql.*;
import java.sql.Connection;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sponsor {
    String sponsorName;
    int sponsorPrice;
    int sponsorId;
    int eventId;
    
    private Connection conn;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";

    public sponsor(String sponsorName, int sponsorPrice, int sponsorId, int eventId) {
        this.sponsorName = sponsorName;
        this.sponsorPrice = sponsorPrice;
        this.sponsorId = sponsorId;
        this.eventId = eventId;
    }

    public sponsor(String sponsorName, int sponsorPrice) {
        this.sponsorName = sponsorName;
        this.sponsorPrice = sponsorPrice;
    }

    public sponsor(String sponsorName, int sponsorPrice, int sponsorId) {
        this.sponsorName = sponsorName;
        this.sponsorPrice = sponsorPrice;
        this.sponsorId = sponsorId;
    }

    public sponsor() {
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public int getSponsorPrice() {
        return sponsorPrice;
    }

    public void setSponsorPrice(int sponsorPrice) {
        this.sponsorPrice = sponsorPrice;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void addSponsor(){
        System.out.println("Enter Sponsor name");
        Scanner Name = new Scanner(System.in);
        String sponosrName = Name.nextLine();
        setSponsorName(sponosrName);

        System.out.println("Enter sponsor cost");
        Scanner Cost = new Scanner(System.in);
        int sponsorBalance = Cost.nextInt();
        setSponsorPrice(sponsorBalance);

        sponsor s = new sponsor(sponsorName,sponsorBalance,0,0);
        database db = new database();
        db.addSponsor(s);

    }
    
    public void deleteSponser(int id) {
        conn = connectToDatabase();
    try {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM sponsor WHERE sponsorid = ?");
        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();
        if(rowsDeleted <= 0) {
            System.out.println("Sponser with ID " + id + " not found in the database.");
        }
    } catch (Exception e) {
        System.err.println("Error deleting Sponser from database: " + e.getMessage());
    }
   }
    
    public Connection connectToDatabase() {
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
    
}