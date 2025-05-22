package DataAccessLayer;

import Model.Client;

/**
 * Data Access Object (DAO) class for performing operations on the {@link Client} entity.
 * <p>
 * This class inherits generic CRUD operations from {@link AbstractDAO} and can be extended
 * to include additional queries specific to the {@code Client} table if needed.
 *
 * @see Model.Client
 * @see AbstractDAO
 */

public class ClientDAO extends AbstractDAO<Client> {
}
