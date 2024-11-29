package event.agency.management.system;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class database {

    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";

    private Connection con;

    public database() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    public void addCustomer(customer c) {

        int id = IncrementId();
        try {
            PreparedStatement stmt = con.prepareStatement("insert into person (namee, phonenumber,email,age,passwordd,personid,persontypeid) VALUES(?,?,?,?,?,?,?) "  );
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getPhoneNo());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getAge());
            stmt.setString(5, c.getPassword());
            stmt.setInt(6, id);
            stmt.setInt(7, 1);

            stmt.executeUpdate();


            stmt = con.prepareStatement("insert into customer (balance,personid,persontypeid ) VALUES(?,?,?) "  );
            stmt.setInt(1, c.getBalance());
            stmt.setInt(2, id);
            stmt.setInt(3, 1);
            stmt.executeUpdate();



            System.out.println("Customer added");
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }
    }

    public void deleteCustomer() {
        System.out.println("Enter the organizer id that you want to remove");
        Scanner id = new Scanner(System.in);
        int customerId = id.nextInt();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM person WHERE personid = ?");
            stmt.setInt(1, customerId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("person with ID " + customerId + " deleted successfully.");
            }

             stmt = con.prepareStatement("DELETE FROM customer WHERE personid = ?");
            stmt.setInt(1, customerId);
            int rowsDeleted2 = stmt.executeUpdate();
            if (rowsDeleted2 > 0) {
                System.out.println("customer with ID " + customerId + " deleted successfully.");
            }

        } catch (Exception e) {
            System.err.println("Error deleting customer from database: " + e.getMessage());
        }
    }
    
    
    public void addPayment(payment p) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into payment values('" + p.getPaymentid()+ "')");
            System.out.println("payment added");
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }
    }

    public int IncrementId(){
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from person order by personid DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("personid");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

        return 0;

    }
    public int IncrementSponsorId(){
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from sponsor order by sponsorid DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("sponsorid");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
    }

    public int IncrementServiceId(){
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from servicee order by serviceid DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("serviceid");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

        return 0;

    }

    public int IncrementSubscriberId(){
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from subscriber order by id DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("id");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

        return 0;

    }

    public int IncrementSMSSubscriberId(){
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from smssubscriber order by id DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("id");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

        return 0;

    }
    public int IncrementFeedbackId(){
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from feedback order by feedbackid DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("feedbackid");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
    }

    public int IncrementEventId(){
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from eventt order by eventid DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("eventid");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
    }

    public void addSponsor(sponsor s){
        int sponsorId = IncrementSponsorId();
        try {

        PreparedStatement stmt = con.prepareStatement("insert into sponsor (sponsorname, sponsorprice,sponsorid,eventid) VALUES(?,?,?,?) "  );
        stmt.setString(1, s.getSponsorName());
        stmt.setInt(2, s.getSponsorPrice());
        stmt.setInt(3, sponsorId);
        stmt.setInt(4, s.getEventId());
        stmt.executeUpdate();
            System.out.println("Sponsor Added to database");
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }

    }

    public void showSponsors() {
        ArrayList<sponsor> sponsors = new ArrayList<sponsor>();
        int sponsorId = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from sponsor");
            while (rs.next()) {
                sponsors.add(new sponsor(rs.getString("sponsorname"),rs.getInt("sponsorprice"),rs.getInt("sponsorid"),rs.getInt("eventid")));
                sponsorId = rs.getInt("sponsorid");
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        for (sponsor s : sponsors) {
            System.out.println("Sponsor name "+ s.getSponsorName());
            System.out.println("Sponsor price "+ s.getSponsorPrice());
            System.out.println("Sponsor id "+ s.getSponsorId());
            System.out.println("Event id " + s.getEventId());
            System.out.println("---------------------------------");

        }

    }


        public ArrayList<event> returnEvents() {
            ArrayList<event> events = new ArrayList<event>();
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from eventt");
                while (rs.next()) {
                    events.add(new event(rs.getInt("cost"), rs.getString("eventname"),rs.getInt("eventid")));
                }
            } catch (Exception e) {
                System.err.println("DATABASE QUERY ERROR: " + e.toString());
            }

            return events;
        }
        
    public void showEvents() {
        ArrayList<event> events = new ArrayList<event>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from eventt");
            while (rs.next()) {
                events.add(new event(rs.getInt("cost"), rs.getString("eventname"),rs.getInt("eventid")));
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        for (event e : events) {
            System.out.println("Event cost "+ e.getCost());
            System.out.println("Event id "+ e.getEventId());
            System.out.println("Event name "+ e.getEventName());
            System.out.println("---------------------------------");

        }

    }

    public void addEventOrganizerToDb(eventorganizer eo, int eventId) {

        try {
            PreparedStatement stmt = con.prepareStatement("insert into person (namee, phonenumber,email,age,passwordd,personid,persontypeid) VALUES(?,?,?,?,?,?,?) "  );
            stmt.setString(1, eo.getName());
            stmt.setString(2, eo.getPhoneNo());
            stmt.setString(3, eo.getEmail());
            stmt.setString(4, eo.getAge());
            stmt.setString(5, eo.getPassword());
            stmt.setInt(6, IncrementId());
            person p = new person();
            stmt.setInt(7, 3);

            stmt.executeUpdate();


            stmt = con.prepareStatement("insert into eventorganizer (companyname,personid,persontypeid,eventid ) VALUES(?,?,?,?) "  );
            stmt.setString(1, eo.getCompanyName());
            stmt.setInt(2, IncrementId());
            stmt.setInt(3, 3);
            stmt.setInt(4,eventId);
            stmt.executeUpdate();



            System.out.println("eventOrganizer added");
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }
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




    public ArrayList<String> getSubscriberEmail(){
        ArrayList<String> emails = new ArrayList<String>();
        String email = " ";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from subscriber");
            while (rs.next()) {
                emails.add((rs.getString("email")));
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

        return emails;
    }

    public ArrayList<String> getSubscriberPhoneNumber(){
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        String email = " ";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from smssubscriber");
            while (rs.next()) {
                phoneNumbers.add((rs.getString("phoneNumber")));
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

        return phoneNumbers;
    }




    public int getEventCost(int eventId){
        int eventCost =0;
        try {
            String sql = "select cost from eventt where eventId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                eventCost = rs.getInt("cost");
                return eventCost;
            }
            else{
                System.out.println("EventId not found");

            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
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
            else {
                System.out.println("EventId34 not found");
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return "";
    }


    public String getServiceName(int eventId){
        try {
            String serviceName = "";

            String sql = "select servicename from servicee where eventid = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                serviceName = rs.getString("servicename");
                return serviceName;
            }
            else {
                System.out.println("Service id not found");

            }

        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return "";
    }

    public String getSponsorName(int eventId){
        String sponsorName = "";
        try {
            String sql = "select sponsorname from sponsor where eventid = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sponsorName = rs.getString("sponsorname");
                return sponsorName;
            }
            else {
                System.out.println("EventId11 not found");

            }

        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return "";
    }

    public int getSponsorAssignedEventPrice( int eventId){
        int sponsorCost = 0;
        try {

            String sql = "select sponsor.sponsorprice " +
                    "from sponsor " +
                    "inner join eventt on sponsor.eventid = eventt.eventid " +
                    "where sponsor.eventid = ? and eventt.eventid = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,eventId);
            stmt.setInt(2,eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sponsorCost = rs.getInt("sponsorprice");
                return sponsorCost;
            } else{
                System.out.println("EventId55 not found");
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
    }

    public int getServiceAssignedEventPrice( int eventId){
        try {
            int serviceCost = 0;

            String sql = "select servicee.serviceprice " +
                    "from servicee " +
                    "inner join eventt on servicee.eventid = eventt.eventid " +
                    "where servicee.eventid = ? and eventt.eventid = ?";
            ;
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,eventId);
            stmt.setInt(2,eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                serviceCost = rs.getInt("serviceprice");
                return serviceCost;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
    }


    public void serviceEvent(int serviceId,int eventId) {
        event event = new event();

        String eventName = getEventName(eventId);
        String serviceName = getSponsorName(serviceId);

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE servicee SET eventid=? WHERE serviceid=?");
            stmt.setInt(1, eventId);
            stmt.setInt(2, serviceId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Service with ID " + serviceId + " updated successfully.");
                String message = serviceName +" is in "+ eventName + " !!!!";
                event.notifyCustomers(message);

            } else {
                System.out.println("Sponsor with ID " + serviceId + " not found in the database.");
            }
        } catch (Exception e) {
            System.err.println("Error updating sponsor in database: " + e.getMessage());
        }
    }

    public void sponsorEvent(int sponsorId,int eventId) {

        event event = new event();
        String eventName = getEventName(eventId);
        String sponsorName = getSponsorName(eventId);

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE sponsor SET eventid=? WHERE sponsorid=?");
            stmt.setInt(1, eventId);
            stmt.setInt(2, sponsorId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Sponsor with ID " + sponsorId + " updated successfully.");
                String message = sponsorName +" is coming to "+ eventName + " !!!!";
                 event.notifyCustomers(message);

            } else {
                System.out.println("Sponsor with ID " + sponsorId + " not found in the database.");
            }
        } catch (Exception e) {
            System.err.println("Error updating sponsor in database: " + e.getMessage());
        }
    }




}