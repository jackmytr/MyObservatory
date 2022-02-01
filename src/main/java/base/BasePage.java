package base;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import com.ConstantFile;
import com.Xpath_Locator_Android_ENG;
import com.Xpath_Locator_Android_SCHI;
import com.Xpath_Locator_Android_TCHI;
import com.Xpath_Locator_IOS_ENG;
import com.Xpath_Locator_IOS_SCHI;
import com.Xpath_Locator_IOS_TCHI;
import com.experitest.appium.SeeTestClient;

import io.cucumber.java.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import utilities.AppiumUtil;
import utilities.ExcelUtil;

public abstract class BasePage {
	protected Scenario scenario = Base.scenario;
	protected static String language = "English";
	protected static Logger logger = Logger.getLogger(BasePage.class);
	protected static SeeTestClient client;
	protected static AppiumDriver<MobileElement> driver = DriverContext.getDriver();
	public String strPlatform = DriverContext.getDeviceOS();
	public String deviceName;
	public String reportStatus = "Passed";
	
	private static int screenHeight = driver.manage().window().getSize().height;
	protected static int screenWidth = driver.manage().window().getSize().width;

	private int display_x_min = (int)(screenWidth*0.1);
	private int display_x_max = (int)(screenWidth*0.9);
	private int display_y_min = (int)(screenHeight*0.1);
	private int display_y_max = (int)(screenHeight*0.9);
	private int swipe_x_small = (int)(screenWidth*0.2);
	private int swipe_x_big = (int)(screenWidth*0.8);
	private int swipe_little_x_small = (int)(screenWidth*0.4);
	private int swipe_little_x_big = (int)(screenWidth*0.6);
	private int swipe_y_small = (int)(screenHeight*0.2);
	private int swipe_y_big = (int)(screenHeight*0.8);
	private int swipe_little_y_small = (int)(screenHeight*0.4);
	private int swipe_little_y_big = (int)(screenHeight*0.6);
	private int swipe_time = 1500;
	
	protected static int va_y_max;//visible area 
	protected static int va_y_min;
	protected static int va_y_max_home_page;

	protected static File img1;
	protected static File img2;
	
    public BasePage() {
    	System.out.println("DriverContext.getDriver() = "+DriverContext.getDriver());
        PageFactory.initElements(new AppiumFieldDecorator(DriverContext.getDriver()), this);

        this.language = ExcelUtil.getValue("Language").trim();
        client = Base.getClient();
    }
    
    protected void resetVisibleAreaMaxY(){
    	resetVisibleAreaMaxY(false);
    }

    protected void resetVisibleAreaMaxY(boolean onHomePage){
    	if(!onHomePage){
    		va_y_max = screenHeight;
    	}
    }


    public int getScreenHeight() {
    		return screenHeight;
    }
    
    public boolean checkFullScreen(MobileElement element) {
    		int position_y_top = element.getLocation().y;
    		int size_y = element.getSize().width;
    		int position_y_below = position_y_top + size_y;
    		
    		System.out.println("position_y_below : " + position_y_below + ", screenHeight : " + screenHeight);
    		boolean flag = position_y_below > (int)(screenHeight*0.95);
    		return flag;
    }
    
    private static void execShell(String scriptPath,String ... para) throws Exception {
		String[] cmd = new String[] {scriptPath};
		cmd = ArrayUtils.addAll(cmd, para);
		System.out.println(cmd);
		
		//handle error: Permission denied 
		ProcessBuilder builder = new ProcessBuilder("/bin/chmod","755",scriptPath);
		Process process = builder.start();
		process.waitFor();
		
		Process ps = Runtime.getRuntime().exec(cmd);
		ps.waitFor();
	}
    
    public static void doPaste() throws Exception { 
		Robot r = new Robot();
		r.setAutoDelay(500); 
	    r.keyPress(KeyEvent.VK_CONTROL); 
	    r.keyPress(KeyEvent.VK_V); 
	    r.keyRelease(KeyEvent.VK_CONTROL); 
	    r.keyRelease(KeyEvent.VK_V); 
	  }
    
    /*public void setValue(String value) throws Exception {
    		String deviceName = client.getDeviceProperty("device.name");
    		System.out.println(deviceName);
    		
    		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    		Transferable tf = new StringSelection(value);
    		clipboard.setContents(tf, null);
    		
    		String osType = System.getProperty("os.name").toLowerCase();
    		if(osType.contains("windows")){
    			deviceName = DriverContext.getDeviceName();
    			String prefix = "";
    			if (Base.platform.equalsIgnoreCase("ANDROID")) {
    				prefix="adb:";
    			}else{
    				prefix="ios_app:";	
    			}
    			setAppWindowFocused(prefix+ deviceName);
    			
    		}else{
    			execShell("resource/getWindowTitle.sh",deviceName);
    		}
    		
		doPaste();
	}*/
    
    private void setAppWindowFocused(String windowTitle) {
		App app = new App(windowTitle);
		app.focus();
		
	}

//    public void sendKeysOneByOne(MobileElement element , String text) {
//    		//解决iPhone11中sendkeys中重复输入
//    		String letter_xpath ="//*[@text='letters']";
//    		String number_xpath ="//*[@text='numbers']";
//    		String current_char = "letter";
//    		String previous_char = "letter";
//    		boolean first_input = true;
//    		char[] c = text.toCharArray();
//		for(char tmp : c) {
//			if (Character.isLetter(tmp)) {
//				current_char = "letter";
//				if (!current_char.equals(previous_char)) {
//					driver.findElementByXPath(letter_xpath).click();
//				}
//				System.out.println(Character.isUpperCase(tmp));
//				if (Character.isUpperCase(tmp) && (!first_input)) {
//					driver.findElementByXPath("//*[@text='shift']").click();
//				}
//			}else {
//				current_char = "number";
//				if (!current_char.equals(previous_char)) {
//					driver.findElementByXPath(number_xpath).click();
//				}
//			}
//			driver.findElementByAccessibilityId(String.valueOf(tmp)).click();
//			previous_char = current_char;
//			first_input = false;
//		}
//	}
    
    /*public void sendKeysInIOSAndAOS(MobileElement element , String text) {
    		if (strPlatform.contains("IOS")) {
//    			sendKeysOneByOne(element , text);
				element.click();
				delay(1);
				element.clear();
				delay(1);
				client.sendText(text);
				delay(1);
    			try {
				new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath_Locator_IOS_SCHI.b_Done_Keybord)));
				driver.findElementByXPath(Xpath_Locator_IOS_SCHI.b_Done_Keybord).click();
				} catch (Exception e) {
					// TODO: handle exception
				}
    		}else {
    		    element.sendKeys(text);
    		}
    		closeKeyBoard();
	}*/
    
    public void isNoticeExist() {
    		WebDriverWait wait = new WebDriverWait(driver,10);
    		System.out.println(System.currentTimeMillis());
    		try {
        		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[(@id='NOTICE' or @id='通知') and @class='UIAStaticText']"))));
        		System.out.println("find notice!");
        		System.out.println(System.currentTimeMillis());
        		delay(3);
        		driver.findElement(By.xpath("//*[@class='UIAButton']")).click();
        		System.out.println("close notice!");
        		System.out.println(System.currentTimeMillis());
			} catch (Exception e) {
				System.out.println("no notice!");
				System.out.println(System.currentTimeMillis());
			}
    }

    public void addCapture(String msg, boolean status){
    		Base.addCapture(msg, status);
	}
    
    public void addCapture(String msg, boolean status, int time){
		Base.addCapture(msg, status, time);
}
    
    public static String getPageLang() {
    	return language;
    }
    
    public void delay(long delaySec) {
		try {
			Thread.sleep(delaySec * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
    
    public void waitElementApper()
    {
    	try {
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    protected void waitLoadingDisappear(){
    	try {
			AppiumUtil.waitElementDisappear();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    protected void waitLoadingDisappear(int timeout){
    		try {
			AppiumUtil.waitElementDisappear(timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    protected MobileElement waitElementPresent(By by){
    	return  waitElementPresent(by,ConstantFile.TIMEOUT60);
    }
    
    protected MobileElement waitElementPresent(By by,int timeout){
    	return  (MobileElement) new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
    }
    
    protected MobileElement waitElementClickable(By by){
    	return waitElementClickable(by,ConstantFile.TIMEOUT60);
    }
    
    protected MobileElement waitElementClickable(By by,int timeout){
    	return AppiumUtil.waitElementClickable(by, timeout);
    }
    
    protected MobileElement waitElementClickable(MobileElement element){
    	return waitElementClickable(element,ConstantFile.TIMEOUT60);
    }
    
    protected MobileElement waitElementClickable(MobileElement element,int timeout){
    	return AppiumUtil.waitElementClickable(element, timeout);
    }
    public void swipeElementVisible(MobileElement element) {
    	int i = 1;
    	int x = element.getCenter().getX();
    	while(!element.isDisplayed()) {
    		System.out.println("Current platform: "+ DriverContext.getDeviceOS());
    		if (!DriverContext.getDeviceOS().contains("IOS")) {
//    			swipeCoordination(200, 1400, 200, 650, swipe_time);
    			addCapture("swipe times : " + i, true);
    			swipeCoordination(x, swipe_y_big, x, swipe_y_small, swipe_time);
    		} else {
    			addCapture("swipe times : " + i, true);
    			ScrollownLittleScreen();
    		}
    		delay(1);
    		i++;
    	}
    }
    
    public boolean isElementPresent(By locator) {
    	
    	return isElementPresent(locator,ConstantFile.TIMEOUT60);
	}

    public boolean isElementPresent(By locator, int timeout) {
    	
    	WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), timeout);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    
    public void swipeLeftOnElement(MobileElement element)
    {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	Map<String, Object> params = new HashMap<>();
    	params.put("direction", "left");
    	params.put("element", ((RemoteWebElement) element).getId());
    	js.executeScript("mobile: swipe", params);

    }
    public void swipeHalfScreen()
    {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		org.openqa.selenium.Dimension dimension = driver.manage().window().getSize();
		Double startHeight = dimension.getHeight()*0.5;
		Double startWidth = dimension.getWidth()*0.5;
		int startY = startHeight.intValue();
		int startX = startWidth.intValue();
		if(!strPlatform.contains("IOS")) {
//			new AndroidTouchAction(driver).press(PointOption.point(startX,startY)).moveTo(PointOption.point(startX,485)).release().perform();
			new AndroidTouchAction(driver).press(PointOption.point(startX,swipe_y_big)).moveTo(PointOption.point(startX,swipe_y_small)).release().perform();
		}else {
//			new IOSTouchAction(driver).press(PointOption.point(startX,startY)).moveTo(PointOption.point(startX,485)).release().perform();
			new IOSTouchAction(driver).press(PointOption.point(startX,swipe_y_big)).moveTo(PointOption.point(startX,swipe_y_small)).release().perform();
		}
    }
    public void scrollToDown() {
    	if(strPlatform.contains("IOS")) {
    		scrollToDownIOS();
    	}
    	else {
//add scroll to down AOS    		
    	}
    }
    public void scrollToDownIOS()
    {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);

    }
    
    public void TapOnScreen(int x, int y) {
		if ( strPlatform.contains("IOS")) {
			new IOSTouchAction(driver).tap(PointOption.point(x, y)).perform();
		} else {
			new AndroidTouchAction(driver).tap(PointOption.point(x, y)).perform();
		}
	}
    
    public void tapOnElement(MobileElement element) {
    	System.out.println("Click on X: " + element.getCenter().getX() + " ,Y: " + element.getCenter().getY());
    	if ( strPlatform.contains("IOS")) {
			new IOSTouchAction(driver).press(PointOption.point(element.getCenter().getX(), element.getCenter().getY())).release().perform();
		} else {
			new AndroidTouchAction(driver).press(PointOption.point(element.getCenter().x, element.getCenter().y)).release().perform();
		}
	}
    
    public static void scrollToListItem(String targetItemXpath, String value) {
    		System.out.println("Client - set picker value :" + value);
    		client.setPickerValues("NATIVE", "xpath=" + targetItemXpath, 0, 0, value);
    		driver.findElement(By.xpath("//*[@text='Done']")).click();
    }
    
    public void scrollToListItem(String targetItemXpath, int WheelIndex, String value) {
    		//多个wheel
		System.out.println("Client - set picker value");
		client.setPickerValues("NATIVE", "xpath=" + targetItemXpath, 0, WheelIndex, value);
    }
    
    /*public void scrollToListItemOneByOne(MobileElement element, String value) {
		//逐个scroll然后click，判断是否一致
    		//scrollToListItem 不需要click，所以直接用
	    	String obj_text = element.getText();
		while (!obj_text.equals(value)) {
			element.click();
			scrollToListItem(Xpath_Locator_IOS_ENG.u_picker, "up:1");
			MobileElement u_picker = driver.findElementByXPath(Xpath_Locator_IOS_ENG.u_picker);
			int x = u_picker.getCenter().getX();
			int y = u_picker.getCenter().getY()-30;
			System.out.println(x +"," +y);
			TapOnScreen(x, y);
			obj_text = element.getText();
			System.out.println(obj_text);
			driver.findElementByXPath("//*[@text='Done']").click();;
		}
    }*/

    public void backToAppIOS13() {
    		int height = driver.manage().window().getSize().height;
    		int width = driver.manage().window().getSize().width;
		TapOnScreen((int)(width*0.036), (int)(height*0.035));
	}
    
    public void ScrollownLittleScreen() {
		int x= (int) (DriverContext.getDriver().manage().window().getSize().getWidth()*0.5);
		int y=(int) (DriverContext.getDriver().manage().window().getSize().getHeight()*0.5);
		if ( strPlatform.contains("IOS")) {
			new IOSTouchAction(driver).press(PointOption.point(swipe_little_x_small, swipe_little_y_big)).moveTo(PointOption.point(swipe_little_x_small, swipe_little_y_small)).perform();
		} else {
//			new AndroidTouchAction(driver).press(PointOption.point(x, y)).moveTo(PointOption.point(x, y-100)).perform();
			new AndroidTouchAction(driver).press(PointOption.point(swipe_little_x_small, swipe_little_y_big)).moveTo(PointOption.point(swipe_little_x_small, swipe_little_y_small)).perform();

		}
	}

    public void closeKeyBoard() {
//    		if (strPlatform.contains("IOS")) {
    			driver.hideKeyboard();
//		}else {
//			driver.findElement(By.xpath("//*[@id='back']")).click();
//		}
    }
    
    protected void hideKeyboardByDone(){
    	By by = By.xpath("//*[@text='Done']");
    	
    	if(isElementPresent(by,3)){
    		driver.findElement(by).click();
    	}
    }
    
	public void WaitLoading(MobileElement element) {
		new WebDriverWait(driver,ConstantFile.TIMEOUT60).until(ExpectedConditions.visibilityOf(element));
		delay(ConstantFile.TIMEOUT3);
	}
	public MobileElement swipeRightUntilVisible(MobileElement element) {
		if (isElementPresent(element, 10)) {
			logger.info("page is loaded");
		}

        try {
			int xPosition = element.getCenter().getX();
			int yPosition = element.getCenter().getY();
			System.out.println("Start xPosition: " + xPosition);
			System.out.println("window width size: " + driver.manage().window().getSize().getWidth());

			int i = 1;
			while (xPosition > driver.manage().window().getSize().getWidth() - 50) {
				addCapture("swipe right times : " + i, true);
				swipeRight(yPosition);
				System.out.println("Swiping Right...");
				xPosition = element.getCenter().getX();
				System.out.println("xPosition: " + xPosition);
				System.out.println("window width size: " + driver.manage().window().getSize().getWidth());
				i++;
			}
			System.out.println("Final xPosition: " + xPosition);
			
		}catch (Exception e) {
			for(int i = 1; i < 10; i++){
				swipeRight(270);
				try {
					if(element.getCenter().getX() <= driver.manage().window().getSize().getWidth() - 50 ) {
						break;
					}
				}catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
        
        delay(2);
        return element;
	}
	
	public MobileElement swipeRightUntilVisibleIPAD(MobileElement element) {
		System.out.println("Func : swipeRightUntilVisibleIPAD");
		System.out.println(driver.getContextHandles());
//		if (isElementPresent(element, 60)) {
//			logger.info("page is loaded");
//		}
		int xPosition = element.getCenter().getX();
		int yPosition = element.getCenter().getY();
		System.out.println("Start xPosition: "+ xPosition);
		System.out.println("window width size: "+ driver.manage().window().getSize().getWidth());
		
		int i = 1;
		while (xPosition > driver.manage().window().getSize().getWidth()) {
			addCapture("swipe right times : " + i, true);
//			swipeRight();
			swipeRightOnVitalityTopSubmenu(yPosition);
			System.out.println("Swiping Right...");
			xPosition = element.getCenter().getX();
			System.out.println("updated xPosition: "+ xPosition);
			System.out.println("window width size: "+ driver.manage().window().getSize().getWidth());
			i ++ ;
		}
		System.out.println("Final xPosition: "+ xPosition);
		return element;
	}
		
//	public static void dragCoordinates(AppiumDriver<MobileElement> driver, int x1, int y1, int x2, int y2, int time) {
//		System.out.println(
//				"drag from point (" + x1 + ", " + y1 + ") to point (" + x2 + ", " + y2 + ") in " + time + " ms..");
//		driver.executeScript(
//				"seetest:client.dragCoordinates(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + time + ")");
//
//	}
	
	public static void dragCoordinates(AppiumDriver<MobileElement> driver, int x1, int y1, int x2, int y2, int time)  {
		dragCoordinatesMethod(driver, x1, y1, x2, y2, time);
	}
	
	public static void dragCoordinates(AppiumDriver<MobileElement> driver, double x1, double y1, double x2, double y2, int time) {
		int x1Int = (int) x1;
		int x2Int = (int) x2;
		int y1Int = (int) y1;
		int y2Int = (int) y2;
		dragCoordinatesMethod(driver, x1Int, y1Int, x2Int, y2Int, time);
	}
	
	private static void dragCoordinatesMethod(AppiumDriver<MobileElement> driver, Object x1, Object y1, Object x2, Object y2, int time) {
		//System.out.println(
		//		"drag from point (" + x1 + ", " + y1 + ") to point (" + x2 + ", " + y2 + ") in " + time + " ms..");
		driver.executeScript(
				"seetest:client.dragCoordinates(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + time + ")");

	}
	
	
	
	public boolean isElementPresent(MobileElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isElePresent(MobileElement element) {
		return isElePresent(element,ConstantFile.TIMEOUT10);
	}
	
	
	public boolean isElePresent(MobileElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.attributeToBeNotEmpty(element, "y"));
			return true;
		} catch (Exception e) {
			return false;
		}
		
		//return element == null ? false : true;
	}
	
	protected void waitElementPresent(MobileElement element){
		waitElementPresent(element,ConstantFile.TIMEOUT10);
	}
	
	protected void waitElementPresent(MobileElement element, int timeout){
		 new WebDriverWait(driver, timeout).until(ExpectedConditions.attributeToBeNotEmpty(element, "y"));
		
	}
	
	public boolean checkElementPresent(MobileElement element, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.attributeToBeNotEmpty(element, "y"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void findElementAndClick(MobileElement element, String elementIOSXpath, String elementAndroidXpath) throws Exception {
		
		try {
			if ("IOS_APP".equalsIgnoreCase(DriverContext.getDeviceOS()) && (!elementIOSXpath.isEmpty())) {
				AppiumUtil.waitUntil(By.xpath(elementIOSXpath), ConstantFile.TIMEOUT60);
			} else if (!elementAndroidXpath.isEmpty()){
				AppiumUtil.waitUntil(By.xpath(elementAndroidXpath), ConstantFile.TIMEOUT60);
			}
		} catch (Exception e) {
			System.out.println("Failed in func: AppiumUtil.waitUntil...");
		}
		
		int xPosition = element.getCenter().getX();
		int yPosition = element.getCenter().getY();
		
//		AppiumUtil.swipeElementVisible(element, ConstantFile.TIMEOUT120);
//		swipeElementVisibleWithWaitAndCustomizedCoordinate(element, 100, 1400, 100, 600, swipe_time);
		swipeElementVisibleWithWaitAndCustomizedCoordinate(element, xPosition, swipe_y_big, xPosition, swipe_y_small, swipe_time);
		System.out.println("Target element displayed....");
		element.click();
		System.out.println("Target element clicked....");
	}
	
	/*public void findElementAndSendkeys(String keys, MobileElement element, String elementIOSXpath, String elementAndroidXpath) throws Exception {
		try {
			if ("IOS_APP".equalsIgnoreCase(DriverContext.getDeviceOS()) && (!elementIOSXpath.isEmpty())) {
				AppiumUtil.waitUntil(By.xpath(elementIOSXpath), ConstantFile.TIMEOUT60);
			} else if (!elementAndroidXpath.isEmpty()){
				AppiumUtil.waitUntil(By.xpath(elementAndroidXpath), ConstantFile.TIMEOUT60);
			}
		} catch (Exception e) {
			System.out.println("Failed in func: AppiumUtil.waitUntil...");
		}
		System.out.println("Target element displayed....");
//		AppiumUtil.swipeElementVisible(element, ConstantFile.TIMEOUT120);
		swipeElementVisibleWithWait(element);
//		element.click();
//		element.sendKeys(keys);
		sendKeysInIOSAndAOS(element, keys);
		System.out.println("Target element clicked....");
	}*/
	
	public void clickElementRightSide(MobileElement targetElement) {
		int targetX = targetElement.getLocation().getX();
		int targetY = targetElement.getLocation().getY();
		System.out.println("targetX = "+targetX+", targetY= "+ targetY);
		
		int sizeX = targetElement.getSize().getWidth();
		int sizeY = targetElement.getSize().getHeight();
		System.out.println("sizeX = "+targetX+", sizeY= "+ targetY);
		
		int finalX = targetX + (int)(sizeX*0.95);
		int finalY = targetY + (int)(sizeY*0.5);
		System.out.println("finalX = "+finalX+", finalY= "+ finalY);
		int clickCount = 1;
		
		System.out.println("about to click Target element....");
		driver.executeScript(
				"seetest:client.clickCoordinate(" + finalX + ", " + finalY + ", " + clickCount + ")");
		System.out.println("Target coordinate clicked....");
	}
	
	protected void clickCoordinate(int x, int y){
		System.out.println("click point：("+x+"," +y+")");
		client.clickCoordinate(x, y, 1);
	}



	public void clickElementByCoordination(MobileElement targetElement, int adjustX, int adjustY, int clickCount) {
		int targetX = targetElement.getLocation().getX();
		int targetY = targetElement.getLocation().getY();
		System.out.println("targetX = "+targetX+", targetY= "+ targetY);
		
		int finalX = targetX + adjustX;
		int finalY = targetY + adjustY;
		System.out.println("finalX = "+finalX+", finalY= "+ finalY);
		
		System.out.println("about to click Target element....");
		driver.executeScript(
				"seetest:client.clickCoordinate(" + finalX + ", " + finalY + ", " + clickCount + ")");
		System.out.println("Target coordinate clicked....");
	}
	
	public void swipe2ElementPresent(By by){
		for(int i=0;i<10;i++){
			boolean present = isElementPresent(by,2);
			if(present){
				break;
			}else{
				swipeDown();
			}
		}
	}
	
	public void swipe2ElementPresent(MobileElement element){
		for(int i=0;i<10;i++){
			boolean present = isElePresent(element,2);
			if(present){
				break;
			}else{
				swipeDown();
			}
		}
	}
	
	public MobileElement swipe2ElementVisible(By by){
		MobileElement element = driver.findElement(by);
		return swipe2ElementVisible(element);
	}
	
	public MobileElement swipe2ElementVisible(By by, int duration){
		MobileElement element = driver.findElement(by);
		if(element == null){
			System.out.println("Strange, element not found");
			swipe2ElementPresent(by);
			
			element = driver.findElement(by);
		}
		return swipe2ElementVisible(element, duration);
	}
	
	public MobileElement swipe2ElementVisible(MobileElement element){
		return swipe2ElementVisible(element,1500);
	}
	
	public MobileElement swipe2ElementVisible(MobileElement element,int duration){
		return swipe2ElementVisible(element,20,duration);
	}
	
	public MobileElement swipe2ElementVisible(MobileElement element, int maxSwipeTime,int duration){
		System.out.println("va_y_max:"+va_y_max+", screenHeight:"+ screenHeight);
		if(element == null){
			System.out.println("error, element is Null");
		}
		Point center = element.getCenter();
		int ct_y = center.y;
		
		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;
		
		//first swipe down to element vertial visible
		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);
		
		for (int i = 1; i < maxSwipeTime; i++) {
			try {
				Thread.sleep(2000);
				if (ele_y_max <= va_y_max) {
					System.out.println("element visible in V ");
					System.out.println("Element Y coordination: " + ele_y);
					System.out.println("Visible Area bottom: " + va_y_max);
					
					break;
				}
				logger.info("element.getLocation().getY(): " + ele_y);
			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, duration);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);
			
			ele_y = element.getLocation().y;
			ele_y_max = ele_y + ele_height;
		}
		
		ct_y = element.getCenter().y;
		
		delay(1);
		return element;
	}
	
	public MobileElement swipeRight2ElementVisible(MobileElement element){
		return swipeRight2ElementVisible(element,1500);
	}
	
	public MobileElement swipeRight2ElementVisible(MobileElement element,int duration){
		return swipeRight2ElementVisible(element,20,duration);
	}
	
	public MobileElement swipeRight2ElementVisible(MobileElement element,
			int maxSwipeTime, int duration) {
		// swipe right to element visible
		int ele_x = element.getLocation().x;
		int ele_width = Integer.parseInt(element.getAttribute("width"));
		int ele_x_max = ele_x + ele_width;

		int mid_screen_width = screenWidth / 2;
		Point center = element.getCenter();
		int ct_y = center.y;
		
		int from_x = mid_screen_width;
		int from_y = ct_y;
		int to_x = (int) (mid_screen_width * 0.25);
		int to_y = ct_y;

		for (int i = 1; i < maxSwipeTime; i++) {
			try {
				Thread.sleep(2000);
				if (ele_x_max <= screenWidth || (ele_x+ele_width/2<screenWidth)) {
					System.out.println("element visible in H ");
					System.out.println("Element X coordination: " + ele_x);
					System.out.println("Screen width: " + screenWidth);
					break;
				}
				logger.info("element.getLocation().getX(): " + ele_x);

			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, duration);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);

			ele_x = element.getLocation().x;
			ele_x_max = ele_x + ele_width;
		}

		delay(1);
		return element;
	}
	
	public MobileElement swipeScreenUp2ElementVisible(MobileElement element){
		Point center = element.getCenter();
		int ct_y = center.y;
		
		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;
		
		//first swipe down to element vertial visible
		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.25);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);
		
		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_y > va_y_min) {
					System.out.println("element visible in V ");
					System.out.println("Element Y coordination: " + ele_y);
					System.out.println("Visible Area top: " + va_y_min);
					
					break;
				}
				logger.info("element.getLocation().getY(): " + ele_y);
			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping up...");
			System.out.println("Swiped times:" + i);
			
			ele_y = element.getLocation().y;
			//ele_y_max = ele_y + ele_height;
		}
		
		ct_y = element.getCenter().y;
		
		//swipe right to element visible
		int ele_x = element.getLocation().x;
		int ele_width = Integer.parseInt(element.getAttribute("width"));
		int ele_x_max = ele_x + ele_width;
		
		 from_x = mid_screen_width;
		 from_y = ct_y;
		 to_x = (int)(mid_screen_width * 0.25);
		 to_y = ct_y;
		
		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_x_max <= screenWidth) {
					System.out.println("element visible in H ");
					System.out.println("Element X coordination: " + ele_x);
					System.out.println("Screen width: " + screenWidth);
					break;
				}
				logger.info("element.getLocation().getX(): " + ele_x);
				
			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);
			
			ele_x = element.getLocation().x;
			ele_x_max = ele_x + ele_width;
		}
		
		
		return element;
	}
	
	public MobileElement swipeDown2ElementDisplayed(MobileElement element){
		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);
		
		for(int i = 0;i<10;i++){
			boolean visible = element.isDisplayed();
			if(visible){
				dragDown2VisibleArea(element);
				break;
			}else{
				swipeCoordination(from_x, from_y, to_x, to_y, 1500);
				
			}
		}
		
		return element;
	}
	
	private void dragDown2VisibleArea(MobileElement element){
		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;
		
		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);
		
		if (ele_y_max <= va_y_max) {
			System.out.println("Element Y coordination: " + ele_y);
			System.out.println("Visible Area bottom: " + va_y_max);
		}else{
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
		logger.info("element.getLocation().getY(): " + ele_y);
	}
	// swipeDown();
	// addCapture("swipe to find element : " + i);
	
	}
	public MobileElement swipeDown2ElementVisible(MobileElement element, boolean instrument) {
		int ele_y = element.getLocation().y;
		int ele_height = Integer.parseInt(element.getAttribute("height"));
		int ele_y_max = ele_y + ele_height;

		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = (int) (screenHeight * 0.75);
		int to_x = mid_screen_width;
		int to_y = (int) (screenHeight * 0.5);
		
		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_y_max <= va_y_max) {
					System.out.println("Element Y coordination: " + ele_y);
					System.out.println("Visible Area bottom: " + va_y_max);
					break;
				}
				logger.info("element.getLocation().getY(): " + ele_y);
			} catch (Exception e) {
			}
			// swipeDown();
			// addCapture("swipe to find element : " + i);
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);
		}

		System.out.println("element found...");

		return element;
	}
	
	public MobileElement swipeElementVisibleWithWait(MobileElement element) {
		return swipeElementVisibleWithWaitAndCustomizedCoordinate(element, 100, swipe_y_big, 100, swipe_y_small, swipe_time);
	}
	
	
	public MobileElement swipeElementVisibleWithWaitAndCustomizedCoordinate(MobileElement element, int fromX, int fromY, int toX, int toY, int duration) {
		logger.info("Start of function swipeElementVisibleWithWait");
		try {
			AppiumUtil.waitElementDisappear();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i = 1; i < 15; i++) {
//		for(int i = 1; i < 8; i++) {
			try {
				Thread.sleep(2000);
				System.out.println("Element Y coordination: "+ element.getLocation().getY());
				System.out.println("Screen bottom: "+(driver.manage().window().getSize().getHeight()));
				if(element.getLocation().getY() <= driver.manage().window().getSize().getHeight() - 260) {
					
					break;
				}
				logger.info("element.getLocation().getY(): "+element.getLocation().getY());
			} catch (Exception e) {
				e.printStackTrace();
			}
//			swipeDown();
			addCapture("swipe to find element : " + i, true);
			swipeCoordination(fromX, fromY, toX, toY, duration);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:"+ i);
		}
		
        System.out.println("Swipe looping done...");
		if (element == null) {
			logger.info("element not found after swipe 10 times");
			throw new RuntimeException("element not found");
		}
		
		System.out.println("element found...");
		int y = element.getLocation().getY();
		logger.info("element.getLocation().getY(): "+y);
		int swipeLittleTimes = 0;
//		while (y < 450 || y > 1550) {
		while (y < display_y_min || y > display_y_max) {
			logger.info("element is not shown in screen,y is " + y);
			if (y < display_y_min) {
				swipeUpLittle();
				logger.info("swipe Up Little...");
			} else if (y > display_y_max) {
				swipeDownLittle();
				logger.info("swipe Down Little...");
			} 
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 2) {
				System.err.println("swipe error");
				break;
			}
		}
		logger.info("exiting function");
		delay(2);
		return element;
	}
	
	
	
	public MobileElement swipeUpUntilElementVisible(MobileElement element) {
		logger.info("func: swipe UP Until Element Visible...");
		for(int i = 1; i < 11; i++) {
			try {
//				if(element.getLocation().getY() >= 400) {
				if(element.getLocation().getY() >= display_y_min*3 && element.isDisplayed()) {
					break;
				}
			} catch (Exception e) {
			}
//			dragCoordinates(driver, 450, 600, 450, 1400, swipe_time);
			dragCoordinates(driver, swipe_little_x_small, swipe_y_small, swipe_little_x_small, swipe_y_big, swipe_time);
		}
        
		if (element == null) {
			logger.info("element not found after swipe 10 times");
			throw new RuntimeException("element not found");
		}
		int y = element.getLocation().getY();
		int swipeLittleTimes = 0;
//		while (y < 450 || y > 1550) {
		while (y < display_y_min || y > display_y_max) {
			logger.info("element is not shown in screen,y is " + y);
//			dragCoordinates(driver, 450, 700, 450, 900, swipe_time);
			dragCoordinates(driver, swipe_little_x_small, swipe_little_y_small, swipe_little_x_small, swipe_little_y_big, swipe_time);
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 2) {
				System.err.println("swipe error");
				break;
			}
		}
		delay(2);
		return element;
	}
	
	public MobileElement swipeDownUntilElementVisible(MobileElement element) {
		logger.info("func: swipe DOWN Until Element Visible...");
		for(int i = 1; i < 11; i++) {
			try {
//				if(element.getLocation().getY() >= 400) {
				if(element.getLocation().getY() >= display_y_min) {
					break;
				}
			} catch (Exception e) {
			}
//			dragCoordinates(driver, 450, 1400, 450, 600, swipe_time);
			dragCoordinates(driver, swipe_little_x_small, swipe_y_big, swipe_little_x_small, swipe_y_small, swipe_time);
		}
        
		if (element == null) {
			logger.info("element not found after swipe 10 times");
			throw new RuntimeException("element not found");
		}
		int y = element.getLocation().getY();
		int swipeLittleTimes = 0;
//		while (y < 450 || y > 1550) {
		while (y < display_y_min || y > display_y_max) {
			logger.info("element is not shown in screen,y is " + y);
//			dragCoordinates(driver, 450, 900, 450, 700, swipe_time);
			dragCoordinates(driver, swipe_little_x_small, swipe_little_y_big, swipe_little_x_small, swipe_little_y_small, swipe_time);
			swipeLittleTimes++;
			y = element.getLocation().getY();
			if (swipeLittleTimes > 2) {
				System.err.println("swipe error");
				break;
			}
		}
		return element;
	}
	
	public void swipeCoordination(int fromX, int fromY, int toX, int toY, int duration) {
		dragCoordinates(driver, fromX, fromY, toX, toY, duration);
	}
	
	public void swipeDownOnLeftSide() {
//		dragCoordinates(driver, 230, 1400, 230, 600, swipe_time);
		dragCoordinates(driver, display_x_min, swipe_y_big, display_x_min, swipe_little_y_small, swipe_time);
	}
	
	public void swipeDown() {
		double fromHeight = driver.manage().window().getSize().height * 0.7;
		double toHeight = driver.manage().window().getSize().height * 0.2;
		double width = driver.manage().window().getSize().width * 0.2;
		
		if (strPlatform.contains("IOS")) {
			System.out.println("dragCoordinates: from " + width + "," + fromHeight + ", to " + width + "," + toHeight);
			dragCoordinates(driver, width, fromHeight, width, toHeight, swipe_time);
		}else {
//			dragCoordinates(driver, 450, 1400, 450, 600, swipe_time);
			dragCoordinates(driver, display_x_min, swipe_y_big, display_x_min, swipe_little_y_small, swipe_time);
		}
	}
	
	public void swipeUp() {
		dragCoordinates(driver, display_x_min, swipe_little_y_small, display_x_min, swipe_y_big, swipe_time);
	}
	
	public void swipeUpLittle() {
//		dragCoordinates(driver, 50, 700, 50, 1200, swipe_time);
		dragCoordinates(driver, swipe_x_small, swipe_little_y_small, swipe_x_small, swipe_little_y_big, swipe_time);
	}
	
	public void swipeDownLittle() {
//		dragCoordinates(driver, 450, 1200, 450, 700, swipe_time);
		dragCoordinates(driver, swipe_x_small, swipe_little_y_big, swipe_x_small, swipe_little_y_small, swipe_time);
	}
	
	public void swipeDownLot() {
//		dragCoordinates(driver, 450, 1200, 450, 700, swipe_time);
		dragCoordinates(driver, swipe_x_small, swipe_y_big, swipe_x_small, swipe_y_small, swipe_time);
	}
	
	public MobileElement swipeLeft2ElementVisible(MobileElement element){
		Point center = element.getCenter();
		int ct_y = center.y;
		
		int ele_x = element.getLocation().x;
		//int ele_y = element.getLocation().y;
		int ele_width = Integer.parseInt(element.getAttribute("width"));
		int ele_x_max = ele_x + ele_width;

		int mid_screen_width = screenWidth / 2;
		int from_x = mid_screen_width;
		int from_y = ct_y;
		int to_x = (int)(mid_screen_width * 0.25);
		int to_y = ct_y;
		
		for (int i = 1; i < 15; i++) {
			try {
				Thread.sleep(2000);
				if (ele_x_max <= screenWidth) {
					System.out.println("Element X coordination: " + ele_x);
					System.out.println("Screen width: " + screenWidth);
					break;
				}
				logger.info("element.getLocation().getX(): " + ele_x);
			} catch (Exception e) {
			}
			
			swipeCoordination(from_x, from_y, to_x, to_y, 1500);
			System.out.println("Swiping down...");
			System.out.println("Swiped times:" + i);
		}

		System.out.println("element found...");

		return element;
		
	}
	
	public void swipeRightFromElement(MobileElement element){
		Point point = element.getCenter();
		dragCoordinates(driver,point.x,point.y,0,point.y,swipe_time);
	}
	
	public void swipeRight() {
//		dragCoordinates(driver, 840, 270, 270, 270, swipe_time);
		dragCoordinates(driver, swipe_x_big, swipe_y_small, swipe_x_small, swipe_y_small, swipe_time);
	}
	
	public void swipeRight(int y) {
//		dragCoordinates(driver, 840, 270, 270, 270, swipe_time);
		dragCoordinates(driver, swipe_x_big, y, swipe_x_small, y, swipe_time);
	}
	
	public void swipeRightOnVitalityTopSubmenu() {
		driver.context("NATIVE");
		System.out.println(driver.getContext() + ", list : " + driver.getContextHandles());
		int height = DriverContext.getDriver().manage().window().getSize().height;
		int width = DriverContext.getDriver().manage().window().getSize().width;
		System.out.println("ready to Swipe Submenu");
		dragCoordinates(driver, (int)(width*0.782), (int)(height*0.125), (int)(width*0.106),  (int)(height*0.125), swipe_time);
//		new TouchAction<>(DriverContext.getDriver()).longPress(PointOption.point((int)(width*0.782), (int)(height*0.125))).moveTo(PointOption.point((int)(width*0.106),  (int)(height*0.125))).release()
//		.perform();
		delay(2);
	}
	
	public void swipeRightOnVitalityTopSubmenu(int y) {
		driver.context("NATIVE");
		System.out.println(driver.getContext() + ", list : " + driver.getContextHandles());
		int height = DriverContext.getDriver().manage().window().getSize().height;
		int width = DriverContext.getDriver().manage().window().getSize().width;
		System.out.println("ready to Swipe Submenu");
		dragCoordinates(driver, swipe_x_big, y, swipe_little_x_small,  y, swipe_time);
//		new TouchAction<>(DriverContext.getDriver()).longPress(PointOption.point((int)(width*0.782), (int)(height*0.125))).moveTo(PointOption.point((int)(width*0.106),  (int)(height*0.125))).release()
//		.perform();
		delay(2);
	}
	
	public void swipeLeftMiddle() {
//		dragCoordinates(driver, 1000, 900, 400, 900, swipe_time);
		dragCoordinates(driver, swipe_x_big, swipe_little_y_small, swipe_x_small, swipe_little_y_small, swipe_time);
	}
	
	public void swipeLeftMiddle(int y) {
//		dragCoordinates(driver, 1000, 900, 400, 900, swipe_time);
		dragCoordinates(driver, swipe_x_big, y, swipe_x_small, y, swipe_time);
	}
	
	public void swipeMiddleLeft() {
//		dragCoordinates(driver, 1000, 900, 400, 900, swipe_time);
		dragCoordinates(driver, swipe_x_big, swipe_little_y_small, swipe_x_small, swipe_little_y_small, swipe_time);
	}
	
	public static void pressEnter() {
		driver.executeScript("seetest:client.deviceAction('Enter')");
	}
	
	public MobileElement swipeToBottom(MobileElement element) {

		for(int i = 1; i < 51; i++) {
			swipeDownLittle();
			try {
				System.out.println(element.getLocation().getY());
				System.out.println(display_y_max);
				if(element.getLocation().getY() <= display_y_max) {
					break;
				}
			} catch (Exception e) {
			}
			catch (Error e) {
			}
			addCapture("swipe down to find buttom: " + i, true);
			new TouchAction(driver).press(PointOption.point(50, screenHeight-300)).moveTo(PointOption.point(50, 400)).release().perform();
		}
        
		if (element == null) {
			logger.info("element not found after swipe 50 times");
			throw new RuntimeException("element not found");
		}
		
		delay(1);
		return element;
	}

	public void moveSeekbar(MobileElement element, double percent) {
		// ???????
		int width = element.getSize().getWidth();
		// ????
		int x = element.getLocation().getX() + 40;
		int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
		dragCoordinates(driver, x, y, (int) (width * percent) + x, y, swipe_time);
	}
	
	public void swipeDownLeftPanel() {
		
		if ("IOS_APP".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			dragCoordinates(driver, 220, 1185, 220, 600, 1000);
		} else {
			dragCoordinates(driver, 170, 1185, 170, 600, 1000);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void checkSize(MobileElement element, int width, int height) throws Exception {
		int actualWidth = element.getSize().getWidth();
		int actualHeight = element.getSize().getHeight();
		System.out.println("actualWidth: "+ actualWidth);
		System.out.println("actualHeight: "+ actualHeight);
		
		Assert.assertTrue(width == actualWidth, "check width");
		Assert.assertTrue(height == actualHeight, "check height");
	}

	public void clearAndSendKeys(MobileElement element, String message){
		element.clear();
		element.click();
		client.sendText(message);
		if("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			driver.hideKeyboard();
		}
		
		hideKeyboardByDone();
	}
	
	public void clickAndSendKeys(MobileElement element, String message){
		clickAndSendKeys(element,message,false);
	}
	
	public void clickAndSendKeys(MobileElement element, String message,boolean clear){
		By doneKeybordBy = By.xpath("//*[@text='Done' or @text='完成']");
		element.click();
		delay(1);
		if("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			driver.hideKeyboard();
		}
		
		if(clear){
			delay(1);
			element.clear();
			delay(1);
		}
		
		client.sendText(message);
		if("ANDROID".equalsIgnoreCase(DriverContext.getDeviceOS())) {
			driver.hideKeyboard();
		}else {
			if (AppiumUtil.isElementPresent(doneKeybordBy, 2)) {
				driver.findElement(doneKeybordBy).click();
			}
		}
	}
	
	protected void inputTextField(MobileElement textField,String value){
		delay(2);
		
		clickAndSendKeys(textField,value);
		
		//hideKeyboardByDone();
	}
	
	protected void inputTextFieldAfterClear(MobileElement textField,String value){
		delay(2);
		
		clickAndSendKeys(textField,value,true);
		
		//hideKeyboardByDone();
	}

	public static void swipeScreenUp(AppiumDriver<MobileElement> driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile:swipe", scrollObject);

	}

	public void turnOnInstrumentedAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			driver.context("NATIVE_APP_INSTRUMENTED");
		}
	}

	public void turnOnWebViewAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			driver.context("NATIVE_APP_INSTRUMENTED");
			driver.context("WEBVIEW_1");
		}
	}

	public void turnBackNativeAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			driver.context("NATIVE_APP");
		}
	}

	protected void selectPickerValue(String value){
		System.out.println("expected picker value:"+value);
		driver.context("NATIVE_APP_INSTRUMENTED");

		By by = By.xpath("//*[@class='WKOptionPickerCell' and ./*[@class='UIImageView']]");
		MobileElement defaultOption = waitElementPresent(by);
		Point center = defaultOption.getCenter();
		int height = Integer.parseInt(defaultOption.getAttribute("height"));
		System.out.println(center.x+","+center.y+", height:"+ height);
		driver.context("NATIVE_APP");

		int fromX = screenWidth/2;
		int fromY = center.y+ height;
		int toY = center.y;

		for(int i =0;i<20;i++){
			dragCoordinates(driver,fromX,fromY,fromX,toY,1000);

			clickCoordinate(center.x,center.y);

			by = By.xpath("//*[@class='UIAPicker']/*[1]");
			MobileElement selectedOption = waitElementPresent(by);
			String optionValue = selectedOption.getAttribute("value");
			System.out.println("optionValue:"+optionValue);
			if(optionValue.equals(value)){
				break;
			}
		}

		by = By.xpath("//*[@text='Done']");
		if(isElementPresent(by,3)){
			driver.findElement(by).click();
		}
	}
	
	protected void SwipeUpToSelectPickerValue(String value){
		System.out.println(value);
		
		By by = By.xpath("//*[@class='UIAPicker']/*[1]");
		MobileElement defaultOption = waitElementPresent(by);
		String defaultValue = defaultOption.getAttribute("value");
		System.out.println("Default option Value:"+defaultValue);
		
		if(value.equals(defaultValue)){
			hideKeyboardByDone();
			return;
		}
		
		driver.context("NATIVE_APP_INSTRUMENTED");

		String xpath = "//*[@class='UIPickerColumnView']//*[@alpha='1' and @text='"+defaultValue+"']";
		by = By.xpath(xpath);
		
		if(!isElementPresent(by,3)){
			xpath = "//*[@accessibilityLabel='"+defaultValue+"' and @class='WKOptionPickerCell']";//请选择
			by = By.xpath(xpath);
		}
		
		 MobileElement option = driver.findElement(by);

		Point center = option.getCenter();
		int height = Integer.parseInt(option.getAttribute("height"));
		System.out.println(center.x+","+center.y+", height:"+ height);
		driver.context("NATIVE_APP");

		int fromX = screenWidth/2;
		int fromY = center.y- height;
		int toY = center.y;

		for(int i =0;i<20;i++){
			dragCoordinates(driver,fromX,fromY,fromX,toY,1000);

			clickCoordinate(center.x,center.y);
			
			by = By.xpath("//*[@class='UIAPicker']/*[1]");
			MobileElement selectedOption = waitElementPresent(by);
			String optionValue = selectedOption.getAttribute("value");
			System.out.println("optionValue:"+optionValue);
			if(optionValue.equals(value)){
				break;
			}
		}

		hideKeyboardByDone();
	}

	public static void setHKLocation() {
		client.setLocation("22.302711", "114.177216");

	}
	
	public void clickUntilDone(MobileElement ele) {
		try {
			int click_count = 0;
			while (ele.isEnabled() && click_count < 10) {
				ele.click();
				delay(1);
				click_count ++;
			}
		} catch (Exception e) {
		}
	}
	


	 //@param DD_MMM_YYYY eg:01 Mar 1965

	/*public static void sendDateForIOS(String date) {
		Date today = Calendar.getInstance().getTime();
		String[] today_list = String.valueOf(today).split(" ");
		String[] date_list = date.split(" ");
		String yearXpath = "//*[@text='" + today_list[5] + "']";
		String picker = "//*[@class='UIAPicker']";
		String monthXpath = "//*[@text='" + today_list[1] + "']";
		MobileElement b_Done_Keybord = driver.findElement(By.xpath("//*[@text='Done' or @text='完成']"));
		String day = date_list[0];
		String month = date_list[1];
		String year = date_list[2];
		
		System.out.println("Select Year...");
		driver.findElement(By.xpath(yearXpath)).click();
		scrollToListItem(picker, year);
		b_Done_Keybord.click();
		
		System.out.println("Select Month...");
		driver.findElement(By.xpath(monthXpath)).click();
		scrollToListItem(picker, month);
		b_Done_Keybord.click();
		
		System.out.println("Select Day...");
		String xpath;
		if(day.startsWith("0")){
			xpath = "(//*[@text='"+day.substring(1)+"' and @y>0])[1]";
		}else{
			xpath = "(//*[@text='" + day + "'])[last()]";
		}
		System.out.println(xpath);
		driver.findElement(By.xpath(xpath)).click();
		b_Done_Keybord.click();
	}*/
	
	public void switchInstrumentedAndroid() {
		if (DriverContext.getDeviceOS().contains("ANDROID")) {
			driver.context("NATIVE_APP_INSTRUMENTED");
			driver.context("NATIVE_APP");
		}
	}
}

