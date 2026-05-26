package Testcomponents;
import bugmanager.Bugmanager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.reports.ExtendReports;
import java.io.IOException;
public class Listenersclass extends Screenshoot implements ITestListener
{

    ExtentReports reports = ExtendReports.getreport();
    ThreadLocal<ExtentTest> thread = new ThreadLocal<>();

    @Override
   public void onTestStart(ITestResult result)
   {
        ExtentTest test = reports.createTest(result.getMethod().getMethodName());
        thread.set(test);
   }
    @Override
    public void onTestSuccess(ITestResult result)
    {
        thread.get().log(Status.PASS,result.getMethod().getMethodName()+" Passed✅");
    }


    @Override
    public void onTestFailure(ITestResult result)
    {
        thread.get().log(Status.FAIL,result.getMethod().getMethodName()+" Failed❌");
        thread.get().log(Status.FAIL,result.getThrowable());

        Throwable throwable = result.getThrowable();
        String actualError = "";
        if (throwable != null)
        {
            actualError = throwable.toString();
        }
        try
        {
            WebDriver driver = ((BaseTest) result.getInstance()).getDriver();
            String path = takeScreenshoot(result.getMethod().getMethodName(),driver);
            thread.get().addScreenCaptureFromPath(path);
            Bugmanager.getfailedtestcase(result.getMethod().getMethodName(),actualError,path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFinish(ITestContext context)
    {
       reports.flush();
    }
}
