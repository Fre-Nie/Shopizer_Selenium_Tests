import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class T2_26_Detailed_product_information {
    private WebDriver driver;
    private String url;
    private StringBuffer verificationErrors = new StringBuffer();
    private String priceCheck;

    @Before
    public void setUp() throws Exception {
        String operatingSystem = System.getProperty("os.name");

        if (operatingSystem.equals("Windows")){
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }
        if (operatingSystem.equals("Unix")){
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }

        //sets systempath for chromedriver if user is running Linux-based OS.
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
    public void testBeachBags() throws Exception {
        driver.get(url);
        driver.findElement(By.xpath("//*[@id=\"main_h\"]/div/div/div/div/nav/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//body")).click();

        //assert that an image is visible
        WebElement image = driver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div[2]/div[1]/a/img"));
        boolean imagePresent = image.isDisplayed();
        assertTrue(imagePresent);


        //assert that tittle is visible
        assertEquals("Vintage Bags - Beach bags", driver.getTitle());

        //assert that the title is visible
        assertEquals("Vintage Bags - Beach bags", driver.getTitle());

        //assert that the price is visible
        priceCheck = driver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div[2]/div[2]/h4/span")).getText();
        assertTrue(priceCheck.contains("48"));
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
