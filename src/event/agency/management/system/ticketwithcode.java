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

public class ticketwithcode {
    private ticket t;
    private ticketcode tc;
    public int price;
    public String type;
    public String eventname;
    public int eventid;
    public int quantity;
    private int code;
    private ArrayList<ticketwithcode> ticketwithcodes = new ArrayList<>();
    
    private Connection conn;
    private String username = "root";
    private String password = "";
    private final String dbName = "eventagency";
    
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
    
    public ticketwithcode(int price, String type, String eventname, int eventid, int quantity, int code) {
        this.price = price;
        this.type = type;
        this.eventname = eventname;
        this.eventid = eventid;
        this.quantity = quantity;
        this.code = code;
    }
    
    public ticketwithcode() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, username, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
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

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    public void addExsistingticketwithcodes() {
        conn=connectToDatabase();
        try {           
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ticket INNER JOIN ticketcode ON primarykey = foreignkey");
            
            while (rs.next()) {
                price = rs.getInt("price");
                type = rs.getString("type");
                eventname = rs.getString("eventname");
                eventid = rs.getInt("eventid");
                quantity = rs.getInt("quantity");
                code = rs.getInt("codee");
                ticketwithcode twc = new ticketwithcode(price,type,eventname,eventid,quantity,code);
                ticketwithcodes.add(twc);
            }
        } 
        
        catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
    }
   
   public void showTicketwithcodes() {
       conn=connectToDatabase();
       addExsistingticketwithcodes();
       System.out.println("Ticket price - Ticket category - Event name - Event id - Quantity - Ticket code");
        for (int i = 0; i < ticketwithcodes.size(); i++) {
        System.out.println(ticketwithcodes.get(i).getPrice()+ " - " + ticketwithcodes.get(i).getType()+ " - " + ticketwithcodes.get(i).getEventname()+ " - " + ticketwithcodes.get(i).getEventid()+ " - " + ticketwithcodes.get(i).getQuantity()+ " - " + ticketwithcodes.get(i).getCode());
    }
    }
   
   public void removeticketwithcode(int tcode) {
       conn=connectToDatabase();
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM ticketcode WHERE codee = ?");
            stmt.setInt(1, tcode);
            stmt.executeUpdate();
            for (int i = 0; i < ticketwithcodes.size(); i++) {
                if (ticketwithcodes.get(i).getCode() == tcode) {
                    ticketwithcodes.remove(i);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.toString());
        }
    }
   
   public int Getticketwithcodeprice(int tcode) {
    conn = connectToDatabase();
    int price = 0;
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT price FROM ticket INNER JOIN ticketcode ON primarykey = foreignkey WHERE codee = ?");
        stmt.setInt(1, tcode);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            price = rs.getInt("price");
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    } 
        return price;
    }
   
   public boolean serachticketwithcode(int tcode) {
    conn = connectToDatabase();
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ticket INNER JOIN ticketcode ON primarykey = foreignkey WHERE codee = ?");
        stmt.setInt(1,tcode);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            return true;
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    }
        return false;
    }
   
   public String Getticketwithcodeeventname(int tcode) {
    conn = connectToDatabase();
    String eventname = "";
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT eventname FROM ticket INNER JOIN ticketcode ON primarykey = foreignkey WHERE codee = ?");
        stmt.setInt(1, tcode);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            eventname = rs.getString("eventname");
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    } 
        return eventname;
    }
   
   public void addeventprofit(String eventname,int tprice) {
       conn = connectToDatabase();
       int profit=0;
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT profit FROM eventprofit WHERE eventname = ?");
        stmt.setString(1, eventname);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            profit = rs.getInt("profit");
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    }
    
    
    profit+=tprice;
    
    
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE eventprofit SET profit = ? WHERE eventname = ?");
            stmt.setInt(1, profit);
            stmt.setString(2, eventname);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("DATABASE UPDATE ERROR123: " + e.toString());
        }
    }
   
}