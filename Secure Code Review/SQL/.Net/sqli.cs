using System;
using System.Data.SQLite;

class VulnerableLoginApp
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
                                                                                                                                           // Vulnerable SQL query concatenating user input directly
        string query = $"SELECT * FROM users WHERE username = '{username}' AND password = '{password}'";

        Console.WriteLine("Executing query: " + query);

        using (var connection = new SQLiteConnection(connectionString))
        {
            connection.Open();
            using (var command = new SQLiteCommand(query, connection))
            {
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























//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
.....................................................................................................................................................................................................

How can this be exploited?

An attacker can craft malicious input to manipulate the query and bypass authentication or access unauthorized data.
Example 1: Bypassing Authentication

If an attacker provides the following input:

    Username: ' OR '1'='1
    Password: anything

The resulting query would look like this:

SELECT * FROM users WHERE username = '' OR '1'='1' AND password = 'anything';

    Breakdown:
        username = '' always evaluates as false.
        '1'='1' always evaluates as true.
        Since the OR condition makes the entire WHERE clause true, the query returns the first user from the users table, logging in the attacker as the admin without knowing the actual password.

Example 2: Accessing Data

An attacker can craft inputs to extract or modify data. For example:

    Username: ' UNION SELECT NULL, sqlite_version(), NULL --
    Password: anything

The query would become:

SELECT * FROM users WHERE username = '' UNION SELECT NULL, sqlite_version(), NULL -- AND password = 'anything';

  














































































