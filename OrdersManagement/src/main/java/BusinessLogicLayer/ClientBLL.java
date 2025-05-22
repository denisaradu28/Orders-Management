package BusinessLogicLayer;

import DataAccessLayer.ClientDAO;
import Model.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code ClientBLL} class represents the business logic layer for handling operations
 * related to {@link Client} entities. It performs validation and delegates database operations
 * to the {@link ClientDAO}.
 */


public class ClientBLL {

    private  final ClientDAO clientDAO = new ClientDAO();

    /**
     * Validates the {@link Client} object to ensure it meets business constraints.
     *
     * @param client the client to validate
     * @throws IllegalArgumentException if any field is invalid
     */

    private void validateClient(Client client) {
        if(client.getName() == null || client.getName().isBlank())
            throw new IllegalArgumentException("Client name cannot be blank");
        if(client.getAddress() == null || client.getAddress().isBlank())
            throw new IllegalArgumentException("Client address cannot be blank");
        if(client.getEmail() == null || !client.getEmail().contains("@"))
            throw new IllegalArgumentException("Invalid email.");
        if(client.getPhone() == null || client.getPhone().length() < 10)
            throw new IllegalArgumentException("Invalid phone number.");
    }

    /**
     * Inserts a new client into the database after validation.
     *
     * @param client the {@link Client} to insert
     * @return the inserted client with potentially updated fields (e.g., ID)
     * @throws IllegalArgumentException if the client is invalid
     */

    public Client insertClient(Client client) {
        validateClient(client);
        return clientDAO.insert(client);
    }

    /**
     * Updates an existing client in the database after validation.
     *
     * @param client the {@link Client} to update
     * @return the updated client
     * @throws IllegalArgumentException if the client is invalid
     */

    public Client updateClient(Client client) {
        validateClient(client);
        return clientDAO.update(client);
    }

    /**
     * Deletes a client from the database by ID.
     *
     * @param id the ID of the client to delete
     */

    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     * Retrieves all clients from the database.
     *
     * @return a list of all {@link Client} entities
     * @throws SQLException if a database access error occurs
     */

    public List<Client> findAllClients() throws SQLException {
        return clientDAO.findAll();
    }
}
