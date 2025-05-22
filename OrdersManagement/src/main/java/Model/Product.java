package Model;

/**
 * Represents a product.
 * <p>
 * This class contains details such as the product ID, name, price, and stock quantity.
 * It is used throughout the application for managing product-related operations.
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructs a product with the specified name, price, and stock quantity.
     * This constructor is typically used when the product ID is generated automatically (e.g., by a database).
     *
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the available stock quantity of the product
     */
    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Default constructor.
     * Creates an empty product instance.
     */
    public Product() {}

    /**
     * Constructs a product with all fields specified.
     *
     * @param id    the unique ID of the product
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the available stock quantity of the product
     */
    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Returns the unique ID of the product.
     *
     * @return the product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the product.
     *
     * @param id the product ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the product.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the product.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the available stock quantity of the product.
     *
     * @return the stock quantity
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the available stock quantity of the product.
     *
     * @param stock the stock quantity to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns a string representation of the product.
     *
     * @return a formatted string containing product information
     */
    @Override
    public String toString() {
        return String.format("Product: %s (ID: %d) - Price: %.2f, Stock: %d", name, id, price, stock);
    }
}
