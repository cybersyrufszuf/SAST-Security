import java.sql.*;
import java.util.Scanner;

public class SecureLoginApp {

    public static void main(String[] args) {
        createDatabase();
        login();
    }

    private static void createDatabase() {
        String url = "jdbc:sqlite:example.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // Create the users table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
            stmt.execute(createTableSQL);

            // Insert a default user (admin)
            String insertUserSQL = "INSERT OR IGNORE INTO users (id, username, password) VALUES (1, 'admin', 'adminpass')";
            stmt.execute(insertUserSQL);

            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Database creation failed: " + e.getMessage());
        }
    }

    private static void login() {
        String url = "jdbc:sqlite:example.db";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(loginQuery)) {

            // Set parameters to prevent SQL injection
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful");
            } else {
                System.out.println("Login failed");
            }
        } catch (SQLException e) {
            System.out.println("Database query failed: " + e.getMessage());
        }
    }
}
