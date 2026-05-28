//package plane;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.Transferable;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.time.Duration;
//public class Planes
//{
//
//    WebDriver driver;
//    WebDriverWait wait;
//
//    // ─── Login Elements ───────────────────────────────────────────────────────
//    @FindBy(xpath = "//input[@id='email']")
//    WebElement emailField;
//
//    @FindBy(xpath = "//input[@id='password']")
//    WebElement passwordField;
//
//    @FindBy(xpath = "//button[normalize-space()='Continue']")
//    WebElement continueButton;
//
//    // ─── Issue Elements ───────────────────────────────────────────────────────
//    @FindBy(xpath = "//div[text()='Add work item']")
//    WebElement addWorkItemBtn;
//
//    @FindBy(xpath = "//input[@placeholder='Title']")
//    WebElement titleField;
//
//    @FindBy(id = "editor-container-issue-modal-editor")
//    WebElement descriptionContainer;
//
//    @FindBy(css = "div.tiptap.ProseMirror[contenteditable='true']")
//    WebElement descriptionEditable;
//
//    @FindBy(xpath = "//span[text()='Backlog']")
//    WebElement backlogBtn;
//
//    @FindBy(xpath = "//input[contains(@placeholder,'Search')]")
//    WebElement searchField;
//
//    @FindBy(xpath = "//li[contains(@role,'option')]")
//    WebElement firstOption;
//
//    @FindBy(xpath = "//span[text()='None']")
//    WebElement noneBtn;
//
//    @FindBy(xpath = "//span[contains(@class,'flex-grow truncate leading-5') and text()='Assignees']")
//    WebElement assigneesBtn;
//
//    @FindBy(xpath = "//span[normalize-space()='Labels']")
//    WebElement labelsBtn;
//
//    @FindBy(xpath = "//span[normalize-space()='Start date']")
//    WebElement startDateBtn;
//
//    @FindBy(xpath = "//td[contains(@class,'rdp-day rdp-today')]")
//    WebElement todayDate;
//
//    @FindBy(xpath = "//button[normalize-space()='Save']")
//    WebElement saveBtn;
//
//    // ─── URL ──────────────────────────────────────────────────────────────────
//    public void loadUrl(String url) {
//        driver.get(url);
//    }
//
//    // ─── Login ────────────────────────────────────────────────────────────────
//    public void login(String email, String password) {
//        wait.until(ExpectedConditions.elementToBeClickable(emailField));
//        emailField.sendKeys(email);
//
//        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
//        continueButton.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
//        passwordField.sendKeys(password);
//
//        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
//        continueButton.click();
//    }
//
//    // ─── Load image into clipboard ────────────────────────────────────────────
//    public void loadImageToClipboard(String imagePath) throws Exception {
//
//        File file = new File(imagePath);
//        if (!file.exists()) {
//            throw new RuntimeException("Image not found at path: " + imagePath);
//        }
//
//        final BufferedImage bufferedImage = ImageIO.read(file);
//
//        if (bufferedImage == null) {
//            throw new RuntimeException("Could not read image — unsupported format: " + imagePath);
//        }
//
//        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
//                new Transferable() {
//                    public DataFlavor[] getTransferDataFlavors() {
//                        return new DataFlavor[]{ DataFlavor.imageFlavor };
//                    }
//                    public boolean isDataFlavorSupported(DataFlavor flavor) {
//                        return DataFlavor.imageFlavor.equals(flavor);
//                    }
//                    public Object getTransferData(DataFlavor flavor)
//                            throws UnsupportedFlavorException {
//                        if (DataFlavor.imageFlavor.equals(flavor)) return bufferedImage;
//                        throw new UnsupportedFlavorException(flavor);
//                    }
//                }, null
//        );
//        System.out.println("Image loaded to clipboard: " + imagePath);
//    }
//
//    // ─── Paste image into description ─────────────────────────────────────────
//    public void pasteImageIntoDescription(String imagePath) throws Exception
//    {
//
//        loadImageToClipboard(imagePath);
//
//        wait.until(ExpectedConditions.elementToBeClickable(descriptionContainer));
//        descriptionContainer.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(descriptionEditable));
//        descriptionEditable.click();
//
//        wait.until(ExpectedConditions.visibilityOf(descriptionEditable));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].focus();", descriptionEditable);
//
//        descriptionEditable.sendKeys(Keys.CONTROL + "v");
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.cssSelector("div.tiptap.ProseMirror img")
//        ));
//        System.out.println("Image pasted into description.");
//    }
//
//    // ─── Create Issue ─────────────────────────────────────────────────────────
//    public void createIssue(String title, String description,
//                            String imagePath,
//                            String section, String priority,
//                            String label) throws Exception {
//
//        wait.until(ExpectedConditions.elementToBeClickable(addWorkItemBtn));
//        addWorkItemBtn.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(titleField));
//        titleField.sendKeys(title);
//
//        wait.until(ExpectedConditions.elementToBeClickable(descriptionContainer));
//        descriptionContainer.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(descriptionEditable));
//        descriptionEditable.click();
//        descriptionEditable.sendKeys(description);
//
//        pasteImageIntoDescription(imagePath);
//
//        wait.until(ExpectedConditions.elementToBeClickable(backlogBtn));
//        backlogBtn.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(searchField));
//        searchField.sendKeys(section);
//
//        wait.until(ExpectedConditions.elementToBeClickable(firstOption));
//        firstOption.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(noneBtn));
//        noneBtn.click();
//
//        WebElement priorityOption = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//span[@class='flex-grow truncate' and text()='" + priority + "']")
//        ));
//        priorityOption.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(assigneesBtn));
//        assigneesBtn.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(labelsBtn));
//        labelsBtn.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(searchField));
//        searchField.sendKeys(label);
//
//        wait.until(ExpectedConditions.elementToBeClickable(firstOption));
//        firstOption.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(startDateBtn));
//        startDateBtn.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(todayDate));
//        todayDate.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
//        saveBtn.click();
//    }
//
//    // ─── Assertions ───────────────────────────────────────────────────────────
//    public void verifyCardCreated(String title, String description,
//                                  String section, String priority,
//                                  String label) {
//
//        System.out.println("\n========== STARTING ASSERTIONS ==========");
//
//        // ── 1. Verify success toast popup appears after save ─────────────────
//        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[contains(text(),'created') or " +
//                        "contains(text(),'success') or " +
//                        "contains(text(),'added')]")
//        ));
//        Assert.assertTrue(
//                toast.isDisplayed(),
//                "FAILED — Toast popup did not appear after saving"
//        );
//        System.out.println("PASS 1 — Toast popup appeared: " + toast.getText());
//
//        // ── 2. Verify card appears in the board/list with correct title ──────
//        WebElement cardInList = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[normalize-space()='" + title + "']")
//        ));
//        Assert.assertTrue(
//                cardInList.isDisplayed(),
//                "FAILED — Card with title '" + title + "' not visible in the list"
//        );
//        Assert.assertEquals(
//                cardInList.getText().trim(), title,
//                "FAILED — Card title mismatch in list. Expected: " + title + " | Got: " + cardInList.getText()
//        );
//        System.out.println("PASS 2 — Card visible in list with title: " + cardInList.getText());
//
//        // ── 3. Open the card by clicking the title ───────────────────────────
//        cardInList.click();
//        System.out.println("INFO  — Card clicked, opening detail view...");
//
//        // ── 4. Verify title inside the opened card detail ────────────────────
//        WebElement detailTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[@value='" + title + "'] | " +
//                        "//*[contains(@class,'title') and normalize-space()='" + title + "']")
//        ));
//        Assert.assertEquals(
//                detailTitle.getText().trim().isEmpty()
//                        ? detailTitle.getAttribute("value").trim()
//                        : detailTitle.getText().trim(),
//                title,
//                "FAILED — Title inside card detail mismatch. Expected: " + title
//        );
//        System.out.println("PASS 3 — Title inside card verified: " + title);
//
//        // ── 5. Verify description text inside the card ───────────────────────
//        WebElement detailDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector("div.tiptap.ProseMirror, div.ProseMirror")
//        ));
//        Assert.assertTrue(
//                detailDescription.getText().contains(description),
//                "FAILED — Description mismatch. Expected to contain: '" + description +
//                        "' | Got: '" + detailDescription.getText() + "'"
//        );
//        System.out.println("PASS 4 — Description verified: " + detailDescription.getText());
//
//        // ── 6. Verify image is present inside the description ────────────────
//        WebElement descImage = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.cssSelector("div.tiptap.ProseMirror img, div.ProseMirror img")
//        ));
//        Assert.assertTrue(
//                descImage.isDisplayed(),
//                "FAILED — Image not found inside the description"
//        );
//        // Also verify the image has a valid src (not empty)
//        String imgSrc = descImage.getAttribute("src");
//        Assert.assertNotNull(imgSrc, "FAILED — Image src is null");
//        Assert.assertFalse(imgSrc.isEmpty(), "FAILED — Image src is empty");
//        System.out.println("PASS 5 — Image found in description with src: " +
//                imgSrc.substring(0, Math.min(imgSrc.length(), 60)) + "...");
//
//        // ── 7. Verify section is shown on the card ───────────────────────────
//        WebElement detailSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[normalize-space()='" + section + "']")
//        ));
//        Assert.assertTrue(
//                detailSection.isDisplayed(),
//                "FAILED — Section '" + section + "' not found on card"
//        );
//        Assert.assertEquals(
//                detailSection.getText().trim(), section,
//                "FAILED — Section mismatch. Expected: " + section + " | Got: " + detailSection.getText()
//        );
//        System.out.println("PASS 6 — Section verified: " + detailSection.getText());
//
//        // ── 8. Verify priority is shown on the card ──────────────────────────
//        WebElement detailPriority = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[normalize-space()='" + priority + "']")
//        ));
//        Assert.assertTrue(
//                detailPriority.isDisplayed(),
//                "FAILED — Priority '" + priority + "' not found on card"
//        );
//        Assert.assertEquals(
//                detailPriority.getText().trim(), priority,
//                "FAILED — Priority mismatch. Expected: " + priority + " | Got: " + detailPriority.getText()
//        );
//        System.out.println("PASS 7 — Priority verified: " + detailPriority.getText());
//
//        // ── 9. Verify label is shown on the card ─────────────────────────────
//        WebElement detailLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[normalize-space()='" + label + "']")
//        ));
//        Assert.assertTrue(
//                detailLabel.isDisplayed(),
//                "FAILED — Label '" + label + "' not found on card"
//        );
//        Assert.assertEquals(
//                detailLabel.getText().trim(), label,
//                "FAILED — Label mismatch. Expected: " + label + " | Got: " + detailLabel.getText()
//        );
//        System.out.println("PASS 8 — Label verified: " + detailLabel.getText());
//
//        // ── 10. Verify start date is set and not empty ───────────────────────
//        WebElement detailStartDate = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[contains(@class,'start') or contains(@class,'date')]" +
//                        "[not(normalize-space()='Start date')]")
//        ));
//        Assert.assertTrue(
//                detailStartDate.isDisplayed(),
//                "FAILED — Start date element not visible on card"
//        );
//        Assert.assertFalse(
//                detailStartDate.getText().trim().isEmpty(),
//                "FAILED — Start date is empty on card"
//        );
//        System.out.println("PASS 9 — Start date verified: " + detailStartDate.getText());
//
//        // ── 11. Verify status shows Backlog ──────────────────────────────────
//        WebElement detailStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[normalize-space()='Backlog']")
//        ));
//        Assert.assertTrue(
//                detailStatus.isDisplayed(),
//                "FAILED — Status 'Backlog' not found on card"
//        );
//        Assert.assertEquals(
//                detailStatus.getText().trim(), "Backlog",
//                "FAILED — Status mismatch. Expected: Backlog | Got: " + detailStatus.getText()
//        );
//        System.out.println("PASS 10 — Status verified: " + detailStatus.getText());
//
//        System.out.println("========== ALL 10 ASSERTIONS PASSED ==========\n");
//    }
//
//    // ─── TestNG Lifecycle ─────────────────────────────────────────────────────
//    @BeforeTest
//    public void setup()
//    {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        PageFactory.initElements(driver, this);
//        loadUrl("https://pm.appscrip.co/new-kommerce/projects/815e4154-2125-4c64-863c-962acad896e1/issues/");
//    }
//    @Test
//    public void createIssueTest() throws Exception
//    {
//
//        // ── Test data — change only here ─────────────────────────────────────
//        String title       = "AI Generated Logs";
//        String description = "Login Failed";
//        String imagePath   = "reports\\screenshots\\adminCreatesEmployee_1765888089670.png";
//        String section     = "AI Generated Logs";
//        String priority    = "Urgent";
//        String label       = "Login";
//
//        // ── Step 1: Login ─────────────────────────────────────────────────────
//        login("madhan.k@appscrip.co", "Universe1234@");
//
//        // ── Step 2: Create the card ───────────────────────────────────────────
//        createIssue(title, description, imagePath, section, priority, label);
//
//        // ── Step 3: Verify every field on the created card ────────────────────
//        verifyCardCreated(title, description, section, priority, label);
//    }
//
//    @AfterTest
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
