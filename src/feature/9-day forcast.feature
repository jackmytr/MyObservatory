Feature: MyObservatory testing
	Description: This feature tests 9-Day Forecast features

Background:
Given User is on main page

	@TC @Forecast
		#Tomorrow weather forecast check in 9-Day forecast - TChi
	Scenario: MyObs_001
		When User selects desired language
		And User taps on 9-Day Forecast button
		Then User checks tomorrow weather forecast

	@Eng @Forecast
		#Tomorrow weather forecast check in 9-Day forecast - Eng
	Scenario: MyObs_002
		When User selects desired language
		And User taps on 9-Day Forecast button
		Then User checks tomorrow weather forecast

	@SC @Forecast
		#Tomorrow weather forecast check in 9-Day forecast - SChi
	Scenario: MyObs_003
		When User selects desired language
		And User taps on 9-Day Forecast button
		Then User checks tomorrow weather forecast



