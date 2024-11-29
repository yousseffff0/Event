package event.agency.management.system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class customer extends person {

    private int Balance;
    private int id;
    private database db = new database();
    private ReadOnlyEvents  ReadOnlyEvents;
    ArrayList<customer> Customers = new ArrayList<customer>();
    private booking b = new booking();
    private event e = new event();

    private Connection con;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";
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

    public customer(String name, String phoneNo, String email, String age, String password, int id, int personTypeID, int balance) {
        super(name, phoneNo, email, age, password, id, personTypeID);
        Balance = balance;

    }

    public customer(){

    }
    public customer(ReadOnlyEvents ReadOnlyEvents){
        this.ReadOnlyEvents=ReadOnlyEvents;
    }


    public void AddCustomer(customer c){
        Customers.add(c);
    }

    public void createAccount(){
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


        System.out.println("Enter your Balance");
        Scanner Balance = new Scanner(System.in);
        int customerBalance = Balance.nextInt();
        setBalance(customerBalance);
        id = db.IncrementId();

        customer cust = new customer(customerName,customerPhone,customerEmail,customerAge,customerPassword, id,1,customerBalance);

        AddCustomer(cust);
        for(customer c: Customers){
            System.out.println(c.getName());
        }

        db.addCustomer(cust);
    }


    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public void viewBooking(){
        b.showbookings();
    }

    public void addBooking(int personid) throws SQLException {
        b.BookTicket(personid);
    }
    public void Refundmoney(int personid) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which payment method do you want to use? (1) Cash, (2) Credit, (3) Fawry");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        payment payment = new payment();

        switch(paymentMethod) {
            case 1:
                payment.setpaymentStrategy(new CashPaymentStrategy());
                break;
            case 2:
                payment.setpaymentStrategy(new CreditPaymentStrategy());
                break;
            case 3:
                payment.setpaymentStrategy(new FawryPaymentStrategy());
                break;
            default:
                System.out.println("Invalid payment method selected.");
                return;
        }
        System.out.println("enter the ticket id");
        int ticketidd= scanner.nextInt();
        scanner.nextLine();
        payment.refundPayment(personid,ticketidd);
    }
    public int login() throws SQLException {
        con = connectToDatabase();
        System.out.println("Enter your name:");
        Scanner x = new Scanner(System.in);
        String name = x.nextLine();

        System.out.println("Enter your password");
        Scanner y = new Scanner(System.in);
        String password = y.nextLine();

        int personids = validateUserInfo(name, password);

        if (personids != -1) {
            System.out.println("Valid Login");

        } else {
            System.out.println("Invalid Login!!");
        }
        return  personids;
    }


    public int validateUserInfo(String name, String password) {
        try {
            String query = "SELECT personid FROM person WHERE namee=? AND passwordd=? AND persontypeid=1";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("personid");
            }
        } catch (Exception e) {
            System.err.println("DATABASE ACCESS ERROR: " + e.toString());
        }
        return -1; // Return -1 to indicate that the login is invalid
    }
    public void manage(int personids) throws SQLException {
        System.out.println(personids);
        Scanner input = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("What would you like to do?");
            System.out.println("1. Add booking");
            System.out.println("2. Refund money");
            System.out.println("3. Add balance");
            System.out.println("4. Exit");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    addBooking(personids);
                    break;
                case 2:
                    Refundmoney(personids);
                    break;
                case 3:
                    addBalance(personids);
                    break;
                case 4:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }



    public void addBalance(int personids) throws SQLException {
        con=connectToDatabase();
        Scanner scanner = new Scanner(System.in);
        System.out.print( personids);
        System.out.print("Enter amount to add: ");
        double amount = scanner.nextDouble();

        System.out.print("Enter payment method (credit card, cash, or fawry): ");
        String method = scanner.next();

        String info = "";

        switch(method) {
            case "credit card":
                info = getCardInfo(scanner);
                break;
            case "cash":
                info = "Please go to Virgin Store located in City Stars Mall, Cairo, Egypt to make the payment.";
                break;
            case "fawry":
                info = getFawryInfo(scanner);
                break;
            default:
                System.out.println("Invalid payment method!");
                return;
        }

        // retrieve the current balance from the database
        String sql = "SELECT balance FROM customer WHERE personid = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, personids);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        double currentBalance = rs.getDouble("balance");


        // add the amount to the current balance
        double newBalance = currentBalance + amount;

        System.out.println(amount + " EGP has been added to your balance using " + method + ".");
        System.out.println("Payment information: " + info);

        // update the balance in the database
        sql = "UPDATE customer SET balance = " + newBalance + " WHERE personid = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, personids);
        pstmt.executeUpdate();
        pstmt.close();
    }


    private String getCardInfo(Scanner scanner) {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.next();

        System.out.print("Enter cardholder name: ");
        String cardHolderName = scanner.next();

        System.out.print("Enter expiration date (MM/YY): ");
        String expDate = scanner.next();

        System.out.print("Enter CVV: ");
        String cvv = scanner.next();

        String info = "Card number: " + cardNumber + ", Cardholder name: " + cardHolderName + ", Expiration date: " + expDate + ", CVV: " + cvv;

        return info;
    }

    private String getFawryInfo(Scanner scanner) {
        System.out.print("Enter your Fawry account number: ");
        String fawryAccount = scanner.next();

        System.out.print("Enter your Fawry password: ");
        String fawryPassword = scanner.next();

        String info = "Fawry account number: " + fawryAccount + ", Fawry password: " + fawryPassword;

        return info;
    }




    public void viewEvents(){
        List<event> events = ReadOnlyEvents.getAllEvents();
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
            String query = "SELECT * FROM person WHERE namee=? AND passwordd=? AND persontypeid=1";
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

                String updateQuery = "UPDATE person SET phonenumber=?, email=?, age=?, namee=?, passwordd=? WHERE namee=? AND passwordd=? AND persontypeid=1";
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

    
    public void addBalanceGUI(int personids,double amount) throws SQLException {
        con=connectToDatabase();
     
      

      

        
        String sql = "SELECT balance FROM customer WHERE personid = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, personids);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        double currentBalance = rs.getDouble("balance");


        // add the amount to the current balance
        double newBalance = currentBalance + amount;

       

        // update the balance in the database
        sql = "UPDATE customer SET balance = " + newBalance + " WHERE personid = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, personids);
        pstmt.executeUpdate();
        pstmt.close();
 }


}