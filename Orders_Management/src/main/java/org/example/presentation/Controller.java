package org.example.presentation;

import org.example.businessLogic.ClientBLL;
import org.example.businessLogic.OrderBLL;
import org.example.businessLogic.ProductBLL;
import org.example.connection.ConnectionFactory;
import org.example.model.Client;
import org.example.model.Order;
import org.example.model.Product;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class Controller {
    private final MainView mainView;
    private final ClientView clientView;
    private final ProductView productView;
    private final OrderView orderView;
    private final ClientBLL clientBLL = new ClientBLL();
    private final ProductBLL productBLL = new ProductBLL();
    private final OrderBLL orderBLL = new OrderBLL();

    public Controller(MainView mainView, ClientView clientView, ProductView productView, OrderView orderView) {
        this.mainView = mainView;
        this.clientView = clientView;
        this.productView = productView;
        this.orderView = orderView;
    }

    /**
     * initializes the controller by setting the listeners and populating the tables (clients and products)
     */
    public void init() {
        setListeners();

        populateClientsTable();
        populateProductsTable();
    }

    /**
     * add all the entries from the database to the clients table from the ClientView object
     */
    private void populateClientsTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<Client> clients;

        for (String column : clientBLL.getFieldNames()) {
            model.addColumn(column);
        }

        try {
            clients = clientBLL.findAllClients();
        } catch (NoSuchElementException e) {
            clientView.updateTable(model);
            return;
        }

        for (Client client : clients) {
            model.addRow(new String[]{
                    String.valueOf(client.getId()),
                    client.getName()
            });
        }

        clientView.updateTable(model);
    }

    /**
     * add all the entries from the database to the products table from the ProductView object
     */
    private void populateProductsTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<Product> products;

        for (String column : productBLL.getFieldNames()) {
            model.addColumn(column);
        }

        try {
            products = productBLL.findAllProducts();
        } catch (NoSuchElementException e) {
            productView.updateTable(model);
            return;
        }

        for (Product product : products) {
            model.addRow(new String[]{
                    String.valueOf(product.getId()),
                    product.getName(),
                    String.valueOf(product.getQuantity())
            });
        }

        productView.updateTable(model);
    }

    /**
     * adds all the listeners to the view classes
     */
    private void setListeners() {
        mainView.setClientsButtonListener(new ClientsListener());
        mainView.setProductsButtonListener(new ProductsListener());
        mainView.setOrdersButtonListener(new OrdersListener());

        clientView.setAddButtonListener(new AddClientListener());
        clientView.setEditButtonListener(new EditClientListener());
        clientView.setDeleteButtonListener(new DeleteClientListener());

        productView.setAddButtonListener(new AddProductListener());
        productView.setEditNameButtonListener(new EditProductNameListener());
        productView.setEditQuantityButtonListener(new EditProductQuantityListener());
        productView.setDeleteButtonListener(new DeleteProductListener());

        orderView.setConfirmButtonListener(new ConfirmListener());
    }

    /**
     * follow up are a list of listener for every button with a different functionality in the application
     */
    private class ClientsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientView.setVisible(true);
        }
    }

    private class ProductsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            productView.setVisible(true);
        }
    }

    private class OrdersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            orderView.setVisible(true);
        }
    }

    private class AddClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Client client;
            String idString = clientView.getId();
            String name = clientView.getName();
            int id;

            if (idString == null || idString.isEmpty() || idString.isBlank() ||
                    name == null || name.isEmpty() || name.isBlank()) {
                clientView.showErrorPopup("The id and name are required to add a client.");
                return;
            }

            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException exception) {
                clientView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            if (id < 1) {
                clientView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            try {
                client = new Client(id, name);
                clientBLL.insertClient(client);
            } catch (SQLException exception) {
                clientView.showErrorPopup("The id is already in use.");
                return;
            }

            clientView.showInfoPopup("Client added successfully.");
            populateClientsTable();
        }
    }

    private class EditClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = clientView.getId();
            String newName = clientView.getNewName();
            int id;

            if (idString == null || idString.isEmpty() || idString.isBlank() ||
                    newName == null || newName.isEmpty() || newName.isBlank()) {
                clientView.showErrorPopup("The id and new name are required to edit a client.");
                return;
            }

            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException exception) {
                clientView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            if (id < 1) {
                clientView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            try {
                clientBLL.findClientById(id);
                clientBLL.updateClient(id, newName);
            } catch (SQLException | NoSuchElementException exception) {
                clientView.showErrorPopup("The id is not registered.");
                return;
            }

            clientView.showInfoPopup("Client updated successfully.");
            populateClientsTable();
        }
    }

    private class DeleteClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = clientView.getId();
            int id;

            if (idString == null || idString.isEmpty() || idString.isBlank()) {
                clientView.showErrorPopup("The id is required to delete a client.");
                return;
            }

            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException exception) {
                clientView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            if (id < 1) {
                clientView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            try {
                clientBLL.findClientById(id);
                clientBLL.deleteClientById(id);
            } catch (SQLException | NoSuchElementException exception) {
                clientView.showErrorPopup("The id is not registered.");
                return;
            }

            clientView.showInfoPopup("Client deleted successfully.");
            populateClientsTable();
        }
    }

    private class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = productView.getId();
            String name = productView.getName();
            String quantityString = productView.getQuantity();
            int id, quantity;

            if (idString == null || idString.isEmpty() || idString.isBlank() ||
                    name == null || name.isEmpty() || name.isBlank() ||
                    quantityString == null || quantityString.isEmpty() || quantityString.isBlank()) {
                productView.showErrorPopup("The id, name and quantity are required to add a product.");
                return;
            }

            try {
                id = Integer.parseInt(idString);
                quantity = Integer.parseInt(quantityString);
            } catch (NumberFormatException exception) {
                productView.showErrorPopup("The id and quantity must be positive integers.");
                return;
            }

            if (id < 1 || quantity < 1) {
                productView.showErrorPopup("The id and quantity must be positive integers.");
                return;
            }

            try {
                productBLL.insertProduct(new Product(id, name, quantity));
            } catch (SQLException exception) {
                productView.showErrorPopup("The id is already in use.");
                return;
            }

            productView.showInfoPopup("Product added successfully.");
            populateProductsTable();
        }
    }

    private class EditProductNameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = productView.getId();
            String newName = productView.getNewName();
            int id;

            if (idString == null || idString.isEmpty() || idString.isBlank() ||
                    newName == null || newName.isEmpty() || newName.isBlank()) {
                productView.showErrorPopup("The id and new name are required to edit a product.");
                return;
            }

            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException exception) {
                productView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            if (id < 1) {
                productView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            try {
                productBLL.findProductById(id);
                productBLL.updateProductName(id, newName);
            } catch (SQLException | NoSuchElementException exception) {
                productView.showErrorPopup("The id is not registered.");
                return;
            }

            productView.showInfoPopup("Product name updated successfully.");
            populateProductsTable();
        }
    }

    private class EditProductQuantityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = productView.getId();
            String newQuantityString = productView.getNewQuantity();
            int id, newQuantity;

            if (idString == null || idString.isEmpty() || idString.isBlank() ||
                    newQuantityString == null || newQuantityString.isEmpty() || newQuantityString.isBlank()) {
                productView.showErrorPopup("The id and new quantity are required to edit a product.");
                return;
            }

            try {
                id = Integer.parseInt(idString);
                newQuantity = Integer.parseInt(newQuantityString);
            } catch (NumberFormatException exception) {
                productView.showErrorPopup("The id and new quantity must be positive integers.");
                return;
            }

            if (id < 1 || newQuantity < 1) {
                productView.showErrorPopup("The id and new quantity must be positive integers.");
                return;
            }

            try {
                productBLL.findProductById(id);
                productBLL.updateProductQuantity(id, newQuantity);
            } catch (SQLException | NoSuchElementException exception) {
                productView.showErrorPopup("The id is not registered.");
                return;
            }

            productView.showInfoPopup("Product quantity updated successfully.");
            populateProductsTable();
        }
    }

    private class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = productView.getId();
            int id;

            if (idString == null || idString.isEmpty() || idString.isBlank()) {
                productView.showErrorPopup("The id is required to delete a product.");
                return;
            }

            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException exception) {
                productView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            if (id < 1) {
                productView.showErrorPopup("The id must be a positive integer.");
                return;
            }

            try {
                productBLL.findProductById(id);
                productBLL.deleteProductById(id);
            } catch (SQLException | NoSuchElementException exception) {
                productView.showErrorPopup("The id is not registered.");
                return;
            }

            productView.showInfoPopup("Product deleted successfully.");
            populateProductsTable();
        }
    }

    private class ConfirmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product product;
            String clientIdString = orderView.getClientId();
            String productIdString = orderView.getProductId();
            String quantityString = orderView.getQuantity();
            int clientId, productId, quantity;

            if (clientIdString == null || clientIdString.isEmpty() || clientIdString.isBlank() ||
                    productIdString == null || productIdString.isEmpty() || productIdString.isBlank() ||
                    quantityString == null || quantityString.isEmpty() || quantityString.isBlank()) {
                orderView.showErrorPopup("All fields are required to place an order.");
                return;
            }

            try {
                clientId = Integer.parseInt(clientIdString);
                productId = Integer.parseInt(productIdString);
                quantity = Integer.parseInt(quantityString);
            } catch (NumberFormatException exception) {
                orderView.showErrorPopup("All fields must be positive integers.");
                return;
            }

            if (clientId < 1 || productId < 1 || quantity < 1) {
                orderView.showErrorPopup("All fields must be positive integers.");
                return;
            }

            try {
                clientBLL.findClientById(clientId);
            } catch (NoSuchElementException exception) {
                orderView.showErrorPopup("Invalid client id.");
                return;
            }

            try {
                product = productBLL.findProductById(productId);
            } catch (NoSuchElementException exception) {
                orderView.showErrorPopup("Invalid product id.");
                return;
            }

            int existingQuantity = product.getQuantity();
            int remainingQuantity = existingQuantity - quantity;
            if (remainingQuantity < 0) {
                orderView.showErrorPopup("Insufficient stock.");
                return;
            }

            try {
                orderBLL.insertOrder(new Order(clientId, productId, quantity));
                if (remainingQuantity != 0) {
                    productBLL.updateProductQuantity(productId, remainingQuantity);
                } else {
                    productBLL.deleteProductById(productId);
                }
            } catch (SQLException ignored) {
            }

            orderView.showInfoPopup("Order placed successfully.");

            populateProductsTable();

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;

            try {
                connection = ConnectionFactory.getConnection();

                statement = connection.prepareStatement("select max(id) as last_id from orders");

                resultSet = statement.executeQuery();
                resultSet.next();
                int orderId = resultSet.getInt("last_id");

                statement = connection.prepareStatement("insert into log (order_id, product_name, product_quantity, order_timestamp) values (?, ?, ?, CURRENT_TIMESTAMP)");
                statement.setInt(1, orderId);
                statement.setString(2, product.getName());
                statement.setInt(3, quantity);

                statement.executeUpdate();
            } catch (SQLException exception) {
                orderView.showErrorPopup("Error generating the bill.");
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }
        }
    }
}
