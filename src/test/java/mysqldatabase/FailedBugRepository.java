package mysqldatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class FailedBugRepository
{
    public void uploadFailedBug(String recordNo,
                                String title,
                                String des,
                                String imagePath,
                                String section,
                                String priority,
                                String label)
    {
        PreparedStatement preparedStatement = null;

        try
        {
            Connection connection =
                    DatabaseConnection.createConnection();

            String query =
                    "INSERT INTO FailedBugs " +
                            "(record_no, title, descriptions, image_path, section, priority, label) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";

            preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setString(1, recordNo);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, des);
            preparedStatement.setString(4, imagePath);
            preparedStatement.setString(5, section);
            preparedStatement.setString(6, priority);
            preparedStatement.setString(7, label);

            int rows = preparedStatement.executeUpdate();

            if(rows > 0)
            {
                System.out.println("Bug uploaded successfully");
            }
            else
            {
                System.out.println("Bug upload failed");
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
    }
}