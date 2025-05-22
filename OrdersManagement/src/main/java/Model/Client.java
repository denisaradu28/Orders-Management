package Model;

/**
 * Represents a client that places an order.
 * <p>
 * A client has a name, address, email, phone number, and a unique identifier.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;

    /**
     * Constructs a new Client without an ID.
     * This constructor is typically used when the ID is generated automatically (e.g., by a database).
     *
     * @param name    the full name of the client
     * @param address the address of the client
     * @param email   the email address of the client
     * @param phone   the phone number of the client
     */
    public Client(String name, String address, String email, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Constructs a new Client with all fields specified.
     *
     * @param id      the unique ID of the client
     * @param name    the full name of the client
     * @param address the address of the client
     * @param email   the email address of the client
     * @param phone   the phone number of the client
     */
    public Client(int id, String name, String address, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Default constructor.
     * Creates an empty client instance.
     */
    public Client() {}

    /**
     * Returns the unique ID of the client.
     *
     * @return the client's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the client.
     *
     * @param id the client's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the client.
     *
     * @return the client's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the client.
     *
     * @param name the client's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the address of the client.
     *
     * @return the client's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the client.
     *
     * @param address the client's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the email address of the client.
     *
     * @return the client's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the client.
     *
     * @param email the client's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the client.
     *
     * @return the client's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the client.
     *
     * @param phone the client's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns a string representation of the client.
     *
     * @return a formatted string containing client information
     */
    @Override
    public String toString() {
        return String.format("Client: %s (ID: %d) - Address: %s, Email: %s, Phone: %s",
                name, id, address, email, phone);
    }
}
