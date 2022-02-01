package com;

public class Xpath_Locator_IOS_ENG{

	public static final String MyObservatory = "//*[@text='MyObservatory']";
	public static final String menuBtn = "//*[@id='Menu, left side panel']";
	public static final String iosSettingBtn = "//*[@id='Settings' and ./preceding-sibling::*[@class='UIAImage']]";
	public static final String languageBtn = "//*[@text='English']";
	public static final String iosBackBtn = "//*[@id='Back']";
	public static final String nineDayForecastBtn = "//*[@text='9-Day Forecast']";
	public static final String nineDayForecastTab = "//*[@text='9-Day Forecast, page tab, 2 in amount of 3, swipe horizontally to show more tabs']";
	public static final String tomorrowDate = "((//*[@class='UIATable'])[3]/*[@class='UIAView']/*[@class='UIAStaticText'])[2]";
	public static final String tomorrowWeather = "((//*[@class='UIATable'])[3]/*[@class='UIAView']/*[@class='UIAStaticText'])[3]";
	public static final String tomorrowDateiPad = "(//*[@class='UIATable']/*[@class='UIAView']/*[@class='UIAStaticText'])[2]";
	public static final String tomorrowWeatheriPad = "(//*[@class='UIATable']/*[@class='UIAView']/*[@class='UIAStaticText'])[3]";
}