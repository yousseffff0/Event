package event.agency.management.system.gui;
import event.agency.management.system.gui.refundMoney.refunedMoney;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NavigationGUI extends JFrame {
    private JPanel mainPanel;
    private JButton page1Button;
    private JButton page2Button;
    private JButton page3Button;
    
    public NavigationGUI() {
        // Set up the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        
        // Set up the buttons
        page1Button = new JButton("Book Ticket");
        page2Button = new JButton("Add to Balance");
        page3Button = new JButton("Refund Money");
        
        // Add the buttons to the main panel
        mainPanel.add(page1Button);
        mainPanel.add(page2Button);
        mainPanel.add(page3Button);
        
        // Add action listeners to the buttons
        page1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close main GUI
                BookTicketGUI gui1 = new BookTicketGUI(); // Create new GUI 1
                gui1.setVisible(true);
            }
        });
        
        page2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close main GUI
                AddBalanceGUI gui1 = new AddBalanceGUI(); // Create new GUI 1
                gui1.setVisible(true);
            }
        });
        
        page3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close main GUI
                refunedMoney gui1 = new refunedMoney(); // Create new GUI 1
                gui1.setVisible(true);
            }
        });
        
        // Set up the frame
        setTitle("Navigation GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setContentPane(mainPanel);
        setVisible(true);
    }
    
    private void navigateToPage1() {
        // Replace the main panel with the Page 1 panel
        mainPanel.removeAll();
        mainPanel.add(new JLabel("Page 1"));
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void navigateToPage2() {
        // Replace the main panel with the Page 2 panel
        mainPanel.removeAll();
        mainPanel.add(new JLabel("Page 2"));
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void navigateToPage3() {
        // Replace the main panel with the Page 3 panel
        mainPanel.removeAll();
        mainPanel.add(new JLabel("Page 3"));
        mainPanel.revalidate();
        mainPanel.repaint();
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
    public static void main(String[] args) {
        new NavigationGUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
