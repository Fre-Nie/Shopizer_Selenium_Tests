import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class T2_39_Register_new_customer {
    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private String url;
    private WebDriverWait wait;
    private String emailAddress;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        //sets system path for chromedriver if user is running Linux-based OS.
        String operatingSystem = System.getProperty("os.name");
        if (operatingSystem.equals("Linux")){
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        //reading url to shopizer and adminpage from a textfile
        String fileName = "weburl.txt";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        url =  bufferedReader.readLine();

        //generating random numbers for emailaddresses
        emailAddress = "username"+System.nanoTime()+"@gmail.com";

        //Saving email in file
        FileWriter fw=new FileWriter("Created_Customer_Info");
        fw.write(emailAddress);
        fw.close();

    }


    @Test
    public void testUntitledTestCase() throws Exception {
        //Navigates to the register account page.
        driver.get(url);
        driver.findElement(By.linkText("My Account")).click();
        driver.findElement(By.id("registerLink")).click();

        //Inputs customer data
        driver.findElement(By.id("firstName")).sendKeys("Team2");
        driver.findElement(By.id("lastName")).sendKeys("VG");
        driver.findElement(By.id("registration_country")).click();
        new Select(driver.findElement(By.id("registration_country"))).selectByVisibleText("Canada");
        driver.findElement(By.id("emailAddress")).sendKeys(emailAddress);
        driver.findElement(By.id("password")).sendKeys("hello123");
        driver.findElement(By.id("passwordAgain")).sendKeys("hello123");

        //Clicks the form and asserts that the My account page is shown.
        driver.findElement(By.id("registrationForm")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='*'])[7]" +
                "/following::button[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main-con" +
                "tent\"]/div/div/div[1]/div/ul/li[1]/a")));
        assertEquals("My Account", driver.findElement(By.xpath("//*[@id=\"main-conten" +
                "t\"]/div/div/div[1]/div/ul/li[1]/a")).getText());
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
