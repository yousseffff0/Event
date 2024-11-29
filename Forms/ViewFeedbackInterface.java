package event.agency.management.system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
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
public class ViewFeedbackInterface extends JFrame {
     private Connection conn;
     public Connection connectToDatabasee() {
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
    public ArrayList<feedback> getHighRatedFeedback() {
    ArrayList<feedback> feedbackList = new ArrayList<>();
    conn = connectToDatabasee();

    try {
        String sql = "SELECT * FROM feedback WHERE feedbackrate >= 7  && feedbackrate <= 10";
        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String description = rs.getString("descriptionn");
            int feedbackIdResult = rs.getInt("feedbackid");
            int eventid = rs.getInt("eventid");
            String feedbackstatus = rs.getString("feedbackstatus");
            int feedbackrate = rs.getInt("feedbackrate");

            feedback feedback = new feedback(description,feedbackIdResult, eventid, feedbackstatus, feedbackrate);
            feedbackList.add(feedback);
        }

        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.out.println("Error retrieving feedback data: " + e.getMessage());
    }
    
    return feedbackList;
}
    public ArrayList<feedback> getnormalRatedFeedback() {
    ArrayList<feedback> feedbackList = new ArrayList<>();
    conn = connectToDatabasee();

    try {
        String sql = "SELECT * FROM feedback WHERE feedbackrate >= 4  && feedbackrate <= 6";
        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String description = rs.getString("descriptionn");
            int feedbackIdResult = rs.getInt("feedbackid");
            int eventid = rs.getInt("eventid");
            String feedbackstatus = rs.getString("feedbackstatus");
            int feedbackrate = rs.getInt("feedbackrate");

            feedback feedback = new feedback(description,feedbackIdResult, eventid, feedbackstatus, feedbackrate);
            feedbackList.add(feedback);
        }

        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.out.println("Error retrieving feedback data: " + e.getMessage());
    }
    
    return feedbackList;
}
    public ArrayList<feedback> getbadRatedFeedback() {
    ArrayList<feedback> feedbackList = new ArrayList<>();
    conn = connectToDatabasee();

    try {
        String sql = "SELECT * FROM feedback WHERE feedbackrate >= 0  && feedbackrate <= 3";
        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String description = rs.getString("descriptionn");
            int feedbackIdResult = rs.getInt("feedbackid");
            int eventid = rs.getInt("eventid");
            String feedbackstatus = rs.getString("feedbackstatus");
            int feedbackrate = rs.getInt("feedbackrate");

            feedback feedback = new feedback(description,feedbackIdResult, eventid, feedbackstatus, feedbackrate);
            feedbackList.add(feedback);
        }

        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.out.println("Error retrieving feedback data: " + e.getMessage());
    }
    
    return feedbackList;
}
 public ViewFeedbackInterface() {
        ArrayList<feedback> feedbackList = new ArrayList<feedback>();
        
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JButton button1 = new JButton("Open Window 1");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 new ViewPostiveFeedBack(getHighRatedFeedback());
              
            }
        });
        panel.add(button1);

        JButton button2 = new JButton("Open Window 2");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           new Viewnutralfeedback(getnormalRatedFeedback());
            }
        });
        panel.add(button2);

        JButton button3 = new JButton("Open Window 3");
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            new ViewnegativeFeedBack(getbadRatedFeedback());
            }
        });
        panel.add(button3);

        getContentPane().add(panel);
        setVisible(true);
    }

  
    

  
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
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewFeedbackInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewFeedbackInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewFeedbackInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewFeedbackInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                  new ViewFeedbackInterface();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
