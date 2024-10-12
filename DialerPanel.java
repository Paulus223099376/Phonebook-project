import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialerPanel extends JPanel {
    private JTextField numberField;
    private JButton callButton;
    private JButton messageButton;

    public DialerPanel() {
        setLayout(new BorderLayout());

        numberField = new JTextField();
        numberField.setFont(new Font("Arial", Font.PLAIN, 24));
        add(numberField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        String[] buttonLabels = {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "*", "0", "#"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    numberField.setText(numberField.getText() + label);
                }
            });
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        callButton = new JButton("Call");
        callButton.setBackground(new Color(46, 204, 113));
        callButton.setForeground(Color.WHITE);
        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DialerPanel.this, "Calling " + numberField.getText());
            }
        });

        messageButton = new JButton("Message");
        messageButton.setBackground(new Color(52, 152, 219));
        messageButton.setForeground(Color.WHITE);
        messageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = JOptionPane.showInputDialog(DialerPanel.this, "Enter your message:");
                if (message != null && !message.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(DialerPanel.this, "Sending message to " + numberField.getText() + ":\n" + message);
                }
            }
        });

        actionPanel.add(callButton);
        actionPanel.add(messageButton);
        add(actionPanel, BorderLayout.SOUTH);
    }

    public String getNumber() {
        return numberField.getText();
    }

    public void setNumber(String number) {
        numberField.setText(number);
    }
}