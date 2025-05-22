package DataAccessLayer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

/**
 * Generic abstract class for Data Access Objects (DAO), providing common CRUD operations
 * (Create, Read, Update, Delete) for any entity type.
 * @param <T> the type of the entity this DAO will manage
 * This class uses reflection to dynamically map database table rows to Java objects of type {@code T},
 * assuming the class name matches the table name and fields match column names.
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    /**
     * Constructor that determines the actual class type for the generic parameter {@code T}.
     */

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Constructs a SELECT query for a specific field.
     *
     * @param field the field/column name
     * @return the constructed SQL SELECT query
     */

    private String createSelectQuery(String field) {
        return "SELECT * FROM " + type.getSimpleName() + " WHERE " + field + " = ?";
    }

    /**
     * Retrieves all records from the database table corresponding to type {@code T}.
     *
     * @return a list of objects of type {@code T}
     * @throws SQLException if a database access error occurs
     */

    public List<T> findAll() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Finds an object of type {@code T} by its ID.
     *
     * @param id the ID of the object
     * @return the found object, or {@code null} if not found
     */

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> results = createObjects(resultSet);
            return results.isEmpty() ? null : results.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates a list of objects from a ResultSet by mapping each row to a Java object of type {@code T}.
     *
     * @param resultSet the ResultSet containing database rows
     * @return a list of mapped objects
     */

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<T> ctor = null;

        for (Constructor<?> c : type.getDeclaredConstructors()) {
            if (c.getParameterCount() == 0) {
                ctor = (Constructor<T>) c;
                break;
            }
        }

        if (ctor == null) {
            throw new IllegalArgumentException("No no-arg constructor found for class " + type.getName());
        }

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = ctor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    if (!columnNames.contains(fieldName)) {
                        continue;
                    }

                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    if (method != null) {
                        method.invoke(instance, value);
                    }
                }

                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Inserts an object of type {@code T} into the corresponding database table.
     *
     * @param t the object to insert
     * @return the inserted object
     */

    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;

        Field[] fields = type.getDeclaredFields();
        StringBuilder query = new StringBuilder("INSERT INTO " + type.getSimpleName() + " (");

        List<Field> dbFields = new ArrayList<>();
        for (Field field : fields) {
            if (!List.class.isAssignableFrom(field.getType())) {
                dbFields.add(field);
            }
        }

        for (int i = 0; i < dbFields.size(); i++) {
            query.append(dbFields.get(i).getName());
            if (i < dbFields.size() - 1) {
                query.append(", ");
            }
        }

        query.append(") VALUES (");
        for (int i = 0; i < dbFields.size(); i++) {
            query.append("?");
            if (i < dbFields.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());

            for (int i = 0; i < dbFields.size(); i++) {
                dbFields.get(i).setAccessible(true);
                statement.setObject(i + 1, dbFields.get(i).get(t));
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * Updates an existing object in the database.
     * The object must have an {@code id} field used to locate the row to update.
     *
     * @param t the object with updated values
     * @return the updated object
     */

    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        Field[] fields = type.getDeclaredFields();

        StringBuilder query = new StringBuilder("UPDATE " + type.getSimpleName() + " SET ");
        int count = 0;
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id") && !List.class.isAssignableFrom(field.getType())) {
                if (count > 0) {
                    query.append(", ");
                }
                query.append(field.getName()).append(" = ?");
                count++;
            }
        }
        query.append(" WHERE id = ?");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());

            int index = 1;
            Object idValue = null;

            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().equalsIgnoreCase("id") && !List.class.isAssignableFrom(field.getType())) {
                    statement.setObject(index++, field.get(t));
                } else if (field.getName().equalsIgnoreCase("id")) {
                    idValue = field.get(t);
                }
            }

            statement.setObject(index, idValue);
            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * Deletes a record from the database based on its ID.
     *
     * @param id the ID of the record to delete
     */

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
