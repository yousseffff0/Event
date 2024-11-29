package event.agency.management.system.gui;
import event.agency.management.system.ticketwithcode;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class TicketWithCodesGUI extends JFrame {
    
    private ArrayList<ticketwithcode> ticketWithCodes;
    private Connection conn;
    private DefaultTableModel tableModel;
    private JTable table;
    private ticketwithcode twc = new ticketwithcode();
    private JTextField codeField;
    
    public TicketWithCodesGUI() {
        super("Ticket with Codes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ticketWithCodes = new ArrayList<>();
        conn = connectToDatabase();
        addExistingTicketWithCodes();
        
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        
        JPanel buttonPanel = new JPanel();
        JLabel codeLabel = new JLabel("Enter Ticket Code:");
        codeField = new JTextField(10); // assign the class variable here
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(new BookButtonListener());
        buttonPanel.add(codeLabel);
        buttonPanel.add(codeField);
        buttonPanel.add(bookButton);
        JButton showButton = new JButton("Show Tickets");
        showButton.addActionListener(new ShowButtonListener());
        buttonPanel.add(showButton);
        
        tableModel = new DefaultTableModel(new String[]{"Ticket Price", "Ticket Category", "Event Name", "Event ID", "Quantity", "Ticket Code"}, 0);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(75);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(750, 500));
        
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(tableScrollPane, BorderLayout.CENTER);

        
        setVisible(true);
    }
    
    private Connection connectToDatabase() {
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
    
    private void addExistingTicketWithCodes() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ticket INNER JOIN ticketcode ON primarykey = foreignkey");
            while (rs.next()) {
                ticketwithcode ticket = new ticketwithcode(rs.getInt("price"), rs.getString("type"), rs.getString("eventname"), rs.getInt("eventid"), rs.getInt("quantity"), rs.getInt("codee"));
                ticketWithCodes.add(ticket);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private class ShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            tableModel.setRowCount(0); // Clear previous table data
            for (int i = 0; i < ticketWithCodes.size(); i++) {
                ticketwithcode ticket = ticketWithCodes.get(i);
                Object[] rowData = {ticket.getPrice(), ticket.getType(), ticket.getEventname(), ticket.getEventid(), ticket.getQuantity(), ticket.getCode()};
                tableModel.addRow(rowData);
            }
        }
    }
    
    private class BookButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        String code = codeField.getText();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(TicketWithCodesGUI.this, "Please enter a ticket code.");
            return;
        }
        bookTicketWithCode(code);
        dispose(); // Close main GUI
        CreateAccountGUI gui1 = new CreateAccountGUI();
        gui1.setVisible(true);
    }
}

private void bookTicketWithCode(String code) {
    conn = connectToDatabase();
    try {
        PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM ticketcode WHERE codee = ?");
        deleteStmt.setString(1, code);
        deleteStmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Ticket with code " + code + " has been booked.");
    } catch (SQLException e) {
        e.printStackTrace();
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
        new TicketWithCodesGUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
