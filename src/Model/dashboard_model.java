
package Model;
import java.sql.*;

public class dashboard_model {
    private static final String URL = "jdbc:mysql://localhost:3306/clothing_store";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Get total number of items
    public int getItemCount() {
        return getCount("SELECT COUNT(*) FROM items");
    }

    // Get total number of orders
    public int getOrderCount() {
        return getCount("SELECT COUNT(*) FROM orders");
    }

    // Get total number of order details
    public int getOrderDetailCount() {
        return getCount("SELECT COUNT(*) FROM orders");
    }

    // Common method for executing COUNT queries
    private int getCount(String query) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

