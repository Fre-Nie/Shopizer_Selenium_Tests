import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class T2_32_Add_to_cart {
    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private String url;
    private String priceCheck;


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
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

        WebElement element = driver.findElement(By.xpath("//*[@id=\"featuredItemsContainer\"]/div[2]/div[2]/div/div/a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();

        driver.findElement(By.xpath("//*[@id=\"featuredItemsContainer\"]/div[2]/div[2]/div/div/a")).click();

        //Hovers the mouse over the cart icon.
        WebElement checkout = driver.findElement(By.xpath("//*[@id=\"miniCartSummary\"]"));
        Actions builder = new Actions(driver);
        builder.moveToElement(checkout).click().build().perform();

        //Waits for cart to be visible.
        Thread.sleep(1000);

        //Asserts that the price, name and that image is correctly displayed in the cart.
        priceCheck = driver.findElement(By.xpath("//*[@id=\"miniCartDetails\"]/li[1]/div[2]/span")).getText();
        assertTrue(priceCheck.contains("78"));

        assertEquals("1 X VINTAGE LAPTOP BAG", driver.findElement(By.xpath("//*[@id=\"miniCartDetails\"]/li[1]/div[2]/h4/a")).getText());
        WebElement image = driver.findElement(By.xpath("//*[@id=\"miniCartDetails\"]/li[1]/div[1]/img"));
        boolean imagePresent = image.isDisplayed();
        assertTrue(imagePresent);

        //Clicks link to checkout.
        driver.findElement(By.xpath("//*[@id=\"miniCartDetails\"]/li[4]/a")).click();

        //Verifies content of the specific item in the list of items in the cart.
        priceCheck = driver.findElement(By.xpath("//*[@id=\"mainCartTable\"]/tbody/tr/td[4]/strong")).getText();
        assertTrue(priceCheck.contains("78"));

        priceCheck = driver.findElement(By.xpath("//*[@id=\"mainCartTable\"]/tbody/tr/td[3]/strong")).getText();
        assertTrue(priceCheck.contains("78"));

        assertEquals("Vintage laptop bag", driver.findElement(By.xpath("//*[@id=\"mainCartTable\"]/tbody/tr/td[1]/div/div[2]/span/strong")).getText());

        WebElement image2 = driver.findElement(By.xpath("//*[@id=\"mainCartTable\"]/tbody/tr/td[1]/div/div[1]/img"));
        boolean imagePresent2 = image2.isDisplayed();
        assertTrue(imagePresent2);


        //Verifies the contents of the total checkout cost on the checkout page,
        priceCheck = driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Total'])[2]/following::span[1]")).getText();
        assertTrue(priceCheck.contains("78"));
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
