package bugmanager;
import generativeai.GeminiDescriptionService;
import model.TestCaseData;
import mysqldatabase.FailedBugRepository;
import utilities.random.Randomnumber;
import xlutilities.ExcelUtility;
import java.util.List;
public class Bugmanager
{
    public static void getfailedtestcase(String testcaseid,String actualfail,String path) throws Exception
    {
        GeminiDescriptionService service = new GeminiDescriptionService("Marche Centrale");
        FailedBugRepository failedBugRepository = new FailedBugRepository();
        String filePath = System.getProperty("user.dir") + "/xlsheet/signup_testcases.xlsx";
        List<TestCaseData> list =
                ExcelUtility.getTestCaseData(filePath, testcaseid.replaceAll("\\D+", ""));

        TestCaseData data = list.get(0);
        String testCaseId = data.getTcId();
        String module = data.getModule();
        String title = data.getTestCaseTitle();
        String expectedResult = data.getExpectedResult();
        String priority = data.getPriority();
        String label = data.getLabel();


        String output = service.generateDescription(
                testCaseId,
                module,
                title,
                expectedResult,
                priority,
                label,
                actualfail
        );
        System.out.println("TEST OUTPUT : "+output);
        String dbPath = path.replace("/", "\\");

        if (!dbPath.startsWith("reports\\"))
        {
            dbPath = "reports\\" + dbPath;
        }
        String[] parts = output.split("\\s*\\|\\|\\s*");

        StringBuilder bugTitle = new StringBuilder();

// Combine all parts except last
        for (int i = 0; i < parts.length - 1; i++) {
            if (i > 0) {
                bugTitle.append(" || ");
            }
            bugTitle.append(parts[i]);
        }
// Last part = description
        String des = parts[parts.length - 1];

//------------------\reports\screenshots\testcase032_1779563427173.png
        failedBugRepository.uploadFailedBug(Randomnumber.getGeneratedBugId(), String.valueOf(bugTitle),des,dbPath,"AI Generated Logs",priority,label);
    }

}
