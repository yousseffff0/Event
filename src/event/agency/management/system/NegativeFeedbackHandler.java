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

public class NegativeFeedbackHandler implements FeedbackHandler {
    private Connection conn;
   
    @Override
    public void FeedbackHandlerr() {
            conn = connectToDatabasee();

    try {
        String sql = "SELECT * FROM feedback WHERE feedbackrate >= 0  && feedbackrate <= 3";
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            String description = rs.getString("descriptionn");
            int feedbackIdResult = rs.getInt("feedbackid");
            int eventid = rs.getInt("eventid");
            String feedbackstatus = rs.getString("feedbackstatus");
            int feedbackrate = rs.getInt("feedbackrate");
            
            System.out.println("Description: " + description);
            System.out.println("Feedback ID: " + feedbackIdResult);
            System.out.println("Event ID: " + eventid);
            System.out.println("Status: " + feedbackstatus);
            System.out.println("Feedback Rate: " + feedbackrate);
            
            
            
            
            System.out.println("--------------------------------");
        }
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.out.println("Error retrieving feedback data: " + e.getMessage());
    }
       
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
}
