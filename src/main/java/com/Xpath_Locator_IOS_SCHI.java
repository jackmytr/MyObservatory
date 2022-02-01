package com;

public class Xpath_Locator_IOS_SCHI{

	public static final String MyObservatory = "//*[@text='我的天文台']";
	public static final String menuBtn = "//*[@text='目录, 左边弹出选单']";
	public static final String iosSettingBtn = "//*[@id='设定' and ./preceding-sibling::*[@class='UIAImage']]";
	public static final String languageBtn = "//*[@text='简体中文']";
	public static final String iosBackBtn = "//*[@id='返回']";
	public static final String nineDayForecastBtn = "//*[@text='九天预报']";
	public static final String nineDayForecastTab = "//*[@text='九天预报, 分页按钮, 第2个共3个, 可横扫显示更多分页']";
	public static final String tomorrowDate = "((//*[@class='UIATable'])[3]/*[@class='UIAView']/*[@class='UIAStaticText'])[2]";
	public static final String tomorrowWeather = "((//*[@class='UIATable'])[3]/*[@class='UIAView']/*[@class='UIAStaticText'])[3]";
	public static final String tomorrowDateiPad = "(//*[@class='UIATable']/*[@class='UIAView']/*[@class='UIAStaticText'])[2]";
	public static final String tomorrowWeatheriPad = "(//*[@class='UIATable']/*[@class='UIAView']/*[@class='UIAStaticText'])[3]";

}


