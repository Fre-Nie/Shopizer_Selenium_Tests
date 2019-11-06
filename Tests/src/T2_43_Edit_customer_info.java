import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class T2_43_Edit_customer_info {
    private WebDriver driver;
    private String emailAddress;
    private StringBuffer verificationErrors = new StringBuffer();

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

        //reading email from textfile from newly created customer.
        String fileName = "Created_Customer_Info";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        emailAddress =  bufferedReader.readLine();
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        //Navigates to loginpage
        driver.get("http://localhost:8080/shop/customer/customLogon.html");

        //user login
        driver.findElement(By.id("signin_userName")).sendKeys(emailAddress);
        driver.findElement(By.id("signin_password")).sendKeys("hello123");
        driver.findElement(By.id("genericLogin-button")).click();

        //Edits user info
        driver.findElement(By.linkText("Billing & shipping information")).click();
        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("company")).clear();
        driver.findElement(By.id("address")).clear();
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.xpath("//*[@id=\"billingPostalCode\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"phone\"]")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Adjöken");
        driver.findElement(By.id("company")).sendKeys("Nisses Fisk & Asfalt AB");
        driver.findElement(By.id("address")).sendKeys("Lurendrejarevägen 1337");
        driver.findElement(By.id("city")).sendKeys("Hell-sink-i");
        driver.findElement(By.id("billingPostalCode")).sendKeys("111 111");
        driver.findElement(By.id("phone")).sendKeys("(111) 111-1111");
        driver.findElement(By.id("submitAddress")).click();

        //Asserts that the changes where successful
        try {
            assertEquals("Request completed with success", driver.findElement(By.id("store.success")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }

        driver.get("http://localhost:8080/shop/customer/billing.html");
        assertEquals("Billing Address  Edit\n" +
                "Nisses Fisk & Asfalt AB\n" +
                "Lurendrejarevägen 1337\n" +
                "Hell-sink-i\n" +
                "Quebec, Canada\n" +
                "111 111\n" +
                "(111) 111-1111", driver.findElement(By.className("checkout-box")).getText());
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