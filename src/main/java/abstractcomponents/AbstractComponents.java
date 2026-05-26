package abstractcomponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class AbstractComponents
{
    WebDriver driver;
    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    WebElement account;

     By logout_btn = By.xpath("//ul[@class='oxd-dropdown-menu']//li//a[text()='Logout']");
    public AbstractComponents(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public void waitForElementBY(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForElementtoBeClickable(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
       WebElement element  =  wait.until(ExpectedConditions.elementToBeClickable(by));
//       element.click();
    }
    public boolean waitForElementtoBeClickableEnabled(WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
       return wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
    }
    public boolean waitForInvisiblilityOfByElement(By by,int time)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
       return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void presenceofWaitBY(By element)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }
    public void moveMouse(WebElement element)
    {
       Actions actions = new Actions(driver);
       actions.moveToElement(element).build().perform();

    }
    public void setLogout_btn()
    {
        account.click();
        waitForElementBY(logout_btn);
        driver.findElement(logout_btn).click();
    }
}
