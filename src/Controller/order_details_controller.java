package Controller;

import Model.order_details_model;
import View.order_details_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;

public class order_details_controller {

    private final order_details_view view;
    private final order_details_model model;
    private int currentOrderId; // track the temporary order ID before saving

    public order_details_controller(order_details_view view, order_details_model model) {
        this.view = view;
        this.model = model;

        // Load item IDs
        model.loadItemIds(view.jComboBox1);

        // Generate a new temporary order ID (next from DB)
        currentOrderId = model.getNextOrderId();
        System.out.println("Temporary Order ID started: " + currentOrderId);

        addListeners();
    }

    // Add all listeners
    private void addListeners() {
        view.jComboBox1.addActionListener(new ComboBoxListener());
        view.btnadd.addActionListener(new AddAction());
        view.jButton1.addActionListener(new ConfirmOrderAction());
        view.btnback.addActionListener(new BackAction());
    }

    // Load item info when selected
    class ComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedId = (String) view.jComboBox1.getSelectedItem();
            if (selectedId != null) {
                try (ResultSet rs = model.getItemDetails(selectedId)) {
                    if (rs != null && rs.next()) {
                        view.txtname.setText(rs.getString("name"));
                        view.txttype.setText(rs.getString("type"));
                        view.txtsize.setText(rs.getString("size"));
                        view.txtprice.setText(rs.getString("price"));
                        view.txtqty.setText("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //Add button — Add item to JTable (with visible order ID)
    class AddAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String itemId = (String) view.jComboBox1.getSelectedItem();
                String name = view.txtname.getText();
                String type = view.txttype.getText();
                String size = view.txtsize.getText();
                double price = Double.parseDouble(view.txtprice.getText());
                int qty = Integer.parseInt(view.txtqty.getText());
                double totalPrice = qty * price;

                if (qty <= 0) {
                    JOptionPane.showMessageDialog(view, "Quantity must be greater than 0!");
                    return;
                }

                DefaultTableModel tableModel = (DefaultTableModel) view.tbldetails.getModel();

                Object[] row = {
                    currentOrderId, // show current order ID in table
                    itemId,
                    name,
                    qty,
                    type,
                    size,
                    price,
                    totalPrice
                };
                tableModel.addRow(row);

                // Update total
                double total = 0;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    total += Double.parseDouble(tableModel.getValueAt(i, 7).toString());
                }
                view.jTextField1.setText(String.format("%.2f", total));

                JOptionPane.showMessageDialog(view, "Item added to order!");
                view.txtqty.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Please enter valid quantity!");
            }
        }
    }

    // Confirm Order — Save all to DB
    class ConfirmOrderAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel modelTable = (DefaultTableModel) view.tbldetails.getModel();

            if (modelTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(view, "No items to confirm!");
                return;
            }

            // Calculate total
            double total = 0;
            for (int i = 0; i < modelTable.getRowCount(); i++) {
                total += Double.parseDouble(modelTable.getValueAt(i, 7).toString());
            }

            boolean ok = model.saveOrder(modelTable, total);
            if (ok) {
                JOptionPane.showMessageDialog(view, "Order saved to database!\nOrder ID: " + currentOrderId);

                // Clear table for next order
                modelTable.setRowCount(0);
                view.jTextField1.setText("0.00");

                // Start new order ID for next order
                currentOrderId = model.getNextOrderId();
                System.out.println("New Temporary Order ID started: " + currentOrderId);
            } else {
                JOptionPane.showMessageDialog(view, "⚠️ Error saving order!");
            }
        }
    }

    // Back to dashboard
    class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            View.dashboard_view dashView = new View.dashboard_view();
            Model.dashboard_model dashModel = new Model.dashboard_model();
            new Controller.dashboard_controller(dashView, dashModel);
            dashView.setVisible(true);
            view.dispose();
        }
    }
}
