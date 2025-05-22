package DataAccessLayer;

import Model.OrderItem;

/**
 * Data Access Object (DAO) class for the {@link OrderItem} entity.
 * <p>
 * This class provides specific data access operations for the {@code OrderItem} table.
 * It extends the generic {@link AbstractDAO} class, which offers basic CRUD operations.
 * No additional methods are defined in this class, but custom operations for {@code OrderItem}
 * can be added here in the future if needed.
 *
 * <p>Typical usage:
 * <pre>{@code
 * OrderItemDAO orderItemDAO = new OrderItemDAO();
 * List<OrderItem> items = orderItemDAO.findAll();
 * }</pre>
 *
 * @see AbstractDAO
 * @see OrderItem
 */
public class OrderItemDAO extends AbstractDAO<OrderItem> {
}
