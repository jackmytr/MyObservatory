package stepdefinition;

import base.Base;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import page.CommonPage;

public class CommonStep extends Base {
	
	private CommonPage common = new CommonPage();

		@Given("User is on main page")
		public void checkMainPage() throws Throwable {
			System.out.println("Checking if user is on main page");
			common.checkMainPage();
		}

		@When("User selects desired language")
		public void selectLang() throws Throwable {
			System.out.println("Selecting language");
			common.selectLangOpt();
		}
}

	