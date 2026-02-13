import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:library.db";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create students table
            String createStudentsTable = 
                "CREATE TABLE IF NOT EXISTS students(" +
                "sid INT PRIMARY KEY, " +
                "firstname VARCHAR(50), " +
                "lastname VARCHAR(50))";
            stmt.execute(createStudentsTable);

            // Create books table
            String createBooksTable = 
                "CREATE TABLE IF NOT EXISTS books(" +
                "bid INT PRIMARY KEY, " +
                "title VARCHAR(50), " +
                "author VARCHAR(50), " +
                "pages INT)";
            stmt.execute(createBooksTable);

            // Create loans table (fixed foreign key reference)
            String createLoansTable = 
                "CREATE TABLE IF NOT EXISTS loans(" +
                "lid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sid INT, " +
                "bid INT, " +
                "loan_date DATE, " +
                "return_date DATE, " +
                "FOREIGN KEY (sid) REFERENCES students(sid), " +
                "FOREIGN KEY (bid) REFERENCES books(bid))";
            stmt.execute(createLoansTable);

            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
