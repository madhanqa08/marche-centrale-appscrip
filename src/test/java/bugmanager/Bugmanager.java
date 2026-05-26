package bugmanager;

import generativeai.GeminiDescriptionService;
import model.TestCaseData;
import mysqldatabase.FailedBugRepository;
import utilities.random.Randomnumber;
import xlutilities.ExcelUtility;

import java.io.InputStream;
import java.util.List;

public class Bugmanager {


    public static void getfailedtestcase(String testcaseid, String actualfail, String path) throws Exception {
        GeminiDescriptionService service = new GeminiDescriptionService("Marche Centrale");
        FailedBugRepository failedBugRepository = new FailedBugRepository();

        InputStream is = Bugmanager.class.getClassLoader()
                .getResourceAsStream("xlsheet/signup_testcases.xlsx");

        if (is == null) {
            throw new RuntimeException("Excel file not found in src/test/resources/xlsheet/signup_testcases.xlsx");
        }


        List<TestCaseData> list =
                ExcelUtility.getTestCaseData(is, testcaseid.replaceAll("\\D+", ""));

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

        System.out.println("TEST OUTPUT : " + output);

        String dbPath = path.replace("/", "\\");
        if (!dbPath.startsWith("reports\\")) {
            dbPath = "reports\\" + dbPath;
        }

        String[] parts = output.split("\\s*\\|\\|\\s*");

        StringBuilder bugTitle = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            if (i > 0) {
                bugTitle.append(" || ");
            }
            bugTitle.append(parts[i]);
        }

        String des = parts[parts.length - 1];

        failedBugRepository.uploadFailedBug(
                Randomnumber.getGeneratedBugId(),
                String.valueOf(bugTitle),
                des,
                dbPath,
                "AI Generated Logs",
                priority,
                label
        );
    }
}