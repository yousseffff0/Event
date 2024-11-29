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

public class promocode {
    public String promocode;
    public String status;
    public ArrayList<promocode> promocodes=new ArrayList<promocode>();
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

    public promocode(String promocode, String status) {
        this.promocode = promocode;
        this.status = status;
    }

    public promocode() {
        promocodes = new ArrayList<>();
       try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, username, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
   public void addPromocode(promocode promo) {
       conn=connectToDatabase();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO promocode (promocode, statuss) VALUES (?, ?)");
            stmt.setString(1, promo.getPromocode());
            stmt.setString(2, promo.getStatus());
            stmt.executeUpdate();
            promocodes.add(promo);
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }
    }
   
   public void addExsistingPromocode() {
       conn=connectToDatabase();
        try {           
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM promocode");
            
            while (rs.next()) {
                String promocodeString = rs.getString("promocode");
                String statuss = rs.getString("statuss");
                promocode promo = new promocode(promocodeString, statuss);
                promocodes.add(promo);
            }
        } 
        
        catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
    }

    public void removePromocode(String promoCode) {
        conn=connectToDatabase();
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM promocode WHERE promocode = ?");
            stmt.setString(1, promoCode);
            stmt.executeUpdate();
            for (int i = 0; i < promocodes.size(); i++) {
                if (promocodes.get(i).getPromocode().equals(promoCode)) {
                    promocodes.remove(i);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.toString());
        }
    }
    
    public void showPromocodes() {
        conn=connectToDatabase();
        for (int i = 0; i < promocodes.size(); i++) {
        System.out.println(promocodes.get(i).getPromocode() + " - " + promocodes.get(i).getStatus());
    }
    }
    
    public boolean searchPromocode(String promoCode) {
    conn = connectToDatabase();
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM promocode WHERE promocode = ?");
        stmt.setString(1, promoCode);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            return true;
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    }
        return false;
    }
    
}
