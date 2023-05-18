package org.example.dataAccess;

import org.example.connection.ConnectionFactory;
import org.example.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * extends the CRUD functionality for the Order model
 */
public class OrderDAO extends AbstractDAO<Order> {
    /**
     * insert an order into the database
     *
     * @param order the order
     * @throws SQLException if there is an error
     */
    @Override
    public void insert(Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement("insert into orders (client_id, product_id, quantity) values (?, ?, ?)");
            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());

            statement.executeUpdate();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
