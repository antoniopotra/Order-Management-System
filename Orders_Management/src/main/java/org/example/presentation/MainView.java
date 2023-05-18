package org.example.presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel panel;
    private JButton clientsButton;
    private JButton productsButton;
    private JButton ordersButton;

    public MainView() {
        setTitle("Main Menu");
        setContentPane(panel);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void setClientsButtonListener(ActionListener actionListener) {
        clientsButton.addActionListener(actionListener);
    }

    public void setProductsButtonListener(ActionListener actionListener) {
        productsButton.addActionListener(actionListener);
    }

    public void setOrdersButtonListener(ActionListener actionListener) {
        ordersButton.addActionListener(actionListener);
    }
}
