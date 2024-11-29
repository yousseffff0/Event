package event.agency.management.system.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateAccountGUI extends JFrame implements ActionListener {
    private PreparedStatement pstmt;

    private JTextField nameField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField ageField;
    private JTextField newNameField;
    private JPasswordField newPasswordField;

    private JButton updateButton;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel ageLabel;
    private JLabel newNameLabel;
    private JLabel newPasswordLabel;
    
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
    
    public UpdateAccountGUI() {
        super("Update Account");

        setLayout(new GridLayout(8, 2));

        nameLabel = new JLabel("Name:");
        add(nameLabel);
        nameField = new JTextField(10);
        add(nameField);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordField = new JPasswordField(10);
        add(passwordField);

        phoneLabel = new JLabel("Phone:");
        add(phoneLabel);
        phoneField = new JTextField(10);
        add(phoneField);

        emailLabel = new JLabel("Email:");
        add(emailLabel);
        emailField = new JTextField(10);
        add(emailField);

        ageLabel = new JLabel("Age:");
        add(ageLabel);
        ageField = new JTextField(10);
        add(ageField);

        newNameLabel = new JLabel("New Name:");
        add(newNameLabel);
        newNameField = new JTextField(10);
        add(newNameField);

        newPasswordLabel = new JLabel("New Password:");
        add(newPasswordLabel);
        newPasswordField = new JPasswordField(10);
        add(newPasswordField);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        add(updateButton);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        String phone = phoneField.getText();
        String email = emailField.getText();
        int age = Integer.parseInt(ageField.getText());
        String newName = newNameField.getText();
        String newPassword = new String(newPasswordField.getPassword());
        
        // validate inputs
        
        if (name.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty() || newName.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // connect to database and update account
        
        Connection con = connectToDatabase();
        try {
            String query = "SELECT * FROM person WHERE namee=? AND passwordd=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String oname = rs.getString("namee");
                String ophonenumber = rs.getString("phonenumber");
                String oemail = rs.getString("email");
                int oage = rs.getInt("age");

                String updateQuery = "UPDATE person SET phonenumber=?, email=?, age=?, namee=?, passwordd=? WHERE namee=? AND passwordd=?";
                PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                updateStmt.setString(1, phone);
                updateStmt.setString(2, email);
                updateStmt.setInt(3, age);
                updateStmt.setString(4, newName);
                updateStmt.setString(5, newPassword);
                updateStmt.setString(6, name);
                updateStmt.setString(7, password);

                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Account information updated successfully.");
                    dispose(); // Close main GUI
                    MainGUI gui1 = new MainGUI(); // Create new GUI 1
                    gui1.setVisible(true);
                } else {
                    System.out.println("No account information updated.");
                }
            } else {
                System.out.println("Invalid name or password.");
            }
        } catch (Exception ee) {
            System.err.println("DATABASE ACCESS ERROR: " + ee.toString());
        }
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
         new UpdateAccountGUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
