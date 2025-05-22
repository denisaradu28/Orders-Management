package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * The View class represents the main window of the Orders Management application.
 * It provides a tabbed user interface for managing clients, products, orders, and logs.
 * <p>
 * Each tab contains a dedicated panel that handles specific functionality.
 * */

public class View extends JFrame {

    private JTabbedPane tabbedPane;

    private ClientPanel clientPanel;
    private ProductPanel productPanel;
    private OrderPanel orderPanel;
    private LogPanel logPanel;

    /**
     * Constructs the main GUI window and initializes all sub-panels (Clients, Products, Orders, Logs).
     * Sets up the tabbed layout and configures the window properties
     * */

    public View() {
        setTitle("Orders Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        clientPanel = new ClientPanel();
        productPanel = new ProductPanel();
        orderPanel = new OrderPanel();
        logPanel = new LogPanel();

        tabbedPane.addTab("Clients", clientPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Orders", orderPanel);
        tabbedPane.addTab("Logs", logPanel);

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Returns the panel responsible for managing clients.
     * */

    public ClientPanel getClientPanel() {
        return clientPanel;
    }

    /**
     * Returns the panel responsible for managing products.
     * */

    public ProductPanel getProductPanel() {
        return productPanel;
    }

    /**
     * Returns the panel responsible for handling orders.
     * */

    public OrderPanel getOrderPanel() {
        return orderPanel;
    }

}
