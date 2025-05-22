package Presentation;

import BusinessLogicLayer.ClientBLL;
import Model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ClientPanel is a custom JPanel that provides a graphical interface to view
 * and manage clients a Swing application.
 * It offers functionalities to add, edit, delete, and refresh clients.
 * */


public class ClientPanel extends JPanel {
    private JTable table;
    private final JButton addBtn = new JButton("Add");
    private final JButton editBtn = new JButton("Edit");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton refreshBtn = new JButton("Refresh");

    /**
     * Constructor that sets up the layout and initializes UI components.
     * */


    public ClientPanel() {
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
     * Attaches event listeners to the buttons to handle user interactions,
     * using the operations defined in ClientBLL.
     * @param clientBLL instance of ClientBLL used for CRUD operations
     * */

    public void setListeners(ClientBLL clientBLL) {
        refreshBtn.addActionListener(e -> refreshTable(clientBLL));

        addBtn.addActionListener(e -> {
            JTextField name = new JTextField();
            JTextField address = new JTextField();
            JTextField email = new JTextField();
            JTextField phone = new JTextField();
            Object[] fields = {
                    "Name:", name,
                    "Address:", address,
                    "Email:", email,
                    "Phone:", phone
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Add Client", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nameVal = name.getText().trim();
                    String emailVal = email.getText().trim();
                    String phoneVal = phone.getText().trim();

                    if (nameVal.isEmpty() || !emailVal.contains("@") || phoneVal.length() < 10 || !phoneVal.matches("\\d+")) {
                        JOptionPane.showMessageDialog(this, "Invalid input!\n- Name must not be empty\n- Email must contain '@'\n- Phone must be at least 10 digits");
                        return;
                    }

                    Client c = new Client(name.getText(), address.getText(), email.getText(), phone.getText());
                    clientBLL.insertClient(c);
                    refreshTable(clientBLL);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                String nameVal = table.getValueAt(row, 1) != null ? table.getValueAt(row, 1).toString() : "";
                String addressVal = table.getValueAt(row, 2) != null ? table.getValueAt(row, 2).toString() : "";
                String emailVal = table.getValueAt(row, 3) != null ? table.getValueAt(row, 3).toString() : "";
                String phoneVal = table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : "";

                JTextField name = new JTextField(nameVal);
                JTextField address = new JTextField(addressVal);
                JTextField email = new JTextField(emailVal);
                JTextField phone = new JTextField(phoneVal);
                Object[] fields = {
                        "Name:", name,
                        "Address:", address,
                        "Email:", email,
                        "Phone:", phone
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Client", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        if (name.getText().isBlank()) {
                            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                            return;
                        }
                        Client updated = new Client(id, name.getText(), address.getText(), email.getText(), phone.getText());
                        clientBLL.updateClient(updated);
                        refreshTable(clientBLL);
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
                clientBLL.deleteClient(id);
                refreshTable(clientBLL);
            }
        });

        refreshTable(clientBLL);
    }

    /**
     * Updates the table with current data from the database.
     * @param clientBLL instance of ClientBLL used to retrieve data
     * */

    private void refreshTable(ClientBLL clientBLL) {
        try {
            List<Client> clients = clientBLL.findAllClients();
            table.setModel(TableGenerator.generateTable(clients, Client.class).getModel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
