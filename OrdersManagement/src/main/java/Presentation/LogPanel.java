package Presentation;

import DataAccessLayer.LogDAO;
import Model.Bill;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The LogPanel class represents the UI panel that displays a log of all generated bills (orders).
 * <p>
 * It contains a table to show bill details and a button to refresh the list by querying the database.
 * */

public class LogPanel extends JPanel {
    private final JTable billTable = new JTable();
    private final JButton refreshBtn = new JButton("Refresh");

    /**
     * Constructs the LopPanel and initializes the layout and components.
     * <p>
     *     Automatically loads the bill data on creation and sets up the refresh button listener.
     * */

    public LogPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(refreshBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(billTable), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> refreshTable());
        refreshTable();
    }

    /**
     * Refreshes the table by retrieving all bills from the database and updating the table model.
     * <p>
     *     Displays an error message if data loading fails.
     * */

    private void refreshTable() {
        try {
            LogDAO logDAO = new LogDAO();
            List<Bill> bills = logDAO.findAllBills();
            billTable.setModel(TableGenerator.generateTable(bills, Bill.class).getModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading bills: " + e.getMessage());
        }
    }
}
