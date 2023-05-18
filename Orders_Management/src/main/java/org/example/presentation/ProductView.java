package org.example.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class ProductView extends JFrame {
    private JPanel panel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel newNameLabel;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField newNameTextField;
    private JButton addButton;
    private JButton editNameButton;
    private JButton editQuantityButton;
    private JScrollPane productsTableScrollPanel;
    private JTable productsTable;
    private JLabel quantityLabel;
    private JLabel newQuantityLabel;
    private JTextField quantityTextField;
    private JTextField newQuantityTextField;
    private JButton deleteButton;

    public ProductView() {
        setTitle("Products Menu");
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

    public String getQuantity() {
        return quantityTextField.getText();
    }

    public String getNewQuantity() {
        return newQuantityTextField.getText();
    }

    public void setAddButtonListener(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void setEditNameButtonListener(ActionListener actionListener) {
        editNameButton.addActionListener(actionListener);
    }

    public void setEditQuantityButtonListener(ActionListener actionListener) {
        editQuantityButton.addActionListener(actionListener);
    }

    public void setDeleteButtonListener(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    public void showErrorPopup(String message) {
        JOptionPane.showMessageDialog(ProductView.this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoPopup(String message) {
        JOptionPane.showMessageDialog(ProductView.this, message, "Status", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateTable(DefaultTableModel model) {
        productsTable.setModel(model);
    }
}
