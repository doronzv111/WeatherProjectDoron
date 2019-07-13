import locators.WeatherLocators;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WeatherTest {
    private ImplementsWeather implementsMethod = new ImplementsWeather();

    public WebDriver openWeather() {
        System.setProperty("webdriver.chrome.driver", WeatherLocators.WEB_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        implementsMethod.startWeather(driver);
        return driver;
    }
    /*****************************************************
     * Description: The test will succeed case the humidity during the past 10 days was below 60
     *******************************************************/
    @Test
    public void checkInLastTenDaysHumidityIsBelow60(){
        WebDriver driver = openWeather();
        implementsMethod.clickOnSearchLine(driver);
        implementsMethod.sendText(driver,"Paris");
        implementsMethod.clickOnResult(driver);
        implementsMethod.clickOnTenDays(driver);
        implementsMethod.ifHumidityHigherBelow60Percent(driver);
        driver.close();
    }
    /*****************************************************
     * Description: The test will fail in case during the past 10 days there are two or more days in a row which are not sunny.
     *******************************************************/
    @Test
    public void checkIfLast2DaysWithoutSun() {
        WebDriver driver = openWeather();
        implementsMethod.clickOnSearchLine(driver);
        implementsMethod.sendText(driver,"Tel Aviv");
        implementsMethod.clickOnResult(driver);
        implementsMethod.clickOnTenDays(driver);
        implementsMethod.checkIf2DaysWithoutSun(driver);
        driver.close();
    }
}
