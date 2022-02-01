package test;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
		monochrome = true,
		//plugin = { "pretty", "json:target/cucumber-reports/Cucumber.json" },
		features = {"src/feature"},
		glue = { "stepdefinition" },
		tags = "@Forecast"
		)

public class TestRun extends AbstractTestNGCucumberTests {
	public static ITestContext itc;
	
	@BeforeSuite
	public void beforeSuite(ITestContext itc) throws Exception {
		this.itc = itc;
	}
}
