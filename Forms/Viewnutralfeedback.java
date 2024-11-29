/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package event.agency.management.system;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author youssef mahmoud
 */
public class Viewnutralfeedback extends javax.swing.JFrame {

      private JTable nutralFeedBackTable;
public Viewnutralfeedback (ArrayList<feedback> feedback) {
        setTitle("Event Organizers");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"description", "Id","Eventid","Statues","Rate"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (feedback e : feedback) {
            String description = e.getDescription();
            int Id = e.getFeedbackId();
            int Eventid = e.getEventid();
            String Statues = e.getFeedbackStatues();
             int Rate = e.getFeedbackRate();
         
            Object[] rowData = {description,Id,Eventid,Statues,Rate};
            tableModel.addRow(rowData);
        }

       nutralFeedBackTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane( nutralFeedBackTable);
        getContentPane().add(scrollPane);

        setVisible(true);
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
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
