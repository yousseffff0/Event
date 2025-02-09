package event.agency.management.system.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EventOrganizerGUI extends JFrame {

       private JButton button1, button2, button3, button4,button5;
       private JPanel mainPanel, panel1, panel2, panel3, panel4,panel5;

    public EventOrganizerGUI() {
        // Initialize components
        button1 = new JButton("Update Account");
        button2 = new JButton("Add event");
        button3 = new JButton("Edit event");
        button4 = new JButton("Add sponsor");
        button5 = new JButton("Add service");

        // Add action listeners to buttons
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close main GUI
                UpdateAccountGUI gui1 = new UpdateAccountGUI();
                gui1.setVisible(true);
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close main GUI
                AddEventDetailsGUI gui2 = new AddEventDetailsGUI();
                gui2.setVisible(true);
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close main GUI
                EditEventGUI gui3 = new EditEventGUI();
                gui3.setVisible(true);
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(panel4);
            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(panel5);
            }
        });

        // Initialize panels
        panel1 = new JPanel();
        panel1.add(new JLabel("Update Account"));

        panel2 = new JPanel();
        panel2.add(new JLabel("Add event"));

        panel3 = new JPanel();
        panel3.add(new JLabel("Edit event"));

        panel4 = new JPanel();
        panel4.add(new JLabel("Add sponsor"));
        
        panel5 = new JPanel();
        panel5.add(new JLabel("Add service"));

        // Initialize main panel and add components
        mainPanel = new JPanel(new GridLayout(5, 1));
        mainPanel.add(button1);
        mainPanel.add(button2);
        mainPanel.add(button3);
        mainPanel.add(button4);
        mainPanel.add(button5);

        // Add main panel to frame
        add(mainPanel);

        // Set frame properties
        setTitle("Event Organizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // Method to show a panel and hide others
    public void showPanel(JPanel panel) {
        mainPanel.setVisible(false);
        panel.setVisible(true);
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
        new EventOrganizerGUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
