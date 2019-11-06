import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class T2_41_Verify_customer_DB {
    private WebDriver driver;
    private String url;
    private StringBuffer verificationErrors = new StringBuffer();
    private String emailAdress;

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

        //Reads admin URL
        String fileName = "adminUrl.txt";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        url = bufferedReader.readLine();

        //Reads email adress from the recently created customer
        fileName = "Created_Customer_Info";
        FileReader fileReader2 = new FileReader(fileName);
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
        emailAdress = bufferedReader2.readLine();
    }

    @Test
    public void testT2_41() throws Exception {

        //navigate to shopizer localhost admin
        driver.get(url);

        //login tasks
        BufferedReader f =new BufferedReader( new FileReader("adminLogin"));
        String admin = f.readLine();
        String password = f.readLine();
        driver.findElement(By.id("username")).sendKeys(admin);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("formSubmitButton")).click();

        //clicks details on the latest entry in the customer lists
        driver.findElement(By.linkText("Customers")).click();
        List<WebElement> myList = (driver.findElements(By.className("buttonTitle")));
        myList.get(myList.size()-1).click();

        //assert name
        assertEquals("Team2", driver.findElement(By.id("billing.firstName")).getAttribute("value"));
        assertEquals("VG", driver.findElement(By.xpath("//*[@id=\"billing.lastName\"]")).getAttribute("value"));

        //assert customers email adress
        assertEquals(emailAdress, driver.findElement(By.id("emailAddress")).getAttribute("value"));

        //Asserting customers country
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"billing.country.isoCode\"]")));
        WebElement option = select.getFirstSelectedOption();
        assertEquals("Canada", option.getText() );

        //logout from admins account
        /*blir problem här. admintexten dyker inte upp då chromedriver startar ett för litet fönster.
        * vi måste hitta ett sätt att automatiskt ändra storleken på fönstret, eller fungerar det i --headless? */
        driver.findElement(By.linkText("admin")).click();
        driver.findElement(By.linkText("Logout")).click();
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