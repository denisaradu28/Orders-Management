package Model;

import java.util.Date;
import java.util.List;

/**
 * Represents an order placed by a client.
 * Contains information such as client ID, date, total amount, and list of order items.
 */
public class Orders {
    private int id;
    private int clientId;
    private Date orderData;
    private double totalAmount;
    private List<OrderItem> items;

    /**
     * Constructs an {@code Orders} instance without an ID.
     * Typically used when the ID is generated automatically (e.g., by a database).
     *
     * @param clientId    the ID of the client placing the order
     * @param orderDate   the date when the order was placed
     * @param totalAmount the total amount of the order
     */
    public Orders(int clientId, Date orderDate, double totalAmount) {
        this.clientId = clientId;
        this.orderData = orderDate;
        this.totalAmount = totalAmount;
    }

    /**
     * Default constructor.
     * Creates an empty order instance.
     */
    public Orders() {}

    /**
     * Returns the unique ID of the order.
     *
     * @return the order ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the order.
     *
     * @param id the order ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the client who placed the order.
     *
     * @return the client ID
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets the ID of the client who placed the order.
     *
     * @param clientId the client ID to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Returns the date when the order was placed.
     *
     * @return the order date
     */
    public Date getOrderData() {
        return orderData;
    }

    /**
     * Sets the date when the order was placed.
     *
     * @param orderData the order date to set
     */
    public void setOrderData(Date orderData) {
        this.orderData = orderData;
    }

    /**
     * Returns the total amount of the order.
     *
     * @return the total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the order.
     *
     * @param totalAmount the total amount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Returns the list of order items associated with this order.
     *
     * @return the list of order items
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Sets the list of order items associated with this order.
     *
     * @param items the list of order items to set
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Returns a string representation of the order.
     *
     * @return a formatted string containing order information
     */
    @Override
    public String toString() {
        return String.format("Order: ID %d (Client ID: %d) - Date: %s, Total Amount: %.2f",
                id, clientId, orderData, totalAmount);
    }
}
