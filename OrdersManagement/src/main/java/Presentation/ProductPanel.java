package Presentation;

import BusinessLogicLayer.ProductBLL;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ProductPanel is a GUI component responsible for managing product-related operations.
 * It allows the user to add, edit, delete, and view products in a table.
 * */

public class ProductPanel extends JPanel {
    private JTable table;
    private final JButton addBtn = new JButton("Add");
    private final JButton editBtn = new JButton("Edit");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton refreshBtn = new JButton("Refresh");

    /**
     * Constructs the ProductPanel and initializes the UI layout and buttons.
     * */

    public ProductPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(addBtn);
        topPanel.add(editBtn);
        topPanel.add(deleteBtn);
        topPanel.add(refreshBtn);

        table = new JTable();
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Sets up the event listeners for the buttons and initializes the product table.
     *
     * @param productBLL the business logic layer object used to perform product operations.
     * */

    public void setListeners(ProductBLL productBLL) {
        refreshBtn.addActionListener(e -> refreshTable(productBLL));

        addBtn.addActionListener(e -> {
            JTextField name = new JTextField();
            JTextField price = new JTextField();
            JTextField stock = new JTextField();
            Object[] fields = {
                    "Name:", name,
                    "Price:", price,
                    "Stock:", stock
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Add Product", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String nameVal = name.getText().trim();
                String priceVal = price.getText().trim();
                String stockVal = stock.getText().trim();

                try {
                    double priceNum = Double.parseDouble(priceVal);
                    int stockNum = Integer.parseInt(stockVal);
                    if (nameVal.isEmpty() || priceNum < 0 || stockNum < 0) {
                        throw new Exception();
                    }

                    Product p = new Product(nameVal, priceNum, stockNum);
                    productBLL.insertProduct(p);
                    refreshTable(productBLL);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid input!\n- Name must not be empty\n- Price and Stock must be non-negative numbers");
                }
            }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) table.getValueAt(row, 0);
                String nameVal = (String) table.getValueAt(row, 1);
                double priceVal = (double) table.getValueAt(row, 2);
                int stockVal = (int) table.getValueAt(row, 3);

                JTextField name = new JTextField(nameVal);
                JTextField price = new JTextField(String.valueOf(priceVal));
                JTextField stock = new JTextField(String.valueOf(stockVal));
                Object[] fields = {
                        "Name:", name,
                        "Price:", price,
                        "Stock:", stock
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Product", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        Product updated = new Product(id, name.getText(),
                                Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()));
                        productBLL.updateProduct(updated);
                        refreshTable(productBLL);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) table.getValueAt(row, 0);
                productBLL.deleteProduct(id);
                refreshTable(productBLL);
            }
        });

        refreshTable(productBLL);
    }

    /**
     * Refreshes the table with the current list of products from the database.
     *
     * @param productBLL the business logic object used to fetch product data.
     * */

    private void refreshTable(ProductBLL productBLL) {
        try {
            List<Product> products = productBLL.findAllProducts();
            table.setModel(TableGenerator.generateTable(products, Product.class).getModel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
