import locators.WeatherLocators;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImplementsWeather {

    public void startWeather(WebDriver driver) {
        driver.get(WeatherLocators.METRIC_URL);
    }

    public void clickOnSearchLine(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(WeatherLocators.LINE_SEARCH_METRIC)));
        while (!driver.findElement(By.xpath(WeatherLocators.RECENT_SEARCH)).isDisplayed()){
            driver.findElement(By.xpath(WeatherLocators.LINE_SEARCH_METRIC)).click();
        }
        Assert.assertTrue("Failed to click on search line. ",driver.findElement(By.xpath(WeatherLocators.RECENT_SEARCH)).isDisplayed());
        System.out.println("Click on search line.");
    }

    public void sendText(WebDriver driver, String location) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        driver.findElement(By.xpath(WeatherLocators.LINE_SEARCH_METRIC)).sendKeys(location);
        Assert.assertTrue("Failed to search location ",driver.findElement(By.xpath(WeatherLocators.RECENT_SEARCH)).isDisplayed());
        System.out.println("Search location: "+location);

    }

    public void clickOnResult(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(WeatherLocators.SEARCH_RESULT_METRIC)));
        driver.findElement(By.xpath(WeatherLocators.SEARCH_RESULT_METRIC)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(WeatherLocators.TEMPERATURE_MATRIC)));
        Assert.assertTrue("Failed to click on result ",driver.findElement(By.xpath(WeatherLocators.TEMPERATURE_MATRIC)).isDisplayed());
        System.out.println("Click on the result");
    }
    public void clickOnTenDays(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        driver.findElement(By.xpath(WeatherLocators.TEN_DAYS)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(WeatherLocators.TEMPERATURE_MATRIC)));
        Assert.assertTrue("Failed to click on 10 days",driver.findElement(By.xpath(WeatherLocators.TEN_DAYS_PAGE)).isDisplayed());
        System.out.println("Click on 10 days");
    }
    public void ifHumidityHigherBelow60Percent(WebDriver driver) {
        String resultMatric = cutTextToVerify(driver.findElement(By.xpath(WeatherLocators.PERCENT_HUMIDITY_1
                +"1"+WeatherLocators.PERCENT_HUMIDITY_2)).getText());
        int accurateResult = Integer.parseInt(resultMatric);
        boolean below= true;

        for (int i=2; i<16;i++){
           if(accurateResult>=80){
               below= false;
               break;
           }
            resultMatric = cutTextToVerify(driver.findElement(By.xpath(WeatherLocators.PERCENT_HUMIDITY_1
                    +i+WeatherLocators.PERCENT_HUMIDITY_2)).getText());
            accurateResult = Integer.parseInt(resultMatric);
        }
        Assert.assertTrue("The humidity was over sixty percent",below);
        System.out.println("The humidity was below sixty percent in the last ten days");

    }
    private String cutTextToVerify(String text) {
        String[] str = text.split("%",2);
        String str1 = str[0];
        return str1;
    }

    public void checkIf2DaysWithoutSun(WebDriver driver) {
        String resultMatric = driver.findElement(By.xpath(WeatherLocators.WEATHER_DESCRIPTION_1
                +"1"+WeatherLocators.WEATHER_DESCRIPTION_2)).getText();
        boolean below= true;

        for (int i=2; i<13;i++) {
            if (!resultMatric.equals(WeatherLocators.SUN)) {
                if(!driver.findElement(By.xpath(WeatherLocators.WEATHER_DESCRIPTION_1
                        +(i+1)+ WeatherLocators.WEATHER_DESCRIPTION_2)).getText().equals(WeatherLocators.SUN)){
                    below = false;
                    break;
                }
            }
            resultMatric = driver.findElement(By.xpath(WeatherLocators.WEATHER_DESCRIPTION_1
                    +i+ WeatherLocators.WEATHER_DESCRIPTION_2)).getText();
        }
        Assert.assertTrue("There have been more than two days of non-sunny weather in the past ten days",below);
        System.out.println("Hot weather in last ten days");
    }

}
