package BusinessLogicLayer;

import DataAccessLayer.OrdersDAO;
import Model.Orders;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code OrderBLL} class represents the business logic layer for handling operations related to {@link Orders}.
 * <p>
 * It is responsible for validating order data and delegating persistence tasks to the {@link OrdersDAO}.
 */
public class OrderBLL {

    private final OrdersDAO ordersDAO = new OrdersDAO();

    /**
     * Inserts a new {@link Orders} object into the database after validating the client ID.
     *
     * @param order the {@code Orders} object to be inserted
     * @return the inserted {@code Orders} object with a generated ID (if applicable)
     * @throws IllegalArgumentException if the client ID is less than or equal to 0
     */
    public Orders insertOrder(Orders order) {
        if (order.getClientId() <= 0) {
            throw new IllegalArgumentException("Client ID must be greater than 0.");
        }
        return ordersDAO.insert(order);
    }

    /**
     * Deletes an {@link Orders} record from the database using its unique ID.
     *
     * @param id the ID of the order to be deleted
     */
    public void deleteOrder(int id) {
        ordersDAO.delete(id);
    }

    /**
     * Retrieves all {@link Orders} records from the database.
     *
     * @return a list of all orders
     * @throws SQLException if a database access error occurs
     */
    public List<Orders> findAllOrders() throws SQLException {
        return ordersDAO.findAll();
    }
}
