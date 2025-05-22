package Presentation;

import BusinessLogicLayer.*;
import DataAccessLayer.LogDAO;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The OrderPanel class provides a user interface for managing product orders.
 * <p>
 *     Users can select clients and products, add items to a cart, place orders, view order history,
 *     and delete orders. Each completed order is logged for billing purposes.
 * */

public class OrderPanel extends JPanel {
    private final JComboBox<Client> clientBox = new JComboBox<>();
    private final JComboBox<Product> productBox = new JComboBox<>();
    private final JTextField quantityField = new JTextField();
    private final JButton addToCartBtn = new JButton("Add to Cart");
    private final JButton finalizeOrderBtn = new JButton("Place Order");
    private final JButton refreshBtn = new JButton("Refresh Orders");
    private final JButton deleteBtn = new JButton("Delete Order");
    private JTable orderTable = new JTable();
    private JTable cartTable = new JTable();

    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;
    private OrderItemBLL itemBLL;

    private final List<OrderItem> cart = new ArrayList<>();

    /**
     * Constructs the order panel with UI components for order creation and management.
     */

    public OrderPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Client:")); formPanel.add(clientBox);
        formPanel.add(new JLabel("Product:")); formPanel.add(productBox);
        formPanel.add(new JLabel("Quantity:")); formPanel.add(quantityField);
        formPanel.add(addToCartBtn);
        formPanel.add(finalizeOrderBtn);
        formPanel.add(refreshBtn);
        formPanel.add(deleteBtn);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);

        centerPanel.add(new JLabel("Cart:"));
        centerPanel.add(cartScrollPane);
        centerPanel.add(new JLabel("Order History:"));
        centerPanel.add(orderScrollPane);

        add(formPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes the business logic layers and sets up event listeners for UI buttons.
     *
     * @param clientBLL   logic layer for clients
     * @param productBLL  logic layer for products
     * @param orderBLL    logic layer for orders
     * @param itemBLL     logic layer for order items
     */

    public void setListeners(ClientBLL clientBLL, ProductBLL productBLL, OrderBLL orderBLL, OrderItemBLL itemBLL) {
        this.clientBLL = clientBLL;
        this.productBLL = productBLL;
        this.orderBLL = orderBLL;
        this.itemBLL = itemBLL;

        try {
            clientBox.removeAllItems();
            clientBLL.findAllClients().forEach(clientBox::addItem);

            productBox.removeAllItems();
            productBLL.findAllProducts().forEach(productBox::addItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

        refreshBtn.addActionListener(e -> refreshOrders());

        addToCartBtn.addActionListener(e -> {
            try {
                Product product = (Product) productBox.getSelectedItem();
                int quantity = Integer.parseInt(quantityField.getText());

                if (quantity <= 0 || quantity > product.getStock()) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity or insufficient stock!");
                    return;
                }

                OrderItem item = new OrderItem(0, product.getId(), quantity, product.getPrice());
                cart.add(item);
                refreshCart();
                JOptionPane.showMessageDialog(this, "Product added to cart!");
                quantityField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        finalizeOrderBtn.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty.");
                return;
            }

            try {
                Client client = (Client) clientBox.getSelectedItem();

                double total = cart.stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum();

                Orders order = new Orders(client.getId(), new Date(), total);
                order = orderBLL.insertOrder(order);

                for (OrderItem item : cart) {
                    item.setOrderId(order.getId());
                    itemBLL.insertOrderItem(item);

                    Product product = productBLL.findProductById(item.getProductId());
                    product.setStock(product.getStock() - item.getQuantity());
                    productBLL.updateProduct(product);
                }

                order.setItems(new ArrayList<>(cart));

                Bill bill = new Bill(
                        0,
                        order.getId(),
                        client.getId(),
                        client.getName(),
                        order.getOrderData(),
                        total
                );
                new LogDAO().insertBill(bill);

                cart.clear();
                refreshCart();
                JOptionPane.showMessageDialog(this, "Order placed!");
                refreshOrders();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error placing order: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = orderTable.getSelectedRow();
            if (row >= 0) {
                int orderId = (int) orderTable.getValueAt(row, 0);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete order #" + orderId + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        itemBLL.deleteOrderItem(orderId);
                        orderBLL.deleteOrder(orderId);
                        JOptionPane.showMessageDialog(this, "Order deleted!");
                        refreshOrders();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error deleting order: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order to delete.");
            }
        });

        refreshOrders();
    }

    /**
     * Updates the order history table with all existing orders and their items.
     * */

    private void refreshOrders() {
        try {
            List<OrderItem> items = itemBLL.findAllOrderItems();
            List<Product> products = productBLL.findAllProducts();

            List<OrderItemView> itemViews = items.stream().map(item -> {
                String productName = products.stream()
                        .filter(p -> p.getId() == item.getProductId())
                        .map(Product::getName)
                        .findFirst()
                        .orElse("Unknown");

                return new OrderItemView(
                        item.getOrderId(),
                        productName,
                        item.getQuantity(),
                        item.getPrice()
                );
            }).toList();

            orderTable.setModel(TableGenerator.generateTable(itemViews, OrderItemView.class).getModel());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the cart table based on the current contests of the cartt.
     * */

    private void refreshCart() {
        try {
            List<Product> products = productBLL.findAllProducts();
            List<OrderItemView> itemViews = cart.stream().map(item -> {
                String productName = products.stream()
                        .filter(p -> p.getId() == item.getProductId())
                        .map(Product::getName)
                        .findFirst()
                        .orElse("Unknown");

                return new OrderItemView(
                        item.getOrderId(),
                        productName,
                        item.getQuantity(),
                        item.getPrice()
                );
            }).toList();

            cartTable.setModel(TableGenerator.generateTable(itemViews, OrderItemView.class).getModel());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}