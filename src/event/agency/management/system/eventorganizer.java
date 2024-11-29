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

public class eventorganizer extends person {
    public String companyName;
    public int eventId;
    int id;
    public database db = new database();

    private Connection con;

    private final String userName = "root";
    private final String dbName = "eventagency";

    private event event = new event();

    ArrayList<eventorganizer> eventorganizers = new ArrayList<eventorganizer>();
    
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


    public eventorganizer(String name, String phoneNo, String email, String age, String password, int id, int personTypeID, String companyName,int eventId) {
        super(name, phoneNo, email, age, password, id, personTypeID);
        this.companyName = companyName;
        this.eventId = eventId;

    }


    public eventorganizer() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, "");
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    public eventorganizer(String companyName) {
        this.companyName = companyName;
    }

    public void addOrganizerData( ){
        System.out.println("Enter your name");
        Scanner name = new Scanner(System.in);
        String customerName = name.nextLine();
        setName(customerName);

        System.out.println("Enter your phone Number");
        Scanner phone = new Scanner(System.in);
        String customerPhone = phone.nextLine();
        setPhoneNo(customerPhone);

        System.out.println("Enter your Email");
        Scanner Email = new Scanner(System.in);
        String customerEmail = Email.nextLine();
        setEmail(customerEmail);

        System.out.println("Enter your Age");
        Scanner Age = new Scanner(System.in);
        String customerAge = Age.nextLine();
        setAge(customerAge);

        System.out.println("Enter your Password");
        Scanner Password = new Scanner(System.in);
        String customerPassword = Password.nextLine();
        setPassword(customerPassword);


        System.out.println("Enter your Company Name");
        Scanner companyName = new Scanner(System.in);
        String CompanyName = companyName.nextLine();
        setCompanyName(CompanyName);
        id = db.IncrementId();

        event.showEvents();
        System.out.println("Enter the event id that this organizer can organize ");
        Scanner Id = new Scanner(System.in);
        int eventid = Id.nextInt();

        eventorganizer eventorganizer = new eventorganizer(customerName,customerPhone,customerEmail,customerAge,customerPassword, id,2,CompanyName,eventId);
        eventorganizers.add(eventorganizer);
        db.addEventOrganizerToDb(eventorganizer,eventid);
    }

    public void editEvent(){
        event.editEventDetails();
    }

    public void addSponsosrToEvent(){
        db.showSponsors();
        System.out.println("Enter the id of the sponsor that you want to add");
        Scanner id = new Scanner(System.in);
        int sponsorId = id.nextInt();
        db.showEvents();

        System.out.println("Enter the id of the event that you want to add the sponsor too");
        Scanner id2 = new Scanner(System.in);
        int eventId = id2.nextInt();

        db.sponsorEvent(sponsorId,eventId);
    }

    public void addServiceToEvent(){
//        db.show
        System.out.println("Enter the id of the service that you want to add");
        Scanner id = new Scanner(System.in);
        int serviceId = id.nextInt();
        db.showEvents();

        System.out.println("Enter the id of the event that you want to add the service too");
        Scanner id2 = new Scanner(System.in);
        int eventId = id2.nextInt();

        db.serviceEvent(serviceId,eventId);
    }

   
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public void showEventOrganizer() {
        ArrayList<eventorganizer> eventOrgainzers = new ArrayList<eventorganizer>();
        int eventId = 0;
        int organizerId = 0;
        int i = 0;
        eventorganizer eo = new eventorganizer();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from person");

            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select * from eventorganizer");
            while (rs.next() && rs2.next()) {
                eo = new eventorganizer(rs.getString("namee"), rs.getString("phonenumber"),rs.getString("email"),
                        rs.getString("age"),rs.getString("passwordd"),
                        rs2.getInt("personid"),rs.getInt("persontypeid"),
                        rs2.getString("companyname"),rs2.getInt("eventid"));
                eventOrgainzers.add(eo);
                eventId = rs2.getInt("eventid");
                organizerId = rs2.getInt("personid");
                printOrganizers(eo,eventId,organizerId);
                i += 1;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

    }

    public void printOrganizers(eventorganizer eo, int eventId,int organizerId){
        System.out.println("Name "+ eo.getName());
        System.out.println("Phone num "+ eo.getPhoneNo());
        System.out.println("Age "+ eo.getAge());
        System.out.println("Password "+ eo.getPassword());
        System.out.println("Company name "+ eo.getCompanyName());
        System.out.println("Organizing "+ getEventName(eventId));
        System.out.println("Id "+ organizerId);
        System.out.println("---------------------------------");

    }

    public void deleteEventOrganizer() {
        showEventOrganizer();
        System.out.println("Enter the organizer id that you want to remove");
        Scanner id = new Scanner(System.in);
        int organizerId = id.nextInt();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM person WHERE personid = ?");
            stmt.setInt(1, organizerId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("person with ID " + organizerId + " deleted successfully.");
            }

            stmt = con.prepareStatement("DELETE FROM eventorganizer WHERE personid = ?");
            stmt.setInt(1, organizerId);
            int rowsDeleted2 = stmt.executeUpdate();
            if (rowsDeleted2 > 0) {
                System.out.println("Event organizer with ID " + organizerId + " deleted successfully.");
            }

        } catch (Exception e) {
            System.err.println("Error deleting event organizer from database: " + e.getMessage());
        }
    }


    public String getEventName(int eventId){
        try {
            String eventName = "";


            String sql = "select eventname from eventt where eventId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                eventName = rs.getString("eventname");
                return eventName;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        System.out.println("EventId not found");
        return "";
    }


public void addEventDetails() {
        con = connectToDatabase();
        ArrayList<event> newevents = new ArrayList<event>();
        System.out.println("Enter the event Cost");
        Scanner cost = new Scanner(System.in);
        int eventcost =cost.nextInt();
        System.out.println("Enter the event id");
        Scanner id = new Scanner(System.in);
        int eventid =id.nextInt();
        System.out.println("Enter the event name");
        Scanner name = new Scanner(System.in);
        String eventname =name.next();
        event ev =new event (eventcost,eventname,eventid);
        newevents.add(ev);
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO pendingevent (cost, eventname,eventid) VALUES (?, ?, ?)");
            stmt.setInt(1, eventcost);
            stmt.setString(2, eventname);
            stmt.setInt(3, eventid);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Event details submitted successfully!");
            } else {
                System.out.println("Error submitting Event details");
            }
        }
        catch (Exception v) {
            System.err.println("DATABASE INSERTION ERROR: " + v.toString());
        }
    }

    public boolean login(){
        con = connectToDatabase();
        System.out.println("Enter your name:");
        Scanner x = new Scanner(System.in);
        String name = x.nextLine();

        System.out.println("Enter your password");
        Scanner y = new Scanner(System.in);
        String password = y.nextLine();

        if(validateOrganizerInfo(name,password)){
            System.out.println("Valid Login");
            return true;
        }
        else{
            System.out.println("Invalid Login!!");
        }
        return false;
    }
    
    public boolean validateOrganizerInfo(String name, String password) {
    try {
        String query = "SELECT * FROM person WHERE namee=? AND passwordd=? AND persontypeid=3";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            return true;
        }
    } catch (Exception e) {
        System.err.println("DATABASE ACCESS ERROR: " + e.toString());
    }
    return false;
}
    
    public void updateAccount(){
    System.out.println("Enter your name:");
    Scanner x = new Scanner(System.in);
    String name = x.nextLine();
    
    System.out.println("Enter your password");
    Scanner y = new Scanner(System.in);
    String password = y.nextLine();
    
    con = connectToDatabase();
    try {           
        String query = "SELECT * FROM person WHERE namee=? AND passwordd=? AND persontypeid=3";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            String oname = rs.getString("namee");
            String ophonenumber = rs.getString("phonenumber");
            String oemail = rs.getString("email");
            int oage = rs.getInt("age");
            
            System.out.println("Enter new phone number:");
            Scanner z = new Scanner(System.in);
            String phonenumber = z.nextLine();
            
            System.out.println("Enter new email:");
            Scanner w = new Scanner(System.in);
            String email = w.nextLine();
            
            System.out.println("Enter new age:");
            Scanner v = new Scanner(System.in);
            int age = v.nextInt();
            
            System.out.println("Enter new name:");
            Scanner i = new Scanner(System.in);
            String nname = i.nextLine();
            
            System.out.println("Enter new password:");
            Scanner j = new Scanner(System.in);
            String npassword = j.nextLine();
            
            String updateQuery = "UPDATE person SET phonenumber=?, email=?, age=?, namee=?, passwordd=? WHERE namee=? AND passwordd=? AND persontypeid=3";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setString(1, phonenumber);
            updateStmt.setString(2, email);
            updateStmt.setInt(3, age);
            updateStmt.setString(4, nname);
            updateStmt.setString(5, npassword);
            updateStmt.setString(6, name);
            updateStmt.setString(7, password);
            
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Account information updated successfully.");
            } else {
                System.out.println("No account information updated.");
            }
        } else {
            System.out.println("Invalid name or password.");
        }
    } catch (Exception e) {
        System.err.println("DATABASE ACCESS ERROR: " + e.toString());
    }
}



    public String getSponsorName(int sponsorId){
        try {
            String sponsorName = "";


            String sql = "select sponsorname from sponsor where sponsorid = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,sponsorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sponsorName = rs.getString("sponsorname");
                return sponsorName;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        System.out.println("EventId not found");
        return "";
    }


    public void addEventDetailsGui(int eventcost,String eventname,int eventid){
        con = connectToDatabase();
        ArrayList<event> newevents = new ArrayList<event>();
        event ev =new event (eventcost,eventname,eventid);
        newevents.add(ev);
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO pendingevent (cost, eventname,eventid) VALUES (?, ?, ?)");
            stmt.setInt(1, eventcost);
            stmt.setString(2, eventname);
            stmt.setInt(3, eventid);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Event details submitted successfully!");
            } else {
                System.out.println("Error submitting Event details");
            }
        }
        catch (Exception v) {
            System.err.println("DATABASE INSERTION ERROR: " + v.toString());
        }
    }
    public void deleteEventOrganizerGui(int organizerId ) {
        showEventOrganizer();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM person WHERE personid = ?");
            stmt.setInt(1, organizerId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("person with ID " + organizerId + " deleted successfully.");
            }

            stmt = con.prepareStatement("DELETE FROM eventorganizer WHERE personid = ?");
            stmt.setInt(1, organizerId);
            int rowsDeleted2 = stmt.executeUpdate();
            if (rowsDeleted2 > 0) {
                System.out.println("Event organizer with ID " + organizerId + " deleted successfully.");
            }

        } catch (Exception e) {
            System.err.println("Error deleting event organizer from database: " + e.getMessage());
        }
    }
    public ArrayList<eventorganizer> showEventOrganizergui() {
        ArrayList<eventorganizer> eventOrgainzers = new ArrayList<eventorganizer>();
        int eventId = 0;
        int organizerId = 0;
        int i = 0;
        eventorganizer eo = new eventorganizer();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from person WHERE persontypeid=3 ");

            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select * from eventorganizer");
            while (rs.next() && rs2.next()) {
                eo = new eventorganizer(rs.getString("namee"), rs.getString("phonenumber"),rs.getString("email"),
                        rs.getString("age"),rs.getString("passwordd"),
                        rs2.getInt("personid"),rs.getInt("persontypeid"),
                        rs2.getString("companyname"),rs2.getInt("eventid"));
                eventOrgainzers.add(eo);
                eventId = rs2.getInt("eventid");
                organizerId = rs2.getInt("personid");
                printOrganizers(eo,eventId,organizerId);
                i += 1;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return eventOrgainzers;
    }
    
}