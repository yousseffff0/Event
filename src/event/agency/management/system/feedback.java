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

public class feedback {
    private String description;
    private int feedbackId;
    private int eventid;
    private String feedbackStatues;
    private int feedbackRate;
    private String eventName;
    private FeedbackHandler feedbackHandler;
    database db = new database();
    public ArrayList<feedback> feedback=new ArrayList<feedback>();
    
    
    private Connection conn;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getFeedbackStatues() {
        return feedbackStatues;
    }

    public void setFeedbackStatues(String feedbackStatues) {
        this.feedbackStatues = feedbackStatues;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public int getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(int feedbackRate) {
        this.feedbackRate = feedbackRate;
    }
    


    public feedback(String description, int feedbackId, int eventid, String feedbackStatues,int feedbackRate) {
        this.description = description;
        this.feedbackId = feedbackId;
        this.feedbackStatues = feedbackStatues;
        this.feedbackRate=feedbackRate;
        this.eventid = eventid;
        
    }
    public feedback(){
         feedback = new ArrayList<>();
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
    
    
    public void viewFeedback(int feedbackId) {
        conn = connectToDatabase();
    try {
        
        String sql = "SELECT * FROM feedback WHERE feedbackid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, feedbackId);

        
        ResultSet rs = stmt.executeQuery();
        
        

        
        while (rs.next()) {
            String description = rs.getString("descriptionn");
            int feedbackIdResult = rs.getInt("feedbackid");
            String eventName = rs.getString("eventName");
            String feedbackStatues = rs.getString("feedbackstatus");
            int eventid = rs.getInt("eventid");
            String feedbackType = rs.getString("feedbackRate");
            
            System.out.println("Feedback ID: " + feedbackId);
            System.out.println("Event Name: " + eventName);
            System.out.println("Description: " + description);
            System.out.println("Status: " + feedbackStatues);
            System.out.println("Event ID: " + eventid);
            System.out.println("feedbackRate: " + feedbackRate);
            System.out.println("--------------------------------");
        }

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.out.println("Error retrieving feedback data: " + e.getMessage());
    }
}
    
public void submitFeedback(String description, int feedbackId,int eventId ,String feedbackstatus,int feedbackRate) {
    
     if (validateFeedback(feedbackId,eventId)) {
        
        
    
     conn = connectToDatabase();
     feedbackId=db.IncrementFeedbackId();
    try {
        
        String sql = "INSERT INTO feedback (descriptionn,feedbackid,eventid,feedbackstatus,feedbackrate) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, description);
        stmt.setInt(2, feedbackId);
        stmt.setInt(3, eventId);
        stmt.setString(4, feedbackstatus);
        stmt.setInt(5, feedbackRate);
        
        
        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Feedback submitted successfully!");
        }
        else {
            System.out.println("Error submitting feedback");
        }

        
        stmt.close();
    } catch (Exception e) {
        System.out.println("Error submitting feedback: " + e.getMessage());
    }
  }else{
         System.out.println("Error submitting feedback123456");
     }
}
public boolean validateFeedback(int feedbackId, int eventId) {
    conn = connectToDatabase();
    try {
        
        String sql = "SELECT COUNT(*) FROM feedback WHERE feedbackid = ? AND eventid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, feedbackId);
        stmt.setInt(2, eventId);

        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        
        rs.close();
        stmt.close();

        
        return count >= 0;
        
    } catch (Exception e) {
        System.out.println("Error validating feedback: " + e.getMessage());
        return false;
    }
}
//interface 3shan a pass el rate

    public void setFeedbackHandler(FeedbackHandler feedbackHandler) {
        this.feedbackHandler = feedbackHandler;
    }

public void FeedbackHandlerr(){
    feedbackHandler.FeedbackHandlerr();
}

public void addExsistingfeedbacks() {
    conn = connectToDatabase();
        try {           
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM feedback");
            
            while (rs.next()) {
                String description = rs.getString("descriptionn");
                int feedbackid = rs.getInt("feedbackid");
                int eventid = rs.getInt("eventid");
                String feedbackstatus = rs.getString("feedbackstatus");
                int feedbackrate = rs.getInt("feedbackrate");
                feedback fb = new feedback(description, feedbackid,eventid,feedbackstatus,feedbackrate);
                feedback.add(fb);
            }
            Collections.sort(feedback, (f1, f2) -> f1.getFeedbackRate() - f2.getFeedbackRate());

        } 
        
        catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
    }

public void showfeedbacks() {
        for (int i = 0; i < feedback.size(); i++) {
        System.out.println(feedback.get(i).getDescription()+ " - " + feedback.get(i).getFeedbackId()+ " - " + feedback.get(i).getEventid()+ " - " + feedback.get(i).getFeedbackStatues()+ " - " + feedback.get(i).getFeedbackRate());
    }
    }

public void feedbackCall(){
            Scanner feedbackall = new Scanner(System.in);
                        System.out.println("1 to view all feedbacks");
                        System.out.println("2 to view positvie feedbacks");
                        System.out.println("3 to view neutral feedbacks");
                        System.out.println("4 to view negative feedbacks");
                        int y=feedbackall.nextInt();    
                switch(y){
                    case 1:showfeedbacks();
                        break;
                    case 2:setFeedbackHandler(new PositiveFeedbackHandler());
                    FeedbackHandlerr();
                        break;
                    case 3:setFeedbackHandler(new NeutralFeedbackHandler());
                    FeedbackHandlerr();
                        break;
                    case 4:setFeedbackHandler(new NegativeFeedbackHandler());
                    FeedbackHandlerr();
                    break;
                }
        }

}
