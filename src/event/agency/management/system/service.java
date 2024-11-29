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

public class service {
    private String serviceName;
    private String serviceDetails;
    private int servicePrice;
    private int serviceId;
    database db = new database();
    private Connection conn;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";
    private ArrayList<service> serviceList=new ArrayList<service>();

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public service(String serviceName, String serviceDetails, int servicePrice, int serviceId) {
        this.serviceName = serviceName;
        this.serviceDetails = serviceDetails;
        this.servicePrice = servicePrice;
        this.serviceId = serviceId;
    }
    
    public service(String serviceName, String serviceDetails, int servicePrice) {
        this.serviceName = serviceName;
        this.serviceDetails = serviceDetails;
        this.servicePrice = servicePrice;
    }

    
    public service(){
          try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }
    
    public void addService(){
        System.out.println("Enter the service name that you want to add");
        Scanner name = new Scanner(System.in);
        String eventName = name.nextLine();
        setServiceName(eventName);
        System.out.println("Enter the service Details that you want to add");
        Scanner Details = new Scanner(System.in);
        String eventDetails = Details.nextLine();
        setServiceDetails(eventDetails);
        System.out.println("Enter the service Price that you want to add");
        Scanner Price = new Scanner(System.in);
        int eventPrice = Price.nextInt();
        setServicePrice(eventPrice);
        addServiceTooDatabase(new service(eventName,eventDetails,eventPrice));
    }
    
    public void addServiceTooDatabase(service myservice ){
        serviceId = db.IncrementServiceId();

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO servicee (servicename, serviceprice,servicedetails ,serviceid) VALUES (?, ?, ?, ?)");
            stmt.setString(1, myservice.getServiceName());
            stmt.setInt(2, myservice.getServicePrice());
            stmt.setString(3, myservice.getServiceDetails());
            stmt.setInt(4, serviceId);
            stmt.executeUpdate();
            serviceList.add(myservice);
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }
    }
    
    
    
    
    
     public void deleteservice(int id) {
    try {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM servicee WHERE serviceId = ?");
        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Service with ID " + id + " deleted successfully.");
            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i).getServiceId() == id) {
                    serviceList.remove(i);
                    break;
                }
            }
        } else {
            System.out.println("Service with ID " + id + " not found in the database.");
        }
    } catch (Exception e) {
        System.err.println("Error deleting event from database: " + e.getMessage());
    }
 }
     public void updateService( String serviceDetails,String serviceName,int servicePrice,int serviceId) {
    try {
        PreparedStatement stmt = conn.prepareStatement("UPDATE servicee SET servicename=?, servicedetails=?, serviceprice=? WHERE serviceid=?");
        stmt.setString(1, serviceName);
        stmt.setString(2, serviceDetails);
        stmt.setInt(3, servicePrice);
        stmt.setInt(4, serviceId);
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Service with ID " + serviceId + " updated successfully.");
            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i).getServiceId() == serviceId) {
                    serviceList.get(i).setServiceName(serviceName);
                    serviceList.get(i).setServiceDetails(serviceDetails);
                    serviceList.get(i).setServicePrice(servicePrice);
                    break;
                }
            }
        } else {
            System.out.println("Service with ID " + serviceId + " not found in the database.");
        }
    } catch (Exception e) {
        System.err.println("Error updating service in database: " + e.getMessage());
    }
}

}
