package event.agency.management.system;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class ticketcode {
    private int code;
    private ArrayList<ticketcode> ticketcodes = new ArrayList();
    
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

    public ticketcode(int code) {
        this.code = code;
    }
    
    public ticketcode() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, username, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    public void addExsistingticketcodes() {
        conn=connectToDatabase();
        try {           
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ticketcode");
            
            while (rs.next()) {
                code = rs.getInt("codee");
                ticketcode tc = new ticketcode(code);
                ticketcodes.add(tc);
            }
        } 
        
        catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
    }
   
   public void showTicketcodes() {
       conn=connectToDatabase();
        for (int i = 0; i < ticketcodes.size(); i++) {
        System.out.println(ticketcodes.get(i).getCode());
    }
    }
}