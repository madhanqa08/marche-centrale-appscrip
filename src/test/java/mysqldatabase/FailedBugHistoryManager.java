package mysqldatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class FailedBugHistoryManager
{
    public int getFailedBugCount()
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int count = 0;

        try
        {
            Connection connection =
                    DatabaseConnection.createConnection();

            String query =
                    "SELECT COUNT(*) AS total FROM FailedBugs";

            preparedStatement =
                    connection.prepareStatement(query);

            resultSet =
                    preparedStatement.executeQuery();

            if(resultSet.next())
            {
                count =
                        resultSet.getInt("total");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }

                if(preparedStatement != null)
                {
                    preparedStatement.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return count;
    }

    public void moveFailedBugsToPermanentTable()
    {
        PreparedStatement insertStatement = null;
        PreparedStatement deleteStatement = null;

        try
        {
            Connection connection = DatabaseConnection.createConnection();

            /*
             * Copy all records from FailedBugs
             * to PermanentFailedBugRecords
             */
            String insertQuery =
                    "INSERT INTO PermanentFailedBugRecords " +
                            "(record_no, title, descriptions, image_path, section, priority, label, times) " +
                            "SELECT record_no, title, descriptions, image_path, section, priority, label, times " +
                            "FROM FailedBugs";

            insertStatement = connection.prepareStatement(insertQuery);

            int insertedRows = insertStatement.executeUpdate();

            System.out.println(insertedRows + " records moved to PermanentFailedBugRecords");

            /*
             * Optional:
             * Clear FailedBugs table after moving
             */
            String deleteQuery = "DELETE FROM FailedBugs";

            deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.executeUpdate();

            System.out.println("FailedBugs table cleared");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (insertStatement != null)
                {
                    insertStatement.close();
                }

                if (deleteStatement != null)
                {
                    deleteStatement.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private static String fit(String text, int width)
    {
        if (text == null)
        {
            text = "";
        }

        if (text.length() > width)
        {
            return text.substring(0, width - 3) + "...";
        }

        return String.format("%-" + width + "s", text);
    }
    public static void displayAllPermanentFailedBugs()
    {
        String query = "SELECT title, image_path, section, priority, label, times " +
                "FROM PermanentFailedBugRecords ORDER BY times DESC";

        try (Connection connection = DatabaseConnection.createConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery())
        {
            System.out.println("+----+------------------------------+------------------+----------+-----------+---------------------+");
            System.out.println("| No | Title                        | Section          | Priority | Label     | Time                |");
            System.out.println("+----+------------------------------+------------------+----------+-----------+---------------------+");

            int count = 1;

            while (rs.next())
            {
                String title = rs.getString("title");
                String section = rs.getString("section");
                String priority = rs.getString("priority");
                String label = rs.getString("label");
                String time = rs.getString("times");

                System.out.println("| "
                        + fit(String.valueOf(count), 2) + " | "
                        + fit(title, 28) + " | "
                        + fit(section, 16) + " | "
                        + fit(priority, 8) + " | "
                        + fit(label, 9) + " | "
                        + fit(time, 19) + " |");

                count++;
            }

            System.out.println("+----+------------------------------+------------------+----------+-----------+---------------------+");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] arge)
    {
        Scanner input = new Scanner(System.in);
        while (true)
        {
            System.out.println("========================PERMANENT BUG LOGS HISTORY=======================");
            System.out.println("Press 1 : View all Bugs History");
            System.out.println("Press 2 : Upload all History to Plane Board");
            System.out.println("Press 3 : Upload specific Bug to Plane Board");
            System.out.println("Press 4 : Completely delete all bugs in database");
            System.out.println("Press 0 : Exit");
            System.out.print("Please enter a number : ");
            int n = -1;
            try
            {
                n = input.nextInt();
            }
            catch (Exception e)
            {
                System.out.println("Invalid input");
                break;
            }
            if(n==1)
            {
                displayAllPermanentFailedBugs();
            }
            else if(n==2)
            {
                System.out.println("Started uploading all bugs to Plane Board...");
            }
            else if (n==3)
            {
                System.out.println("Please upload specific time stamp to upload in Plane Board");
                System.out.println("Example input format : [2026-05-23 22:09:54,2026-05-23 22:13:36]");
                String timestamps = input.nextLine();
                System.out.println("Started uploading all bugs to Plane Board...");
            }
            else if(n==4)
            {
                System.out.println("All bug records are deleted in database");
            }
            else
            {
                System.out.println("EXIT");
                break;
            }
        }
    }
}
