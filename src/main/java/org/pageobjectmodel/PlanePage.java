package org.pageobjectmodel;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;
public class PlanePage
{
    WebDriver driver;
    WebDriverWait wait;

    public PlanePage(WebDriver driver, WebDriverWait wait)
    {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='email']")
    WebElement emailField;

    @FindBy(xpath = "//input[@id='password']")
    WebElement passwordField;

    @FindBy(xpath = "//button[normalize-space()='Continue']")
    WebElement continueButton;

    @FindBy(xpath = "//div[text()='Add work item']")
    WebElement addWorkItemBtn;

    @FindBy(xpath = "//input[@placeholder='Title']")
    WebElement titleField;

    @FindBy(id = "editor-container-issue-modal-editor")
    WebElement descriptionContainer;

    @FindBy(css = "div.tiptap.ProseMirror[contenteditable='true']")
    WebElement descriptionEditable;

    @FindBy(xpath = "//span[text()='Backlog']")
    WebElement backlogBtn;

    @FindBy(xpath = "//input[contains(@placeholder,'Search')]")
    WebElement searchField;

    @FindBy(xpath = "//li[contains(@role,'option')]")
    WebElement firstOption;

    @FindBy(xpath = "//span[text()='None']")
    WebElement noneBtn;

    @FindBy(xpath = "//span[contains(@class,'flex-grow truncate leading-5') and text()='Assignees']")
    WebElement assigneesBtn;

    @FindBy(xpath = "//span[normalize-space()='Labels']")
    WebElement labelsBtn;

    @FindBy(xpath = "//span[normalize-space()='Start date']")
    WebElement startDateBtn;

    @FindBy(xpath = "//td[contains(@class,'rdp-day rdp-today')]")
    WebElement todayDate;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    WebElement saveBtn;

    public void loadUrl(String url)
    {
        driver.get(url);
    }

    public void login(String email, String password)
    {
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.sendKeys(email);

        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }

    public void createIssue(String title, String description,
                            String imagePath, String section,
                            String priority, String label) throws Exception
    {
        wait.until(ExpectedConditions.elementToBeClickable(addWorkItemBtn));
        addWorkItemBtn.click();

        wait.until(ExpectedConditions.elementToBeClickable(titleField));
        titleField.sendKeys(title);

        wait.until(ExpectedConditions.elementToBeClickable(descriptionContainer));
        descriptionContainer.click();

        wait.until(ExpectedConditions.elementToBeClickable(descriptionEditable));
        descriptionEditable.click();
        descriptionEditable.sendKeys(description);

        pasteImageIntoDescription(imagePath);

        wait.until(ExpectedConditions.elementToBeClickable(backlogBtn));
        backlogBtn.click();

        wait.until(ExpectedConditions.elementToBeClickable(searchField));
        searchField.sendKeys(section);

        wait.until(ExpectedConditions.elementToBeClickable(firstOption));
        firstOption.click();

        wait.until(ExpectedConditions.elementToBeClickable(noneBtn));
        noneBtn.click();

        WebElement priorityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='flex-grow truncate' and text()='" + priority + "']")
        ));
        priorityOption.click();

        wait.until(ExpectedConditions.elementToBeClickable(assigneesBtn));
        assigneesBtn.click();

        wait.until(ExpectedConditions.elementToBeClickable(labelsBtn));
        labelsBtn.click();

        wait.until(ExpectedConditions.elementToBeClickable(searchField));
        searchField.sendKeys(label);

        wait.until(ExpectedConditions.elementToBeClickable(firstOption));
        firstOption.click();

        wait.until(ExpectedConditions.elementToBeClickable(startDateBtn));
        startDateBtn.click();

        wait.until(ExpectedConditions.elementToBeClickable(todayDate));
        todayDate.click();

        wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
        Thread.sleep(10000);
        saveBtn.click();
    }

    public void pasteImageIntoDescription(String imagePath) throws Exception
    {
        loadImageToClipboard(imagePath);

        wait.until(ExpectedConditions.elementToBeClickable(descriptionContainer));
        descriptionContainer.click();

        wait.until(ExpectedConditions.elementToBeClickable(descriptionEditable));
        descriptionEditable.click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", descriptionEditable);

        descriptionEditable.sendKeys(Keys.CONTROL + "v");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.tiptap.ProseMirror img")
        ));

        System.out.println("Image pasted into description.");
    }

    public void loadImageToClipboard(String imagePath) throws Exception
    {
        File file = new File(imagePath);
        if (!file.exists())
        {
            throw new RuntimeException("Image not found at path: " + imagePath);
        }

        BufferedImage bufferedImage = ImageIO.read(file);
        if (bufferedImage == null)
        {
            throw new RuntimeException("Could not read image — unsupported format: " + imagePath);
        }

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new Transferable()
                {
                    public DataFlavor[] getTransferDataFlavors()
                    {
                        return new DataFlavor[]{DataFlavor.imageFlavor};
                    }

                    public boolean isDataFlavorSupported(DataFlavor flavor)
                    {
                        return DataFlavor.imageFlavor.equals(flavor);
                    }

                    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
                    {
                        if (DataFlavor.imageFlavor.equals(flavor)) return bufferedImage;
                        throw new UnsupportedFlavorException(flavor);
                    }
                },
                null
        );

        System.out.println("Image loaded to clipboard: " + imagePath);
    }
}