package Model;

import java.sql.*;

public class login_model {

    private static final String URL = "jdbc:mysql://localhost:3306/clothing_store";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Authenticate user credentials
    public boolean checkLogin(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                return rs.next(); // true if user found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
