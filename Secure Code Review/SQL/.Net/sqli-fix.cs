using System;
using System.Data.SQLite;

class SecureLoginApp
{
    static void Main(string[] args)
    {
        CreateDatabase();
        Login();
    }

    private static void CreateDatabase()
    {
        string connectionString = "Data Source=example.db;Version=3;";
        using (var connection = new SQLiteConnection(connectionString))
        {
            connection.Open();

            string createTableQuery = @"
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL,
                    password TEXT NOT NULL
                )";
            using (var command = new SQLiteCommand(createTableQuery, connection))
            {
                command.ExecuteNonQuery();
            }

            string insertAdminQuery = @"
                INSERT OR IGNORE INTO users (id, username, password) VALUES (1, 'admin', 'adminpass')";
            using (var command = new SQLiteCommand(insertAdminQuery, connection))
            {
                command.ExecuteNonQuery();
            }

            Console.WriteLine("Database initialized successfully.");
        }
    }

    private static void Login()
    {
        string connectionString = "Data Source=example.db;Version=3;";
        Console.Write("Enter username: ");
        string username = Console.ReadLine();
        Console.Write("Enter password: ");
        string password = Console.ReadLine();

        // Secure SQL query using parameters
        string query = "SELECT * FROM users WHERE username = @username AND password = @password";

        using (var connection = new SQLiteConnection(connectionString))
        {
            connection.Open();
            using (var command = new SQLiteCommand(query, connection))
            {
                // Add parameters to prevent SQL injection
                command.Parameters.AddWithValue("@username", username);
                command.Parameters.AddWithValue("@password", password);

                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        Console.WriteLine("Login successful");
                    }
                    else
                    {
                        Console.WriteLine("Login failed");
                    }
                }
            }
        }
    }
}
