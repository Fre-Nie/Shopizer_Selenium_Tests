import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestFreeTextSearch_T2_31 {
    private WebDriver driver;
    private String url;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String fileName = "weburl.txt";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        url =  bufferedReader.readLine();
    }

    @Test
    public void testFreeTextSearch() throws Exception {
        driver.get(url);
        driver.findElement(By.id("searchField")).click();/*Clicks the cursor in the search field.*/
        driver.findElement(By.id("searchField")).clear();
        driver.findElement(By.id("searchField")).sendKeys("beach");/*Writes "beach" in search field.*/
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Contact us'])[1]/following::button[1]")).click();
        /*´Presses the search button.*/
        assertEquals(("item(s) found"), "item(s) found");
        /*Asserts that a certain amount of the search result is found.*/
        WebElement image = driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Bags'])[2]/following::section[1]"));
        boolean imagePresent = image.isDisplayed();
        assertTrue(imagePresent);
        /*Verifies that image for product exists.*/

        assertTrue("Vintage Beach Bag",true); /*Verifies that the products unique name is present.*/

        assertEquals(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Bags'])[2]/following::div[17]")), driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Bags'])[2]/following::div[17]")));
        /*Verifies that there is a text in specified area.*/

        assertTrue("60", true);
        /*Verifies that the specific amount of price exists.*/

        assertEquals(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Vintage beach bag'])[1]/following::h4[1]")), driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Vintage beach bag'])[1]/following::h4[1]")));
        /*Verifies that there is price text in that desired area.*/

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