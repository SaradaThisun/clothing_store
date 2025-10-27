package Model;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class order_details_model {

    private static final String URL = "jdbc:mysql://localhost:3306/clothing_store";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Load all item IDs
    public void loadItemIds(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        String sql = "SELECT item_id FROM items";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) comboBox.addItem(rs.getString("item_id"));

        } catch (Exception e) {
            System.out.println("Error loading item IDs: " + e.getMessage());
        }
    }

    // Get item details
    public ResultSet getItemDetails(String itemId) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE item_id=?");
            stmt.setString(1, itemId);
            return stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("Error getting item details: " + e.getMessage());
            return null;
        }
    }

    public boolean saveOrder(DefaultTableModel tableModel, double total) {
        if (tableModel.getRowCount() == 0) {
            System.out.println(" No items to save.");
            return false;
        }

        Connection conn = null;
        PreparedStatement summaryStmt = null;
        PreparedStatement detailStmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);

            
            String summarySQL = "INSERT INTO orders_summary (total) VALUES (?)";
            summaryStmt = conn.prepareStatement(summarySQL, Statement.RETURN_GENERATED_KEYS);
            summaryStmt.setDouble(1, total);
            summaryStmt.executeUpdate();

            rs = summaryStmt.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) orderId = rs.getInt(1);

            
            String detailSQL = """
                INSERT INTO order_details (order_id, item_id, name, qty, type, size, price, total_price)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
            detailStmt = conn.prepareStatement(detailSQL);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                detailStmt.setInt(1, orderId);
                detailStmt.setString(2, tableModel.getValueAt(i, 1).toString());
                detailStmt.setString(3, tableModel.getValueAt(i, 2).toString());
                detailStmt.setInt(4, Integer.parseInt(tableModel.getValueAt(i, 3).toString()));
                detailStmt.setString(5, tableModel.getValueAt(i, 4).toString());
                detailStmt.setString(6, tableModel.getValueAt(i, 5).toString());
                detailStmt.setDouble(7, Double.parseDouble(tableModel.getValueAt(i, 6).toString()));
                detailStmt.setDouble(8, Double.parseDouble(tableModel.getValueAt(i, 7).toString()));
                detailStmt.addBatch();
            }

            detailStmt.executeBatch();
            conn.commit();
            success = true;
            System.out.println("Order saved (Order ID: " + orderId + ")");

        } catch (Exception e) {
            System.out.println("Error saving order: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (Exception ignore) {}
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
            try { if (summaryStmt != null) summaryStmt.close(); } catch (Exception ignore) {}
            try { if (detailStmt != null) detailStmt.close(); } catch (Exception ignore) {}
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
        }

        return success;
    }
    
    // Get next available Order ID
public int getNextOrderId() {
    String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'orders_summary' AND TABLE_SCHEMA = 'clothing_store'";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt("AUTO_INCREMENT");
        }

    } catch (Exception e) {
        System.out.println("Error getting next order ID: " + e.getMessage());
    }
    return 1; 
}

}



