package Testcomponents;
import mysqldatabase.FailedBugHistoryManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.pageobjectmodel.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import plane.PlaneLogging;
import utilities.CredentialsReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
public class BaseTest
{
    static
    {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder")
                .setLevel(Level.OFF);

        Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver")
                .setLevel(Level.OFF);

        Logger root = Logger.getLogger("");

        root.setLevel(Level.OFF);

        for (var handler : root.getHandlers())
        {
            handler.setLevel(Level.OFF);
        }
    }

    public WebDriver driver;
    ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    FailedBugHistoryManager failedBugHistoryManager = new FailedBugHistoryManager();
    public LoginPage loginPage;
    public WebDriver getDriver()
    {
        return tlDriver.get();
    }
    public WebDriver initialize() throws IOException
    {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//GlobalData.properties");
        properties.load(inputStream);
        String browser = System.getProperty("browser") != null
                ? System.getProperty("browser")
                : properties.getProperty("browser");
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--force-device-scale-factor=1.1");
        options.addArguments("--guest");

        if(browser.equalsIgnoreCase("chrome"))
        {
            driver = new ChromeDriver(options);
        }
        else if(browser.equalsIgnoreCase("edge"))
        {
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        return driver;
    }


    @BeforeMethod(alwaysRun = true)
    public LoginPage launchApplication() throws IOException
    {
        System.out.println();
        driver = initialize();
        tlDriver.set(driver);
        loginPage = new LoginPage(driver);
        loginPage.load_url(CredentialsReader.get("url"));
        return loginPage;
    }

    @BeforeSuite
    public void shareHistorytoPermanentdatabase()
    {
        System.out.println("Please wait, creating new database for you bug logs...");
        failedBugHistoryManager.moveFailedBugsToPermanentTable();
    }

    @AfterSuite
    public void end() throws Exception {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                BUG REPORT                    ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf ("║ Total Bugs Found : %-25s ║%n",
                failedBugHistoryManager.getFailedBugCount());
        System.out.println("╚══════════════════════════════════════════════╝");
        PlaneLogging obj = new PlaneLogging();
        obj.createIssueTest();

    }
    @AfterMethod
    public void quit()
    {
        driver.quit();
    }
}
