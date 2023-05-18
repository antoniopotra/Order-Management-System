package org.example.presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

public class OrderView extends JFrame {
    private JPanel panel;
    private JLabel clientIdLabel;
    private JLabel productIdLabel;
    private JLabel quantityLabel;
    private JTextField clientIdTextField;
    private JTextField productIdTextField;
    private JTextField quantityTextField;
    private JButton confirmButton;

    public OrderView() {
        setTitle("Orders Menu");
        setContentPane(panel);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public String getClientId() {
        return clientIdTextField.getText();
    }

    public String getProductId() {
        return productIdTextField.getText();
    }

    public String getQuantity() {
        return quantityTextField.getText();
    }

    public void setConfirmButtonListener(ActionListener actionListener) {
        confirmButton.addActionListener(actionListener);
    }

    public void showErrorPopup(String message) {
        JOptionPane.showMessageDialog(OrderView.this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoPopup(String message) {
        JOptionPane.showMessageDialog(OrderView.this, message, "Status", JOptionPane.INFORMATION_MESSAGE);
    }
}
