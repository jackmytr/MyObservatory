package page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.ConstantFile;
import com.Xpath_Locator_Android_ENG;
import com.Xpath_Locator_Android_SCHI;
import com.Xpath_Locator_Android_TCHI;
import com.Xpath_Locator_IOS_ENG;
import com.Xpath_Locator_IOS_SCHI;
import com.Xpath_Locator_IOS_TCHI;
import base.Base;
import base.BasePage;
import base.DriverContext;
import org.testng.ITestContext;
import utilities.AppiumUtil;
import utilities.ExcelUtil;


public class ForecastServicesPage extends BasePage {

	private AppiumDriver<MobileElement> driver = DriverContext.getDriver();

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.nineDayForecastBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.nineDayForecastBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.nineDayForecastBtn)
	})
	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.nineDayForecastBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.nineDayForecastBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.nineDayForecastBtn)
	})
	@CacheLookup
	private MobileElement nineDayForecastBtn;

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.nineDayForecastTab),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.nineDayForecastTab),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.nineDayForecastTab)
	})
	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.nineDayForecastTab),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.nineDayForecastTab),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.nineDayForecastTab)
	})
	@CacheLookup
	private MobileElement nineDayForecastTab;

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.tomorrowDate),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.tomorrowDate),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.tomorrowDate)
	})
	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.tomorrowDate),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.tomorrowDate),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.tomorrowDate),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.tomorrowDateiPad),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.tomorrowDateiPad),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.tomorrowDateiPad),
	})
	@CacheLookup
	private MobileElement tomorrowDate;

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.tomorrowTemperature),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.tomorrowTemperature),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.tomorrowTemperature)
	})
	private MobileElement tomorrowTemperature;

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.tomorrowHumidity),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.tomorrowHumidity),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.tomorrowHumidity)
	})
	private MobileElement tomorrowHumidity;

	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.tomorrowWeather),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.tomorrowWeather),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.tomorrowWeather),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.tomorrowWeatheriPad),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.tomorrowWeatheriPad),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.tomorrowWeatheriPad)

	})
	@CacheLookup
	private MobileElement tomorrowWeather;

	public void tapNineDayForecastBtn() throws Exception {
		//call function to swipe left menu bar until 9-Day forecast btn is visible then click
		AppiumUtil.swipeLeftElementVisible(nineDayForecastBtn, ConstantFile.TIMEOUT2);
		nineDayForecastBtn.click();
	}

	public void checkNineDayForecastPage() throws Exception {
		//wait till 9-Day forecast page loaded by checking 9-Day forecast tab displayed
		try {
			new WebDriverWait(driver,ConstantFile.TIMEOUT10).until(ExpectedConditions.visibilityOf(nineDayForecastTab));
			Assert.assertTrue(nineDayForecastTab.isDisplayed());
		} catch (AssertionError e) {
			Base.setErrorMsg("9-Day Forecast page is not displayed");
			throw e;
		}
	}

	public void checkTomorrowWeatherForecast() throws Exception {
		ITestContext itc = test.TestRun.itc;
		//OS checking since Android and IOS has different way of weather presentation
		if (itc.getCurrentXmlTest().getParameter("platform").equals("ANDROID")) {
			//Android: assertion for tomorrow's date, temperature and humidity's element presence before reading their text
			Assert.assertEquals(true, tomorrowDate.isDisplayed());
			Assert.assertEquals(true, tomorrowTemperature.isDisplayed());
			Assert.assertEquals(true, tomorrowHumidity.isDisplayed());

			//get text value from date, temperature and humidity elements and print
			System.out.println("Weather forecast for tomorrow (" + tomorrowDate.getText() + ") is:" );
			System.out.println("Temperature: " + tomorrowTemperature.getText());
			System.out.println("Humidity: " + tomorrowHumidity.getText());
		}
		else if (itc.getCurrentXmlTest().getParameter("platform").equals("IOS")) {
			//IOS: assertion for tomorrow's date, temperature and humidity's element presence before reading their text
			Assert.assertEquals(true, tomorrowDate.isDisplayed());
			Assert.assertEquals(true, tomorrowWeather.isDisplayed());

			//get text value from date, weather elements and print
			System.out.println("Weather forecast for tomorrow (" + tomorrowDate.getText() + ") is:" );
			System.out.println("Temperature/Humidity: " + tomorrowWeather.getText());
		}
	}
}