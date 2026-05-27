package mysqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private static Connection connection;

    private static final String URL =
            System.getenv("DB_URL");

    private static final String USERNAME =
            System.getenv("DB_USERNAME");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    public static Connection createConnection()
    {
        try
        {
            if(connection == null || connection.isClosed())
            {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection =
                        DriverManager.getConnection(
                                URL,
                                USERNAME,
                                PASSWORD
                        );

                System.out.println("Database Connected Successfully");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return connection;
    }


    public static void closeConnection()
    {
        try
        {
            if(connection != null && !connection.isClosed())
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