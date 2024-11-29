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

public class booking {


    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";
    private int bookingID;
    private int  ticketquantity;
    private String BookingDetails;
    private int  cost;
    public ArrayList<booking> bookinglist =new ArrayList<booking>();
    private Connection con;
    private ticketwithcode twc =new ticketwithcode();
    private promocode promocodes= new promocode();

    ArrayList<booking> booked = new ArrayList<booking>();



    private Connection connectToDatabase(){
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
        return con;
    }
    public booking(int bookingID,int ticketquantity ,String bookingDetails,int cost) {
        this.bookingID = bookingID;
        this.BookingDetails = bookingDetails;
        this.ticketquantity=ticketquantity;
        this.cost=cost;
    }

    public booking(){};
    
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingDetails() {
        return BookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.BookingDetails = bookingDetails;
    }
    public int getTicketquantity() {
        return ticketquantity;
    }

    public void setTicketquantity(int ticketquantity) {
        this.ticketquantity = ticketquantity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    
    
    
    public void addBookingToDatabase(booking b){
        try {PreparedStatement stmt = con.prepareStatement("insert into booking (bookingid,quantity,bookingdetails,cost) VALUES(?,?,?,?) "  );
            stmt.setInt(1, b.getBookingID());
            stmt.setInt(2, b.getTicketquantity());
            stmt.setString(3, b.getBookingDetails());
            stmt.setInt(4, b.getCost());
            stmt.executeUpdate();
            System.out.println("booking added");

        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }

    }


    public void BookTicket(int personid) throws SQLException {
        
        bookingID=IncrementBookingId();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which payment method do you want to use? (1) Cash, (2) Credit, (3) Fawry");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine();
        payment payment = new payment();
        
        switch(paymentMethod) {
            case 1:
                payment.setpaymentStrategy(new CashPaymentStrategy());
                setBookingDetails("Cash");
                break;
            case 2:
                payment.setpaymentStrategy(new CreditPaymentStrategy());
                setBookingDetails("Credit");
                break;
            case 3:
                payment.setpaymentStrategy(new FawryPaymentStrategy());
                setBookingDetails("Fawry");
                break;
            default:
                System.out.println("Invalid payment method selected.");
                return;
        }
        twc.showTicketwithcodes();
        int  quantity= 0;
        cost=0;
        int id;

        for(int i =0;i<=18;i++){
            System.out.println("Enter the number of ticket code that you want to book");
            System.out.println("Or 0 To Exit");
            Scanner x = new Scanner(System.in);
            id=x.nextInt();
            if(id==0){
            break;
            }
            else if(twc.serachticketwithcode(id)){
                cost+=twc.Getticketwithcodeprice(id);

                twc.addeventprofit(twc.Getticketwithcodeeventname(id), twc.Getticketwithcodeprice(id));

                twc.removeticketwithcode(id);
                quantity++;
            }
            else{
                System.out.println("Ticket Code is not available !!");
            }
        }
        setTicketquantity(quantity);

        System.out.println("Enter the promo code");
        Scanner pc = new Scanner(System.in);
        String promocode = pc.nextLine();
        if(promocodes.searchPromocode(promocode)){
            cost-=100;
            System.out.println("We Are Applying Your Promo Code ' "+promocode+" '........");
            System.out.println("Your Promo Code ' "+promocode+" ' is applied successfulyy..");
            System.out.println("Your Cost Is Decreased By 100 Pounds");
        }
        else if(!promocodes.searchPromocode(promocode)){
            System.out.println("Promo Code is not valid !!");
        }

        System.out.println("Your Booking Info:");
        System.out.println("Booking ID:"+bookingID);
        System.out.println("Ticket Quantity :"+quantity);
        System.out.println("Booking Details :"+BookingDetails);
        System.out.println("Your Booking Total Cost : "+cost);
        System.out.println("Please Press (1) To Confirm Your Booking (:");
        System.out.println("Or Press Any Other Number To Cancel ):");
        Scanner x = new Scanner(System.in);
        int c=x.nextInt();
        if(c==1){
            payment.makePayment(personid,cost);
            booking b = new booking (bookingID,quantity,BookingDetails,cost);
            addBookingToDatabase(b);
            }
        }




    public void deleteBooking(int id) {
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM ticket WHERE bookingid = ?");
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("booking with ID " + id + " deleted successfully.");
                
                for (int i = 0; i < booked.size(); i++) {
                    if (booked.get(i).getBookingID() == id) {
                        booked.remove(i);
                        break;
                    }
               }
            } else {
                System.out.println("booking with ID " + id + " not found in the database.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting booking from database: " + e.getMessage());
        }
    }
    
    public int IncrementBookingId(){
        con=connectToDatabase();
        
        try {
            Statement stmt = con.createStatement();
            String query = ("select * from booking order by bookingid DESC LIMIT 1;");
            ResultSet rs = null;
            rs = stmt.executeQuery(query);
            if (rs.first()) {
                int id = rs.getInt("bookingid");
                id++;
                return id;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return 0;
    }
    
    public void addExsistingBooking() {
       con=connectToDatabase();
        try {           
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking");
            
            while (rs.next()) {
                bookingID = rs.getInt("bookingid");
                ticketquantity = rs.getInt("quantity");
                BookingDetails = rs.getString("bookingdetails");
                cost = rs.getInt("cost");
                booking booking1 = new booking(bookingID,ticketquantity,BookingDetails,cost);
                bookinglist.add(booking1);
            }
        } 
        
        catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
    }
    
    public void showbookings() {
        con=connectToDatabase();
        addExsistingBooking();
        System.out.println("Booking ID - Ticket Quantity - Booking Details - Cost");
        for (int i = 0; i < bookinglist.size(); i++) {
        System.out.println(bookinglist.get(i).getBookingID()+ " - " + bookinglist.get(i).getTicketquantity()+ " - " + bookinglist.get(i).getBookingDetails()+ " - " + bookinglist.get(i).getCost());
    }
    }

    public int GetTotalCost() {
    con = connectToDatabase();
    int totalcost = 0;
    try {
        PreparedStatement stmt = con.prepareStatement("SELECT cost FROM booking");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            totalcost += rs.getInt("cost");
        }
    } catch (Exception e) {
        System.err.println("DATABASE ERROR: " + e.toString());
    }
    return totalcost;
}

    public ArrayList<booking> showbookinglist() {
        con=connectToDatabase();
        ArrayList<booking> bookinglist = new ArrayList<booking>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking");
            while (rs.next()) {
                bookinglist.add(new booking(rs.getInt("bookingid"), rs.getInt("quantity"),rs.getString("bookingdetails"),rs.getInt("cost")));
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }

        return  bookinglist;
    }

}