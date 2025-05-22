package Model;

/**
 * A view model class that represents an item in an order,
 * including the product's name for display purposes.
 * <p>
 * This class is typically used for displaying order item information
 * in a user interface or report, where the product name is more informative
 * than a product ID.
 */
public class OrderItemView {
    private int orderId;
    private String productName;
    private int quantity;
    private double price;

    /**
     * Default constructor.
     * Creates an empty instance of {@code OrderItemView}.
     */
    public OrderItemView() {}

    /**
     * Constructs a new {@code OrderItemView} with the specified details.
     *
     * @param orderId     the ID of the order this item belongs to
     * @param productName the name of the product
     * @param quantity    the quantity of the product ordered
     * @param price       the price per unit of the product
     */
    public OrderItemView(int orderId, String productName, int quantity, double price) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Returns the ID of the order this item belongs to.
     *
     * @return the order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the ID of the order this item belongs to.
     *
     * @param orderId the order ID to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns the name of the product.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     *
     * @param productName the product name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Returns the quantity of the product ordered.
     *
     * @return the quantity ordered
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product ordered.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the price per unit of the product.
     *
     * @return the unit price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per unit of the product.
     *
     * @param price the unit price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Calculates and returns the total price for this order item
     * (quantity multiplied by the unit price).
     *
     * @return the total price of the order item
     */
    public double getTotalPrice() {
        return price * quantity;
    }

    /**
     * Returns a string representation of the {@code OrderItemView},
     * including order ID, product name, quantity, unit price, and total price.
     *
     * @return a formatted string describing this order item view
     */
    @Override
    public String toString() {
        return String.format("OrderItemView(Order ID: %d, Product: %s, Quantity: %d, Unit Price: %.2f, Total: %.2f)",
                orderId, productName, quantity, price, getTotalPrice());
    }
}
