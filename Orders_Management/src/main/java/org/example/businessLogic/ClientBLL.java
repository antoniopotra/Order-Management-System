package org.example.businessLogic;

import org.example.dataAccess.ClientDAO;
import org.example.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * this class acts as an intermediate layer between the presentation layer and the client dao layer
 */
public class ClientBLL {
    private static final Client theClient = new Client();
    private final ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) throws NoSuchElementException {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException();
        }
        return client;
    }

    public List<Client> findAllClients() throws NoSuchElementException {
        List<Client> clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException();
        }
        return clients;
    }

    public void insertClient(Client client) throws SQLException {
        clientDAO.insert(client);
    }

    public void updateClient(int id, String newName) throws SQLException {
        clientDAO.updateClientName(id, newName);
    }

    public void deleteClientById(int id) throws SQLException {
        clientDAO.deleteById(id);
    }

    public String[] getFieldNames() {
        return clientDAO.getFieldNames(theClient);
    }
}
