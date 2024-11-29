package event.agency.management.system.gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginGUI extends JFrame {
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection con;
    private final String userName = "root";
    private final String password = "";
    private final String dbName = "eventagency";

    public LoginGUI() {
        // Create components
        con = connectToDatabase();
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        
        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        try {
            int personid = login();
            int persontypeid = getpersontypeid(nameField.getText(),passwordField.getText());
            if (personid != -1 && persontypeid != -1) {
                JOptionPane.showMessageDialog(null, "Valid Login");
                switch (persontypeid) {
                    case 1:
                        {
                            CustomerGUI gui1 = new CustomerGUI(); // Create new GUI 1
                            setVisible(false);
                            gui1.setVisible(true);
                            System.out.println("Navigate to page for Customer");
                            break;
                        }
                    case 2:
                        {
                            AdminGUI gui2 = new AdminGUI(); // Create new GUI 2
                            setVisible(false);
                            gui2.setVisible(true);
                            System.out.println("Navigate to page for Admin");
                            break;
                        }
                    case 3:
                        {
                            EventOrganizerGUI gui3 = new EventOrganizerGUI(); // Create new GUI 3
                            setVisible(false);
                            gui3.setVisible(true);
                            System.out.println("Navigate to page for Event Organizer");
                            break;
                        }
                    default:
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login!!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database");
        }
    }
});

        // Create panel and add components
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        // Add panel to frame
        add(panel);

        // Set frame properties
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }


    public int login() {
        con = connectToDatabase();
        String name = nameField.getText();
        String password = passwordField.getText();
        int personids = validateUserInfo(name, password);
        return personids;
    }

    public Connection connectToDatabase() {
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

    public int validateUserInfo(String name, String password) {
        con = connectToDatabase();
        try {
            String query = "SELECT * FROM person WHERE namee=? AND passwordd=?";
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
        return -1;
    }
    
    public int getpersontypeid(String name, String password) {
        try {
            String query = "SELECT * FROM person WHERE namee=? AND passwordd=?";
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
        return -1;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new LoginGUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
