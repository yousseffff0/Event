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

public class report {
    private int reportId;
    private String report;
    private String month;
    private int eventscost=0;
    private int eventsprofit=0;
    private int eventsrevenue=0;
    public ArrayList<report> reportlist =new ArrayList<report>();
    
    private Connection conn;
    private String username = "root";
    private String password = "";
    private final String dbName = "eventagency";
    database db = new database();
    
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

    public report(int reportId, String report, String month, int eventscost, int eventsprofit, int eventsrevenue) {
        this.reportId = reportId;
        this.report = report;
        this.month = month;
        this.eventscost = eventscost;
        this.eventsprofit = eventsprofit;
        this.eventsrevenue = eventsrevenue;
    }
    
    public report(){}

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getEventscost() {
        return eventscost;
    }

    public void setEventscost(int eventscost) {
        this.eventscost = eventscost;
    }

    public int getEventsprofit() {
        return eventsprofit;
    }

    public void setEventsprofit(int eventsprofit) {
        this.eventsprofit = eventsprofit;
    }

    public int getEventsrevenue() {
        return eventsrevenue;
    }

    public void setEventsrevenue(int eventsrevenue) {
        this.eventsrevenue = eventsrevenue;
    }

    public ArrayList<report> getReportlist() {
        return reportlist;
    }

    public void setReportlist(ArrayList<report> reportlist) {
        this.reportlist = reportlist;
    }

    public int geteventsprofit() {
       conn = connectToDatabase();
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT profit FROM eventprofit");
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            eventsprofit += rs.getInt("profit");
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    }   
    return eventsprofit;
}
    
    public int geteventscost() {
       conn = connectToDatabase();
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT cost FROM eventt");
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            eventscost += rs.getInt("cost");
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    }   
    return eventscost;
}
    
    public int geteventsrevenue() {
        return eventsrevenue=eventsprofit-eventscost;
}
    
    public void addExsistingreports() {
       conn=connectToDatabase();
        try {           
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM report");
            
            while (rs.next()) {
                int reportid = rs.getInt("reportid");
                String reportinfo = rs.getString("reportinfo");
                String monthh = rs.getString("monthh");
                int eventscost = rs.getInt("eventscost");
                int eventsprofit = rs.getInt("eventsprofit");
                int eventsrevenue = rs.getInt("eventsrevenue");
                report r = new report(reportid,reportinfo,monthh,eventscost,eventsprofit,eventsrevenue);
                reportlist.add(r);
            }
        } 
        
        catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
    }
    
    public void addreport(report r) {
       conn=connectToDatabase();
       reportId=IncrementreportId();
       
       System.out.println("Enter the Report Info");
       Scanner x = new Scanner(System.in);
       String reportinfo = x.nextLine();
       setReport(reportinfo);
       
       System.out.println("Enter the Report month");
       Scanner y = new Scanner(System.in);
       String month = y.nextLine();
       setMonth(month);
       
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO report (reportid,reportinfo,monthh,eventscost,eventsprofit,eventsrevenue) VALUES (?,?,?,?,?,?)");
            stmt.setInt(1,reportId);
            stmt.setString(2, r.getReport());
            stmt.setString(3, r.getMonth());
            stmt.setInt(4, r.geteventscost());
            stmt.setInt(5, r.geteventsprofit());
            stmt.setInt(6, r.geteventsrevenue());
            stmt.executeUpdate();
            reportlist.add(r);
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }
    }
    
    public void showreports() {
        conn=connectToDatabase();
        addExsistingreports();
        for (int i = 0; i < reportlist.size(); i++) {
        System.out.println(reportlist.get(i).getReportId()+ " - " + reportlist.get(i).getReport()+ " - " + reportlist.get(i).getMonth()+ " - " + reportlist.get(i).getEventscost()+ " - " + reportlist.get(i).getEventsprofit()+ " - " + reportlist.get(i).getEventsrevenue());
    }
    }
    
    public int IncrementreportId(){
        conn=connectToDatabase();
        
        try {
            Statement stmt = conn.createStatement();
            String query = ("select * from report order by reportid DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("reportid");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
    }

    /////////////////////////////////////////////////////////

    public void makeEventReport(){
        System.out.println("Enter the event id that you want to view its profit");
        Scanner y = new Scanner(System.in);
        int id = y.nextInt();
        System.out.println( db.getEventName(id));
        System.out.println("Cost "+ db.getEventCost(id));
        System.out.println(db.getServiceName(id));
        System.out.println("Cost "+ db.getServiceAssignedEventPrice(id));
        System.out.println("Sponsor Name "+ db.getSponsorName(id));
        System.out.println("Cost "+ db.getSponsorAssignedEventPrice(id));

    }

    /* //////////////////////////////////////////////////////// */
}