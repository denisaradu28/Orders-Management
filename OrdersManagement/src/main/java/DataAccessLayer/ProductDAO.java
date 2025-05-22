package DataAccessLayer;

import Model.Product;

/**
 * Data Access Object (DAO) class for the {@link Product} entity.
 * <p>
 * This class provides specific data access operations for the Product table.
 * It inherits generic CRUD operations from {@link AbstractDAO}.
 *
 * <p>Typical usage:
 * <pre>{@code
 * ProductDAO productDAO = new ProductDAO();
 * List<Product> products = productDAO.findAll();
 * }</pre>
 *
 * @see AbstractDAO
 * @see Product
 */

public class ProductDAO extends AbstractDAO<Product>{
}
