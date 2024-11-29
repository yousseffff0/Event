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

public class ticket {
    public int price;
    public String type;
    public String eventname;
    public int eventid;
    public int quantity;
    private ArrayList<ticketcode> ticketcodes = new ArrayList();
    private ArrayList<ticket> tickets = new ArrayList();
    
    private Connection conn;
    private String username = "root";
    private String password = "";
    private final String dbName = "eventagency";
    public ArrayList<promocode> promocodesCopy;
    
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

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public ticket(int price, String type, String eventname, int eventid,int quantity) {
        this.price = price;
        this.type = type;
        this.eventname = eventname;
        this.eventid=eventid;
        this.quantity = quantity;
    }
    

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public ticket() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, username, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    
    public ArrayList<promocode> getpromocodes() {
        conn=connectToDatabase();
        ArrayList<promocode> codes = new ArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from promocode");
            while (rs.next()) {
                codes.add(new promocode(rs.getString("promocode"), rs.getString("statuss")));
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        promocodesCopy=codes;
        return codes;
 }
    
   public void showPromocodes() {
       conn=connectToDatabase();
    for (int i = 0; i < promocodesCopy.size(); i++) {
        System.out.println(promocodesCopy.get(i).getPromocode() + promocodesCopy.get(i).getStatus());
    }
}
 
   public void checkPromocode(String code) {
       conn=connectToDatabase();
    String value;
    for (int i = 0; i < promocodesCopy.size(); i++) {
        if (promocodesCopy.get(i).getPromocode().equals(code)) {
            value="Promocode " + code + " is available";
            System.out.println(value);
            return;
        }
    }
    value="Promocode " + code + " is not available";
    System.out.println(value);
}   
   
   public void addCode(ticketcode ticketcode)
    {
        ticketcodes.add(ticketcode);
    }
   
   public void showTicketCodes() {
    for (int i = 0; i < ticketcodes.size(); i++) {
        System.out.println(ticketcodes.get(i).getCode());
    }
}
   
   public void addExsistingtickets() {
       conn=connectToDatabase();
        try {           
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ticket");
            
            while (rs.next()) {
                price = rs.getInt("price");
                type = rs.getString("type");
                eventname = rs.getString("eventname");
                eventid = rs.getInt("eventid");
                quantity = rs.getInt("quantity");
                ticket t = new ticket(price, type,eventname,eventid,quantity);
                tickets.add(t);
            }
        } 
        
        catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
    }
   
   public void showTickets() {
       conn=connectToDatabase();
        for (int i = 0; i < tickets.size(); i++) {
        System.out.println(tickets.get(i).getPrice()+ " - " + tickets.get(i).getType()+ " - " + tickets.get(i).getEventname()+ " - " + tickets.get(i).getEventid()+ " - " + tickets.get(i).getQuantity());
    }
    }
}