package org.example.dataAccess;

import org.example.connection.ConnectionFactory;
import org.example.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * extends the CRUD functionality for the Client model
 */
public class ClientDAO extends AbstractDAO<Client> {
    /**
     * updates the name of a client
     *
     * @param id      the id of the client
     * @param newName the new name the client will be given
     * @throws SQLException if there is an error
     */
    public void updateClientName(int id, String newName) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "update clients set name = ? where id = ?;";

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
}