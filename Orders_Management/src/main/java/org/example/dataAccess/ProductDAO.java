package org.example.dataAccess;

import org.example.connection.ConnectionFactory;
import org.example.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * extends the CRUD functionality for the Product model
 */
public class ProductDAO extends AbstractDAO<Product> {
    /**
     * updates the name of a product
     *
     * @param id      the id of the product
     * @param newName the new name the product will be given
     * @throws SQLException if there is an error
     */
    public void updateProductName(int id, String newName) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "update products set name = ? where id = ?;";

        try {
            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setInt(2, id);
            statement.executeUpdate();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void updateProductQuantity(int id, int newQuantity) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "update products set quantity = ? where id = ?;";

        try {
            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(query);
            statement.setInt(1, newQuantity);
            statement.setInt(2, id);
            statement.executeUpdate();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}