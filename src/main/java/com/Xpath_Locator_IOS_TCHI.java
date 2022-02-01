package com;

public class Xpath_Locator_IOS_TCHI{

	public static final String MyObservatory = "//*[@text='我的天文台']";
	public static final String menuBtn = "//*[@id='目錄, 左邊彈出選單']";
	public static final String iosSettingBtn = "//*[@id='設定' and ./preceding-sibling::*[@class='UIAImage']]";
	public static final String languageBtn = "//*[@text='繁體中文']";
	public static final String iosBackBtn = "//*[@id='返回']";
	public static final String nineDayForecastBtn = "//*[@text='九天預報']";
	public static final String nineDayForecastTab = "//*[@text='九天預報, 分頁按鈕, 第2個共3個, 可橫掃顯示更多分頁']";
	public static final String tomorrowDate = "((//*[@class='UIATable'])[3]/*[@class='UIAView']/*[@class='UIAStaticText'])[2]";
	public static final String tomorrowWeather = "((//*[@class='UIATable'])[3]/*[@class='UIAView']/*[@class='UIAStaticText'])[3]";
	public static final String tomorrowDateiPad = "(//*[@class='UIATable']/*[@class='UIAView']/*[@class='UIAStaticText'])[2]";
	public static final String tomorrowWeatheriPad = "(//*[@class='UIATable']/*[@class='UIAView']/*[@class='UIAStaticText'])[3]";

}
