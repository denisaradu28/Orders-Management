package Model;

import java.util.Date;

/**
 * Represents a bill generated for a specific order.
 * <p>
 * A {@code Bill} contains basic invoice information such as the order ID,
 * client ID, client name, date of the order, and the total amount.
 *
 * @param id          the unique identifier of the bill
 * @param orderId     the ID of the order for which the bill is issued
 * @param clientId    the ID of the client who placed the order
 * @param clientName  the name of the client
 * @param orderData   the date when the order was placed
 * @param totalAmount the total amount to be paid for the order
 */

public record Bill(
        int id,
        int orderId,
        int clientId,
        String clientName,
        Date orderData,
        double totalAmount
) {}