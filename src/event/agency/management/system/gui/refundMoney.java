package event.agency.management.system.gui;

import event.agency.management.system.CreditPaymentStrategy;
import event.agency.management.system.payment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class refundMoney {

    public static class refunedMoney extends JFrame implements ActionListener {
        private JLabel personIdLabel, bookingIdLabel;
        private JTextField personIdField, bookingIdField;
        private JButton refundButton;

        public refunedMoney() {
            setTitle("Refund Payment");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(300, 200);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(3, 2));

            personIdLabel = new JLabel("Person ID:");
            panel.add(personIdLabel);
            personIdField = new JTextField();
            panel.add(personIdField);

            bookingIdLabel = new JLabel("Booking ID:");
            panel.add(bookingIdLabel);
            bookingIdField = new JTextField();
            panel.add(bookingIdField);

            refundButton = new JButton("Refund");
            refundButton.addActionListener(this);
            panel.add(refundButton);

            getContentPane().add(panel);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == refundButton) {
                int personId = Integer.parseInt(personIdField.getText());
                int bookingId = Integer.parseInt(bookingIdField.getText());
                try {
                    payment payment = new payment();
                    payment.setpaymentStrategy(new CreditPaymentStrategy());
                    payment.refundPayment(personId, bookingId);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error refunding payment: " + ex.getMessage());
                }
            }
        }
    }
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
            java.util.logging.Logger.getLogger(refunedMoney.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(refunedMoney.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(refunedMoney.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(refunedMoney.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                refunedMoney gui = new refunedMoney();
            }
        });
    }
}
