package plane;
import Testcomponents.PlaneBase;
import model.FailedBugDTO;
import mysqldatabase.DatabaseConnection;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class PlaneLogging extends PlaneBase
{

    public void createIssueTest() throws Exception
    {
        planePage.login("madhan.k@appscrip.co", "Universe1234@");

        List<FailedBugDTO> failedBugs = getFailedBugsFromDatabase();

        for (FailedBugDTO bug : failedBugs)
        {
//            String description = "Login Failed"; // table has no description column

            planePage.createIssue(
                    bug.getTitle(),
                    bug.getDescriptions(),
                    bug.getImagePath(),
                    bug.getSection(),
                    bug.getPriority(),
                    bug.getLabel()
            );

            Thread.sleep(1000);
        }
    }

    private List<FailedBugDTO> getFailedBugsFromDatabase()
    {
        List<FailedBugDTO> failedBugs = new ArrayList<>();

        String query =
                "SELECT record_no, title, descriptions, image_path, section, priority, label " +
                        "FROM FailedBugs ORDER BY times ASC";

        try
        {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                FailedBugDTO bug = new FailedBugDTO();

                bug.setRecordNo(rs.getString("record_no"));
                bug.setTitle(rs.getString("title"));
                bug.setDescriptions(rs.getString("descriptions"));
                bug.setImagePath(rs.getString("image_path"));
                bug.setSection(rs.getString("section"));
                bug.setPriority(rs.getString("priority"));
                bug.setLabel(rs.getString("label"));

                failedBugs.add(bug);
            }

            rs.close();
            ps.close();
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return failedBugs;
    }
}