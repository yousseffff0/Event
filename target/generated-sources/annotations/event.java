package event.agency.management.system;
import javax.security.auth.Subject;
import java.sql.*;
import java.sql.Connection;
import java.util.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class event implements Subject,ReadOnlyEvents {

    private ArrayList<Observer> observers = new ArrayList();
    private SMSSubscriber SMSSubscriber = new SMSSubscriber();
    private EmailSubscriber emailSubscriber = new EmailSubscriber();


    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public List<event> getAllEvents() {

        List<event> events = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM eventt");

            while (resultSet.next()) {
                event event = new event();
                event.setEventId(resultSet.getInt("eventid"));
                event.setEventName(resultSet.getString("eventname"));
                event.setCost(resultSet.getInt("cost"));

                events.add(event);

                // Print the event to the console

                System.out.println(event.toString());

            }

        } catch (SQLException e) {
            // handle exception
        }

        return events;
    }

    private int cost;
    private String eventName;
    private int eventId;
    private Connection conn;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";
    database db = new database();

    private ArrayList<event> eventList = new ArrayList<event>();


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int id) {
        this.eventId = id;
    }

    public event(int cost, String eventName, int id) {
        this.cost = cost;
        this.eventName = eventName;
        this.eventId = id;
    }

    public event() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }
//    public void addEvent(event myevent ){
//        try {
//            PreparedStatement stmt = conn.prepareStatement("INSERT INTO eventt (cost, eventname, eventid) VALUES (?, ?, ?)");
//            stmt.setInt(1, myevent.getCost());
//            stmt.setString(2, myevent.getEventName());
//            stmt.setInt(3, myevent.getEventId());
//            stmt.executeUpdate();
//            eventList.add(myevent);
//            notifyCustomers("Added","A");
//        } catch (Exception e) {
//            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
//        }


    public void deleteEvent() {
        String eventName = "";
        showEvents();
        System.out.println("Enter the event id that you want to remove");
        Scanner id = new Scanner(System.in);
        int eventId = id.nextInt();
        eventName = db.getEventName(eventId);

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM eventt WHERE eventid = ?");
            stmt.setInt(1, eventId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Event with ID " + eventId + " deleted successfully.");

                notifyCustomers("Deleted", eventName);
            } else {
                System.out.println("Event with ID " + eventId + " not found in the database.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting event from database: " + e.getMessage());
        }
    }


    public void showEventsDetails() {
        ArrayList<event> events = new ArrayList<event>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from eventt");
            while (rs.next()) {
                events.add(new event(rs.getInt("cost"), rs.getString("eventname"), rs.getInt("eventid")));
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        for (event e : events) {
            System.out.println("Event cost " + e.getCost());
            System.out.println("Event id " + e.getEventId());
            System.out.println("Event name " + e.getEventName());
            System.out.println("---------------------------------");

        }

    }


    public void editEventDetails() {
        ArrayList<event> events = new ArrayList<event>();
        events = db.returnEvents();

        for (event e : events) {
            System.out.println("Event cost " + e.getCost());
            System.out.println("Event id " + e.getEventId());
            System.out.println("Event name " + e.getEventName());
            System.out.println("---------------------------------");

        }
        updateEvent();

    }

    public void viewPendingEventDetails() {
        ArrayList<event> events = new ArrayList<event>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from pendingevent");
            while (rs.next()) {
                events.add(new event(rs.getInt("cost"), rs.getString("eventname"), rs.getInt("eventid")));
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        for (event e : events) {
            System.out.println("Event cost " + e.getCost());
            System.out.println("Event id " + e.getEventId());
            System.out.println("Event name " + e.getEventName());
            System.out.println("---------------------------------");

        }
    }

    public event returnEventInfo(int eventId) {
        event event = new event();
        String eventName ="";
        int eventCost = 0;
        try {
            PreparedStatement selectStmt = conn.prepareStatement("SELECT cost, eventname FROM eventt WHERE eventid = ?");
            selectStmt.setInt(1, eventId);
            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {
                eventCost = resultSet.getInt("cost");
                eventName = resultSet.getString("eventname");
                return new event(eventCost,eventName,eventId);
            } else {
                System.out.println("Event not found");
                return event;
            }

        }catch(SQLException e){
            System.err.println("DATABASE ERROR: " + e.getMessage());
            }
        return event;

        }

    public void ChooseEventToConfirm(){
        viewPendingEventDetails();
        System.out.println("Enter the event id that you want to add");
        Scanner id = new Scanner(System.in);
        int eventId = id.nextInt();
        ConfirmEvent(eventId);
    }

    public  void ConfirmEvent(int eventId)  {
        event event = new event();
        try {
            PreparedStatement selectStmt = conn.prepareStatement("SELECT cost, eventname FROM pendingevent WHERE eventid = ?");
            selectStmt.setInt(1, eventId);
            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {

                int cost = resultSet.getInt("cost");
                String eventName = resultSet.getString("eventname");

                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO eventt (cost, eventname, eventid) VALUES (?, ?, ?)");
                insertStmt.setInt(1, cost);
                insertStmt.setString(2, eventName);
                insertStmt.setInt(3, eventId);
                int rows = insertStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Event added successfully!");
                    event.notifyCustomers("Added", eventName);

                } else {
                    System.out.println("Error adding Event ");
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


    public void updateEvent() {
        showEvents();
        String eventName = "";
        System.out.println("Enter the event id that you want to update");
        Scanner id = new Scanner(System.in);
        int eventId = id.nextInt();

        System.out.println("Enter the event cost that you want to update");
        Scanner cost = new Scanner(System.in);
        int eventCost = id.nextInt();

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE eventt SET cost=? WHERE eventid=?");
            stmt.setInt(1, eventCost);
            stmt.setInt(2, eventId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Event with ID " + id + " not found in the database.");
            }
        } catch (Exception e) {
            System.err.println("Error updating service in database: " + e.getMessage());
        }
    }

   public void updateEventWithSponsorAndService(){
        showEvents();
        System.out.println("Enter the event id that you want to update cost");
        Scanner id = new Scanner(System.in);
        int eventid = id.nextInt();
        int totalCost = 0;
        totalCost = db.getEventCost(eventid);
       System.out.println(totalCost);
        totalCost = totalCost + db.getServiceAssignedEventPrice(eventid);
       System.out.println(totalCost);
        totalCost = totalCost - db.getSponsorAssignedEventPrice(eventid);
       System.out.println(totalCost);
       try {
           PreparedStatement stmt = conn.prepareStatement("UPDATE eventt SET cost=? WHERE eventid=?");
           stmt.setInt(1, totalCost);
           stmt.setInt(2, eventid);
           int rowsUpdated = stmt.executeUpdate();

           if (rowsUpdated > 0) {
               System.out.println("Event with ID " + id + " not found in the database.");
           }
       } catch (Exception e) {
           System.err.println("Error updating service in database: " + e.getMessage());
       }

    }

    public ArrayList<event> showEvents() {
        ArrayList<event> events = new ArrayList<event>();
        try {
            Statement stmt = conn.createStatement();
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
        return  events;
    }


    public void notifyCustomers(String action, String eventName) {
        String message = "An event has been " + action + ": " + eventName;
        System.out.println("Sending message "+ message);
        updateAll(message);

    }

    public void notifyCustomers(String message) {
        System.out.println("Sending message "+ message);
        updateAll(message);

    }

    @Override
    public void addObserver(Observer o) {
        if (o instanceof  EmailSubscriber) {
            int id = db.IncrementSubscriberId();
            addEmailSubscriberToDatabase(o,id);
        }
        else if (o instanceof SMSSubscriber){
            int id = db.IncrementSMSSubscriberId();
            addSmSSubscriberToDatabase(o,id);
        }
    }

    public void subscribeToEmail(){
        System.out.println("Enter your name");
        Scanner name = new Scanner(System.in);
        String customerName = name.nextLine();

        System.out.println("Enter your email");
        Scanner email = new Scanner(System.in);
        String customerEmail = email.nextLine();
        int id = db.IncrementSubscriberId();
        Observer o = emailSubscriber = new EmailSubscriber(customerName,customerEmail);
        addObserver(o);
    }

    public void subscribeToSms(){
        System.out.println("Enter your name");
        Scanner name = new Scanner(System.in);
        String customerName = name.nextLine();

        System.out.println("Enter your phone number");
        Scanner phoneNum = new Scanner(System.in);
        String customerPhoneNum = phoneNum.nextLine();
        Observer o = SMSSubscriber= new SMSSubscriber(customerName,customerPhoneNum);
        addObserver(o);
    }

    public void addEmailSubscriberToDatabase(Observer o, int id){
        if(o instanceof EmailSubscriber) {
            try {
                PreparedStatement stmt = conn.prepareStatement("insert into subscriber (name, email,id) VALUES(?,?,?) ");
                stmt.setString(1, ((EmailSubscriber) o).getName());
                stmt.setString(2, ((EmailSubscriber) o).getEmail());
                stmt.setInt(3, id);

                stmt.executeUpdate();
                System.out.println("Observer Added to database");

            } catch (Exception e) {
                System.err.println("DATABASE INSERTION ERROR: " + e.toString());
            }
        }
    }

    public void addSmSSubscriberToDatabase(Observer o , int id){
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into smssubscriber (name, phoneNumber,id) VALUES(?,?,?) ");
            stmt.setString(1, ((SMSSubscriber) o).getName());
            stmt.setString(2, ((SMSSubscriber) o).getMobileNo());
            stmt.setInt(3, id);

            stmt.executeUpdate();
            System.out.println("Observer Added to database");

        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }

    }




    @Override
    public void removeObserver(Observer o) {
//        showEmailSubscribers();
        if (o instanceof  EmailSubscriber) {
            String name = "";
            String email = "";
            name =  ((EmailSubscriber) o).getName();
            email = ((EmailSubscriber) o).getEmail();
            removeEmailSubscriberFromDatabase(name,email);
        }
        else if (o instanceof SMSSubscriber){
            String name = "";
            String PhoneNum ="";
            name =  ((SMSSubscriber) o).getName();
            PhoneNum = ((SMSSubscriber) o).getMobileNo();
            removeSMSSubscriberFromDatabase(name,PhoneNum);
        }

    }
    public void removeEmailSubscriberFromDatabase(String name, String email){
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM subscriber WHERE name = ? and email = ?");
            stmt.setString(1, name);
            stmt.setString(2, email);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("subscriber with name " + name + " and email " + email + " deleted successfully.");
            }

        } catch (Exception e) {
            System.err.println("Error deleting subscriber from database: " + e.getMessage());
        }
    }

    public void removeSMSSubscriberFromDatabase(String name, String phoneNumber){
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM smssubscriber WHERE name = ? and phoneNumber = ?");
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("subscriber with name " + name + " and phone number " + phoneNumber + " deleted successfully.");
            }

        } catch (Exception e) {
            System.err.println("Error deleting subscriber from database: " + e.getMessage());
        }
    }

    public void unSubscribeFromEmail(){

        System.out.println("Enter your name");
        Scanner name = new Scanner(System.in);
        String customerName = name.nextLine();

        System.out.println("Enter your email");
        Scanner email = new Scanner(System.in);
        String customerEmail = email.nextLine();

        int id = db.IncrementSubscriberId();
        Observer o = emailSubscriber = new EmailSubscriber(customerName, customerEmail);
        removeObserver(o);
    }

    public void unSubscribeFromSMS(){

        System.out.println("Enter your name");
        Scanner name = new Scanner(System.in);
        String customerName = name.nextLine();

        System.out.println("Enter your phone number");
        Scanner num = new Scanner(System.in);
        String customerPhoneNum = num.nextLine();

        int id = db.IncrementSubscriberId();
        Observer o = SMSSubscriber = new SMSSubscriber(customerName, customerPhoneNum);
        removeObserver(o);
    }

    @Override
    public void updateAll(String message) {
        ArrayList<String> mails = new ArrayList<String>();
        EmailSubscriber es = new EmailSubscriber();
        SMSSubscriber ss = new SMSSubscriber();
        mails = db.getSubscriberEmail();
        es.Update(message);
        ss.Update(message);



    }

}