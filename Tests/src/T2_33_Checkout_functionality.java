import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class T2_33_Checkout_functionality {
    private WebDriver driver;
    private String url;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        //sets system path for chromedriver if user is running Linux-based OS.
        String operatingSystem = System.getProperty("os.name");
        if (operatingSystem.equals("Linux")){
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //reading url from a textfile
        String fileName = "weburl.txt";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        url =  bufferedReader.readLine();
    }

    @Test
    public void testUntitledTestCase() throws Exception {

        //Navigates to Shopizer webpage and adds a Vintage Courier bag to the cart.
        driver.get(url);

        //Scrolls down to add to cart button
        WebElement element = driver.findElement(By.xpath("//*[@id=\"featuredItemsContainer\"]/div[2]/div[2]/div/div/a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();

        driver.findElement(By.xpath("//*[@id=\"featuredItemsContainer\"]/div[2]/div[2]/div/div/a")).click();

        //Hovers the mouse over the cart icon.
        WebElement checkout = driver.findElement(By.xpath("//*[@id=\"miniCartSummary\"]"));
        Actions builder = new Actions(driver);
        builder.moveToElement(checkout).click().build().perform();

        //Waits until cart is visible.
        Thread.sleep(1000);

        //Clicks link to checkout.
        driver.findElement(By.xpath("//*[@id=\"miniCartDetails\"]/li[4]/a")).click();

        //Proceeds to checkout.
        driver.findElement(By.xpath("/html/body/div[5]/div/div/div/div/div/div[2]/div[2]/div[2]/a")).click();

       //Fills in the contact details.
        driver.findElement(By.id("customer.firstName")).sendKeys("Andreas");
        driver.findElement(By.id("customer.lastName")).sendKeys("Example");
        driver.findElement(By.id("customer.billing.address")).sendKeys("Example Street");
        driver.findElement(By.id("customer.billing.city")).sendKeys("Gothenburg");
        driver.findElement(By.id("customer.billing.country")).click();
        new Select(driver.findElement(By.id("customer.billing.country"))).selectByVisibleText("India");
        driver.findElement(By.xpath("//*[@id=\"billingStateProvince\"]")).sendKeys("Example");
        driver.findElement(By.xpath("//*[@id=\"billingPostalCode\"]")).sendKeys("12345");
        driver.findElement(By.id("customer.emailAddress")).sendKeys("12345@123.123");
        driver.findElement(By.id("customer.billing.phone")).sendKeys("12345678");

        //Submits the order
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"submitOrder\"]")).click();

        //Asserts that the order has succesfully been submitted.
        assertEquals("Thank you for ordering from Default store.", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Order completed'])[1]/following::p[1]")).getText());

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}

