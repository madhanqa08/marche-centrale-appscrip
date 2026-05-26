package Testcomponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobjectmodel.PlanePage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public class PlaneBase
{
    WebDriver driver;
    WebDriverWait wait;
    public PlanePage planePage;

    @BeforeTest
    public void setup()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        planePage = new PlanePage(driver, wait);

        planePage.loadUrl("https://pm.appscrip.co/new-kommerce/projects/815e4154-2125-4c64-863c-962acad896e1/issues/");
    }

    @AfterTest
    public void teardown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }
}