import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/profitiq";
    private static final String USER = "postgres";
    private static final String PASSWORD = "profitiq123"; // Updated password

    public static Connection getConnection() throws SQLException {
        try {
            // Try to load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found. Please ensure postgresql-connector.jar is in your classpath.");
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
        
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }
}