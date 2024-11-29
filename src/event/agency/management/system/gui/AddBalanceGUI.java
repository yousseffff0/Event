/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package event.agency.management.system.gui;
import event.agency.management.system.customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddBalanceGUI extends JFrame implements ActionListener {
    private JLabel personIdLabel, amountLabel, methodLabel;
    private JTextField personIdField, amountField;
    private JComboBox<String> methodComboBox;
    private JButton addButton;

    public AddBalanceGUI() {
        setTitle("Add Balance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        personIdLabel = new JLabel("Person ID:");
        panel.add(personIdLabel);
        personIdField = new JTextField();
        panel.add(personIdField);

        amountLabel = new JLabel("Amount:");
        panel.add(amountLabel);
        amountField = new JTextField();
        panel.add(amountField);

        methodLabel = new JLabel("Payment Method:");
        panel.add(methodLabel);
        String[] methods = {"Credit Card", "Cash", "Fawry"};
        methodComboBox = new JComboBox<>(methods);
        panel.add(methodComboBox);

        addButton = new JButton("Add Balance");
        addButton.addActionListener(this);
        panel.add(addButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            int personId = Integer.parseInt(personIdField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String method = (String) methodComboBox.getSelectedItem();
            String info = "";

            switch(method) {
                case "Credit Card":
                    info = getCardInfo();
                    break;
                case "Cash":
                    info = "Please go to Virgin Store located in City Stars Mall, Cairo, Egypt to make the payment.";
                    break;
                case "Fawry":
                  info=  getfawryInfo();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid payment method!");
                    return;
            }

            try {
                customer customer = new  customer();
                customer.addBalanceGUI(personId,amount);
                JOptionPane.showMessageDialog(null, amount + " EGP has been added to your balance using " + method + "." + "\nPayment information: " + info);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error adding balance: " + ex.getMessage());
            }
        }
    }

    private String getCardInfo() {
        JTextField cardNumberField = new JTextField();
        JTextField cardHolderNameField = new JTextField();
        JTextField expDateField = new JTextField();
        JTextField cvvField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Card Number:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("Cardholder Name:"));
        panel.add(cardHolderNameField);
        panel.add(new JLabel("Expiration Date (MM/YY):"));
        panel.add(expDateField);
        panel.add(new JLabel("CVV:"));
        panel.add(cvvField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Card Information", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String cardNumber = cardNumberField.getText();
            String cardHolderName = cardHolderNameField.getText();
            String expDate = expDateField.getText();
            String cvv = cvvField.getText();

        }
        return null;
    }
     private String getfawryInfo() {
        JTextField cardNumberField = new JTextField();
        JTextField cardHolderNameField = new JTextField();
        JTextField expDateField = new JTextField();
        JTextField cvvField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("phone number:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("account number"));
        panel.add(cardHolderNameField);
        panel.add(new JLabel("CVV:"));
        panel.add(cvvField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Card Information", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String cardNumber = cardNumberField.getText();
            String cardHolderName = cardHolderNameField.getText();
            String cvv = cvvField.getText();

        }
        return null;
    }
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
        new AddBalanceGUI().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
