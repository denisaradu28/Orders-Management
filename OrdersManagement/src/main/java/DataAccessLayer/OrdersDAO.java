package DataAccessLayer;

import Model.Orders;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

/**
 * Data Access Object (DAO) for the {@link Orders} entity.
 * <p>
 * This class handles database operations related to orders, extending
 * generic functionality from {@link AbstractDAO}. It includes a custom
 * implementation of the {@code insert} method to handle order-specific
 * insertion logic and to retrieve generated keys (e.g., the order ID).
 *
 * @see AbstractDAO
 * @see Orders
 */
public class OrdersDAO extends AbstractDAO<Orders> {
    protected static final Logger LOGGER = Logger.getLogger(OrdersDAO.class.getName());

    /**
     * Inserts a new order into the database and updates the order object
     * with the generated ID.
     *
     * @param order the {@link Orders} object to be inserted
     * @return the same {@link Orders} object with the ID field set if the insertion was successful;
     *         otherwise, returns the original object with ID unchanged
     */
    @Override
    public Orders insert(Orders order) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        String query = "INSERT INTO Orders (clientId, orderData, totalAmount) VALUES (?, ?, ?)";

        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, order.getClientId());
            stmt.setDate(2, new java.sql.Date(order.getOrderData().getTime()));
            stmt.setDouble(3, order.getTotalAmount());

            stmt.executeUpdate();

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                order.setId(id); // set generated ID
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrdersDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(generatedKeys);
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(connection);
        }

        return order;
    }
}
