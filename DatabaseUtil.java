import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AppointmentSystem";
    private static final String USER = "SA"; // Replace with your SQL Server username
    private static final String PASSWORD = "DB_Password"; // Replace with your SQL Server password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
} 

