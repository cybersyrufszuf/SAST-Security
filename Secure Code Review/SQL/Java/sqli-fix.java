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

















//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Why is this Fixed Code Secure?

    Parameterized Query:
        The PreparedStatement binds user inputs as parameters. These parameters are treated as literal values, not executable SQL code, preventing SQL injection.

    No Direct Concatenation:
        The query uses ? placeholders, eliminating the need to concatenate user input.

    Safe Query Execution:
        Any malicious SQL input (e.g., admin' --) is treated as a plain string, preventing it from altering the query logic.

Example of Fixed Code Behavior

For the same malicious input:

    username = admin' --
    password = (empty)

The query executed becomes:

SELECT * FROM users WHERE username = 'admin'' --' AND password = ''

Here, the injected SQL (--) is escaped and treated as part of the username value, making it harmless.





















































