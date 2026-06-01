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
import utilities.CredentialsReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {

    static {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
        Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(Level.OFF);

        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);

        for (var handler : root.getHandlers()) {
            handler.setLevel(Level.OFF);
        }
    }

    private final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private final FailedBugHistoryManager failedBugHistoryManager = new FailedBugHistoryManager();
    public LoginPage loginPage;

    public WebDriver getDriver() {
        return tlDriver.get();
    }

    public void setDriver(WebDriver driver) {
        tlDriver.set(driver);
    }

    public WebDriver initialize() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(
                System.getProperty("user.dir") + "//src//main//resources//GlobalData.properties")) {
            properties.load(inputStream);
        }

        String browser = System.getProperty("browser") != null
                ? System.getProperty("browser")
                : properties.getProperty("browser");

        WebDriver driver;

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--force-device-scale-factor=1.1");
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public LoginPage launchApplication() throws IOException {
        WebDriver driver = initialize();
        setDriver(driver);

        loginPage = new LoginPage(getDriver());
        loginPage.load_url(CredentialsReader.get("url"));
        return loginPage;
    }

    @BeforeSuite
    public void shareHistorytoPermanentdatabase() {
        System.out.println("Please wait, creating new database for you bug logs...");
        failedBugHistoryManager.moveFailedBugsToPermanentTable();
    }

    @AfterSuite
    public void end() throws Exception {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                BUG REPORT                    ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf("║ Total Bugs Found : %-25s ║%n",
                failedBugHistoryManager.getFailedBugCount());
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
        }
    }
}