import java.sql.*;
import java.util.Scanner;

public class VulnerableLoginApp {

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

        // This query concatenates user input directly, making it vulnerable to SQL injection
        String loginQuery = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

        System.out.println("Executing query: " + loginQuery);

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(loginQuery);
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














..............................................................................................................................................................
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

How it could be Exploitation

Suppose the attacker enters the following:

    username: admin' --
    password: (left empty)

The SQL query becomes:

SELECT * FROM users WHERE username = 'admin' --' AND password = ''

    The -- is a SQL comment operator, which causes the rest of the query to be ignored.
    This effectively changes the query to:

    SELECT * FROM users WHERE username = 'admin'

    The attacker is authenticated as the admin user without needing the password.

Further Exploitation Risks

    Data Extraction:
        An attacker could craft inputs to extract sensitive data. For example:
            Input: username = ' OR '1'='1 and password = ' OR '1'='1.
            Query becomes:

        SELECT * FROM users WHERE username = '' OR '1'='1' AND password = '' OR '1'='1'

        This retrieves all rows from the users table.

Database Manipulation:

    An attacker could modify the database using injected SQL commands. For example:
        Input: username = admin'; DROP TABLE users; -- and password = anything.
        Query becomes:

        SELECT * FROM users WHERE username = 'admin'; DROP TABLE users; --' AND password = 'anything'

        This executes two commands: a valid query followed by dropping the users table.

Database Compromise:

    Attackers can potentially execute commands to retrieve all data, delete records, or even escalate access depending on the database configuration.












































