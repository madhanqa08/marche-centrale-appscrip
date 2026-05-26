package mysqldatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private static Connection connection;

    private static final String URL =
            "jdbc:mysql://localhost:3306/APPSCRIP";

    private static final String USERNAME =
            "root";

    private static final String PASSWORD =
            "Universe9952@";

    public static Connection createConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(
                        URL,
                        USERNAME,
                        PASSWORD
                );

                System.out.println("Database Connected Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
                System.out.println("Database Connection Closed");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
