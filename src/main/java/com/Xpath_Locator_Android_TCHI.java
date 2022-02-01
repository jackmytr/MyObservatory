package com;


public class Xpath_Locator_Android_TCHI{
	public static final String MyObservatory = "//*[@text='我的天文台']";
	public static final String moreOptionsBtn = "//*[@contentDescription='More options']";
	public static final String menuBtn = "//*[@contentDescription='Navigate up']";
	public static final String languageBtn = "(//*[@class='android.widget.ListView']/*/*/*/*[@id='title'])[4]";
	public static final String TCOption = "//*[@text='繁體中文']";
	public static final String EngOption = "//*[@text='English']";
	public static final String SCOption = "//*[@text='简体中文']";
	public static final String nineDayForecastBtn = "//*[@text='九天預報']";
	public static final String nineDayForecastTab = "//*[@class='android.widget.LinearLayout']/*[@text='九天預報']";
	public static final String tomorrowDate = "(((//*[@id='mainAppSevenDayView']/*[@class='android.widget.LinearLayout'])[1]/*[@class='android.widget.LinearLayout'])[1]/*[@text])[1]";
	public static final String tomorrowTemperature = "((//*[@id='mainAppSevenDayView']/*[@class='android.widget.LinearLayout'])[1]/*[@class='android.widget.LinearLayout']/*[@class='android.widget.LinearLayout']/*[@text])[1]";
	public static final String tomorrowHumidity = "((//*[@id='mainAppSevenDayView']/*[@class='android.widget.LinearLayout'])[1]/*[@class='android.widget.LinearLayout']/*[@class='android.widget.LinearLayout']/*[@text])[2]";
}


