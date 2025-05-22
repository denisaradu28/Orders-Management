package BusinessLogicLayer;

import DataAccessLayer.OrderItemDAO;
import Model.OrderItem;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code OrderItemBLL} class represents the business logic layer for managing {@link OrderItem} entities.
 * <p>
 * This class handles validation logic and delegates database operations to the {@link OrderItemDAO}.
 * It enforces rules such as requiring a positive quantity and a non-negative price.
 */
public class OrderItemBLL {

    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    /**
     * Validates the specified {@link OrderItem} to ensure it meets business constraints.
     *
     * @param orderItem the {@code OrderItem} to validate
     * @throws IllegalArgumentException if the quantity is not positive or the price is negative
     */
    private void validateItem(OrderItem orderItem) {
        if (orderItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (orderItem.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    /**
     * Inserts a new {@link OrderItem} into the database after validation.
     *
     * @param orderItem the {@code OrderItem} to insert
     * @return the inserted {@code OrderItem} with any generated fields (e.g., ID)
     * @throws IllegalArgumentException if the item is invalid
     */
    public OrderItem insertOrderItem(OrderItem orderItem) {
        validateItem(orderItem);
        return orderItemDAO.insert(orderItem);
    }

    /**
     * Deletes an {@link OrderItem} from the database using its unique identifier.
     *
     * @param id the ID of the {@code OrderItem} to delete
     */
    public void deleteOrderItem(int id) {
        orderItemDAO.delete(id);
    }

    /**
     * Retrieves all {@link OrderItem} records from the database.
     *
     * @return a list of all {@code OrderItem} entities
     * @throws SQLException if a database access error occurs
     */
    public List<OrderItem> findAllOrderItems() throws SQLException {
        return orderItemDAO.findAll();
    }
}
