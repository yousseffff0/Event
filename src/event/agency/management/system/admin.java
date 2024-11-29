package event.agency.management.system;
import java.io.IOException;
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


public class admin extends person {
    private int Admin_id ;
    private Connection conn;
    private static admin singleInstance = null;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";
    Random random = new Random();
    int randomNum = random.nextInt(100) + 1;
    private static feedback feedback;
    private static eventorganizer eventorganizer;
    private static admin admin;
    private static event event;

    private static report report;
    
    public int getAdmin_id() {
        return Admin_id;
    }

    public void setAdmin_id(int admin_id) {
        Admin_id = admin_id;
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
    
     public admin(){};
    
     private admin(String name, String phoneNo, String email, String age, String password, int id, int personTypeID, int admin_id) {
        super(name, phoneNo, email, age, password, id, personTypeID);
        this.Admin_id = admin_id;
        this.conn = connectToDatabase();
        feedback = new feedback();
        eventorganizer= new eventorganizer();
        admin=new admin();
        event = new event();
    }

    public  void ConfirmEvent()  {
        conn = connectToDatabase();
        System.out.println("Enter the event ID to search:");
        Scanner scanner = new Scanner(System.in);
        int eventId = scanner.nextInt();
        try {
            PreparedStatement selectStmt = conn.prepareStatement("SELECT cost, eventname FROM pendingevent WHERE eventid = ?");
            selectStmt.setInt(1, eventId);
            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {

                int cost = resultSet.getInt("cost");
                String eventName = resultSet.getString("eventname");

                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO eventt (cost, eventname, eventid,ticketcode,serviceid) VALUES (?, ?, ?,?,?)");
                insertStmt.setInt(1, cost);
                insertStmt.setString(2, eventName);
                insertStmt.setInt(3, eventId);
                insertStmt.setInt(4, randomNum);
                insertStmt.setInt(5, randomNum);
                int rows = insertStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Event details submitted successfully!");
                    event.notifyCustomers("Added", eventName);

                } else {
                    System.out.println("Error submitting Event details");
                }
            } else {
                System.out.println("No data found in panndeingevent table for event id: " + eventId);
            }
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM  pendingevent WHERE eventid = ?");
            deleteStmt.setInt(1, eventId );
            int deleteRows = deleteStmt.executeUpdate();
            if (deleteRows > 0) {
                System.out.println("Data removed from panndeingevent table for event id: " + eventId );
            } else {
                System.out.println("No data found in panndeingevent table for event id: " + eventId );
            }
        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getMessage());
        }

    }

    public static admin getInstance(String name, String phoneNo, String email, String age, String password, int id, int personTypeID, int admin_id)  {
        if (singleInstance == null) {
            singleInstance = new admin(name, phoneNo, email, age, password, id, personTypeID, admin_id);
            System.out.println("Admin loged in");
            Scanner adminall = new Scanner(System.in);
            System.out.println("1 for Add Organizer");
            System.out.println("2 for remove Organizer");
            System.out.println("3 for generate report");
            System.out.println("4 for confirm event");
            System.out.println("5 for view feedbacks");
            System.out.println("0 for Exit");
            int x=adminall.nextInt();

            while(x!=0){
                switch(x){
                    case 1:eventorganizer.addOrganizerData();
                        break;
                    case 2:eventorganizer.deleteEventOrganizer();
                        break;
                    case 3:report.showreports();
                        break;
                    case 4:
                        event.ChooseEventToConfirm();
                        break;
                    case 5: feedback.feedbackCall();
                        break;
                }
                break;
            }
            return singleInstance;
        }else{
            System.out.print("Admin is already loged in!");
            return null;
        }
    }
}