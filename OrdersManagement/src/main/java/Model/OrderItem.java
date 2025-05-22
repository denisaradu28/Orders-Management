package Model;

/**
 * Represents an item that is part of an order, linking a product to an order with a specific quantity and price.
 * <p>
 * This class is used to store derailed information about products included in a specific order.
 * */

public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    /**
     * Constructs a new {@code OrderItem} with the given order ID, product ID, quantity, and price.
     * This constructor is used when the ID is generated elsewhere (e.g., by the database).
     *
     * @param orderId   the ID of the order this item belongs to
     * @param productId the ID of the product ordered
     * @param quantity  the number of units ordered
     * @param price     the price per unit
     */

    public OrderItem(int orderId, int productId, int quantity, double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Default constructor.
     */

    public OrderItem(){}

    /**
     * Constructs a new {@code OrderItem} with all fields specified, including ID.
     *
     * @param id        the unique ID of the order item
     * @param orderId   the ID of the order this item belongs to
     * @param productId the ID of the product ordered
     * @param quantity  the number of units ordered
     * @param price     the price per unit
     */

    public OrderItem(int id, int orderId, int productId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Returns the ID of the order item.
     * */

    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the order item.
     * */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the order this item belongs to.
     * */

    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the ID of the order this item belongs to.
     * */

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns the ID of the product ordered.
     * */

    public int getProductId() {
        return productId;
    }

    /**
     * Sets the ID of the product ordered.
     * */

    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Returns the quantity of the product ordered.
     * */

    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product ordered.
     * */

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the price of the product ordered.
     * */

    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product ordered.
     * */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the order item.
     * */

    @Override
    public String toString() {
        return String.format("OrderItem: ID %d (Order ID: %d, Product ID: %d) - Quantity: %d, Price: %.2f",
                id, orderId, productId, quantity, price);
    }

}