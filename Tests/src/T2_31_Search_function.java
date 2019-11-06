import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class T2_31_Search_function {
    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private String url;

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
        driver.get(url);
        driver.findElement(By.id("searchField")).click();
        driver.findElement(By.id("searchField")).clear();
        driver.findElement(By.id("searchField")).sendKeys("vintage");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Contact us'])[1]/following::button[1]")).click();
        assertEquals("Vintage Bags - Shopizer demo", driver.getTitle());
        try {
            assertTrue(isElementPresent(By.xpath("//*[@id=\"productsContainer\"]/div[1]/div[1]/a")));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

}
