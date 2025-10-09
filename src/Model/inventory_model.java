

package Model;

import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class inventory_model {
    private static final String URL = "jdbc:mysql://localhost:3306/clothing_store";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Add a new item
    public boolean addItem(String id, String name, String qty, String type, String price, String size) {
        String sql = "INSERT INTO items (item_id, name, qty, type, price, size) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, qty);
            stmt.setString(4, type);
            stmt.setString(5, price);
            stmt.setString(6, size);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing item
    public boolean updateItem(String id, String name, String qty, String type, String price, String size) {
        String sql = "UPDATE items SET name=?, qty=?, type=?, price=?, size=? WHERE item_id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, qty);
            stmt.setString(3, type);
            stmt.setString(4, price);
            stmt.setString(5, size);
            stmt.setString(6, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an item
    public boolean deleteItem(String id) {
        String sql = "DELETE FROM items WHERE item_id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search an item by ID
    public ResultSet searchItem(String id) {
        String sql = "SELECT * FROM items WHERE item_id=?";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            return stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Load all items to JTable
    public void loadItemsToTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear table first
        String sql = "SELECT * FROM items";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("item_id"),
                    rs.getString("name"),
                    rs.getString("qty"),
                    rs.getString("type"),
                    rs.getString("price"),
                    rs.getString("size")
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
