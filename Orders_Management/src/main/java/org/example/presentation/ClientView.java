package org.example.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JPanel panel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel newNameLabel;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField newNameTextField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JScrollPane clientsTableScrollPanel;
    private JTable clientsTable;

    public ClientView() {
        setTitle("Clients Menu");
        setContentPane(panel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public String getId() {
        return idTextField.getText();
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getNewName() {
        return newNameTextField.getText();
    }

    public void setAddButtonListener(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void setEditButtonListener(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }

    public void setDeleteButtonListener(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    public void showErrorPopup(String message) {
        JOptionPane.showMessageDialog(ClientView.this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoPopup(String message) {
        JOptionPane.showMessageDialog(ClientView.this, message, "Status", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateTable(DefaultTableModel model) {
        clientsTable.setModel(model);
    }
}
