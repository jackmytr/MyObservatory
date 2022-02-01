package page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import org.openqa.selenium.By;
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


public class CommonPage extends BasePage {

	private AppiumDriver<MobileElement> driver = DriverContext.getDriver();

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.MyObservatory),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.MyObservatory),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.MyObservatory)
	})
	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.MyObservatory),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.MyObservatory),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.MyObservatory)
	})
	@CacheLookup
	private MobileElement MyObservatory;

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.moreOptionsBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.moreOptionsBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.moreOptionsBtn)
	})
	@CacheLookup
	private MobileElement moreOptionsBtn;


	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.iosSettingBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.iosSettingBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.iosSettingBtn)
	})
	@CacheLookup
	private MobileElement iosSettingBtn;

	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.iosBackBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.iosBackBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.iosBackBtn)
	})
	@CacheLookup
	private MobileElement iosBackBtn;

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.menuBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.menuBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.menuBtn)
	})
	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.menuBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.menuBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.menuBtn)
	})
	@CacheLookup
	private MobileElement menuBtn;

	@AndroidFindAll({
			@AndroidBy(xpath = Xpath_Locator_Android_ENG.languageBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_SCHI.languageBtn),
			@AndroidBy(xpath = Xpath_Locator_Android_TCHI.languageBtn)
	})
	@iOSXCUITFindAll({
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_ENG.languageBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_SCHI.languageBtn),
			@iOSXCUITBy(xpath = Xpath_Locator_IOS_TCHI.languageBtn)
	})
	@CacheLookup
	private MobileElement languageBtn;

	public void checkMainPage() throws Exception {
		//check if main page is shown by checking main title displayed
		try {
			new WebDriverWait(driver,ConstantFile.TIMEOUT10).until(ExpectedConditions.visibilityOf(MyObservatory));
			Assert.assertTrue(MyObservatory.isDisplayed());
		} catch (AssertionError e) {
			Base.setErrorMsg("Main page is not displayed");
			throw e;
		}
	}

	public void clickMenuBtn() throws Exception {
		menuBtn.click();  //click menu button
	}

	public void selectLangOpt() throws Exception {
		ITestContext itc = test.TestRun.itc;
		if (itc.getCurrentXmlTest().getParameter("platform").equals("ANDROID")) {
			//Language switch - Android flow
			new WebDriverWait(driver,ConstantFile.TIMEOUT5).until(ExpectedConditions.visibilityOf(moreOptionsBtn));
			moreOptionsBtn.click();  //click Android more options btn
			new WebDriverWait(DriverContext.driver,ConstantFile.TIMEOUT5).until(ExpectedConditions.visibilityOf(languageBtn));
			languageBtn.click();  //click lang btn
			String lang = ExcelUtil.getValue("Language").trim();
			DriverContext.driver.findElement(By.xpath("//*[@text='" + lang + "']")).click();  //get language from Excel data and decide which language option to tap
		} else if (itc.getCurrentXmlTest().getParameter("platform").equals("IOS")) {
			//Language switch - IOS flow
			new WebDriverWait(DriverContext.driver,ConstantFile.TIMEOUT5).until(ExpectedConditions.visibilityOf(menuBtn));
			menuBtn.click();  //click menu btn
			new WebDriverWait(DriverContext.driver,ConstantFile.TIMEOUT5).until(ExpectedConditions.visibilityOf(iosSettingBtn));
			iosSettingBtn.click();  //click iOS setting btn on menu bar
			new WebDriverWait(DriverContext.driver,ConstantFile.TIMEOUT5).until(ExpectedConditions.visibilityOf(languageBtn));
			languageBtn.click();  //click lang btn
			String lang = ExcelUtil.getValue("Language").trim();
			DriverContext.driver.findElement(By.xpath("//*[@text='" + lang + "']")).click();  //get language from Excel data and decide which language option to tap
			new WebDriverWait(DriverContext.driver,ConstantFile.TIMEOUT5).until(ExpectedConditions.visibilityOf(iosBackBtn));
			iosBackBtn.click();  //click iOS back btn to go back to menu bar
			new WebDriverWait(DriverContext.driver,ConstantFile.TIMEOUT5).until(ExpectedConditions.visibilityOf(menuBtn));
			menuBtn.click();  //dismiss menu bar
		}

	}
}


