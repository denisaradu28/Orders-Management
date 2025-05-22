package DataAccessLayer;

import Connection.ConnectionFactory;
import Model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for handling operations related to the billing log.
 * <p>
 * This class provides methods to insert billing records and retrieve all billing logs
 * from the database. The log is used to track historical order transactions.
 */

public class LogDAO {

    /**
     * Inserts a new bill entry into the {@code log} table.
     *
     * @param bill the {@link Bill} record containing order and client information
     * @throws RuntimeException if a SQL error occurs during insertion
     */

    public void insertBill(Bill bill)
    {
        String query = "INSERT INTO log (orderID, clientId, clientName, orderData, totalAmount) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, bill.orderId());
            preparedStatement.setInt(2, bill.clientId());
            preparedStatement.setString(3, bill.clientName());
            preparedStatement.setTimestamp(4, new Timestamp(bill.orderData().getTime()));
            preparedStatement.setDouble(5, bill.totalAmount());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Bill into Log: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all bill records from the {@code log} table.
     *
     * @return a list of {@link Bill} objects representing all log entries
     * @throws RuntimeException if a SQL error occurs during retrieval
     */

    public List<Bill> findAllBills(){
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM log";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery()){

            while(rs.next())
            {
                Bill bill = new Bill(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        rs.getInt("clientId"),
                        rs.getString("clientName"),
                        rs.getTimestamp("orderData"),
                        rs.getDouble("totalAmount")
                );
                bills.add(bill);
            }

        }catch (SQLException e)
        {
            throw new RuntimeException("Error finding Bills from Log: " + e.getMessage(), e);
        }
        return bills;
    }

}
