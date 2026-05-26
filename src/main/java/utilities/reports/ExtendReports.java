package utilities.reports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtendReports
{
    public static ExtentReports getreport()
    {
        String path = System.getProperty("user.dir")+"//reports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Madhan kumar");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("OrangeHrm Test report");
        ExtentReports extendReports = new ExtentReports();
        extendReports.attachReporter(reporter);
        extendReports.setSystemInfo("Tester","Madhan kumar B");
        return extendReports;
    }
}
