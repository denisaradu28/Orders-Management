package BusinessLogicLayer;

import DataAccessLayer.ProductDAO;
import Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static Connection.ConnectionFactory.getConnection;

/**
 * The {@code ProductBLL} (Business Logic Layer) class provides the business rules and validation logic
 * for operations involving {@link Product} entities.
 *
 * <p>It interacts with the {@link ProductDAO} to perform CRUD operations on the database,
 * while also enforcing input validation before persisting or updating data.</p>
 */

public class ProductBLL {

    private final ProductDAO productDAO = new ProductDAO();

    /**
     * Validates the product data according to business rules.
     *
     * @param product the {@link Product} to validate
     * @throws IllegalArgumentException if the product name is blank,
     *                                  or if price or stock are negative
     */

    private void validateProduct(Product product) {
        if(product.getName() == null || product.getName().isBlank())
            throw new IllegalArgumentException("Product name cannot be blank");
        if(product.getPrice() < 0)
            throw new IllegalArgumentException("Product price cannot be negative");
        if(product.getStock() < 0)
            throw new IllegalArgumentException("Product stock cannot be negative");
    }

    /**
     * Inserts a new {@link Product} into the database after validating its data.
     *
     * @param product the {@code Product} to insert
     * @return the inserted {@code Product} object
     */

    public Product insertProduct(Product product) {
        validateProduct(product);
        return productDAO.insert(product);
    }

    /**
     * Updates an existing {@link Product} in the database with new values.
     *
     * @param product the {@code Product} containing updated values
     * @return the updated {@code Product} if the update was successful
     * @throws SQLException if the product does not exist or a database error occurs
     */

    public Product updateProduct(Product product) {
        String query = "UPDATE product SET name = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.setInt(4, product.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return product;
            } else {
                throw new SQLException("No product found with id " + product.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a product from the database using its ID.
     *
     * @param id the ID of the {@code Product} to delete
     */

    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    /**
     * Retrieves all products from the database.
     *
     * @return a {@code List} of all {@link Product} entities
     * @throws SQLException if a database error occurs
     */

    public List<Product> findAllProducts() throws SQLException {
        return productDAO.findAll();
    }

    /**
     * Finds a product in the database by its ID.
     *
     * @param productId the ID of the {@code Product} to retrieve
     * @return the corresponding {@code Product}, or {@code null} if not found
     */

    public Product findProductById(int productId) {
        return productDAO.findById(productId);
    }
}
