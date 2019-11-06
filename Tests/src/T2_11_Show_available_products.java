import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class T2_11_Show_available_products {
    private WebDriver driver;
    private String url;
    private StringBuffer verificationErrors = new StringBuffer();
    private String priceCheck;


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        //sets system path for chromedriver if user is running Linux-based OS.
        String operatingSystem = System.getProperty("os.name");
        if (operatingSystem.equals("Linux")){
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }
        //constructs new ChromeDriver with implicit 30 second wait
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String fileName = "weburl.txt";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        url =  bufferedReader.readLine();
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        //navigates to shop
        driver.get(url);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Featured items'])[1]/following::a[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Bags'])[2]/following::h3[1]")).click();

        //assert that product name is visible
        assertEquals("Chic vintage DeVille", driver.getTitle());

        //assert that price is displayed
        priceCheck = driver.findElement(By.xpath("//*[@id=\"productPrice\"]/span/span")).getText();
        assertTrue(priceCheck.contains("60"));

        //assert that image is present
        WebElement image = driver.findElement(By.xpath("//*[@id=\"view1\"]/a/img"));
        boolean imagePresent = image.isDisplayed();
        assertTrue(imagePresent);

        //assert that Product Description is present
        assertEquals("Chic vintage DeVille\n" +
                "Weight :\n" +
                "1   Pounds\n" +
                "Height :\n" +
                "17   Inches\n" +
                "Width :\n" +
                "28   Inches\n" +
                "Length :\n" +
                "4   Inches",driver.findElement(By.xpath("(.//*[normalize-space(text())" +
                " and normalize-space(.)='Customer review(s)'])[1]/following::div[3]")).getText());

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
