package stepdefinition;

import base.Base;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import page.CommonPage;
import page.ForecastServicesPage;

public class ForcastServicesStep extends Base {
	
	private ForecastServicesPage forecastServices = new ForecastServicesPage();
	private CommonPage common = new CommonPage();

		@And("User taps on 9-Day Forecast button")
		public void tap9DayForecastBtn() throws Throwable {
			System.out.println("Tapping 9-Day Forecast button");
			common.clickMenuBtn();
			forecastServices.tapNineDayForecastBtn();
		}

		@Then("User checks tomorrow weather forecast")
		public void checkTomorrowWeatherForecast() throws Throwable {
			System.out.println("Checking tomorrow's weather forecast");
			forecastServices.checkNineDayForecastPage();
			forecastServices.checkTomorrowWeatherForecast();
		}
}

	