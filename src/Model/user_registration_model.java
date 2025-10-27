
package Model;

import java.sql.*;


public class user_registration_model {
     private static final String URL = "jdbc:mysql://localhost:3306/clothing_store";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Insert new user into database
    public boolean registerUser(String userId, String username, String password) {
        String sql = "INSERT INTO users (user_id, username, password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("User ID already exists!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verify login 
    public boolean verifyLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); 

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


