Feature: Preclerance Regression Testing

 
Scenario: Testcase 1190
	Given Start of testing create report file
	Given User login to "QA" Environment
	When User navigate to "Admin"
	And User expand "Company Templates"
	Then "Preclear Rules" options appear
 
Scenario: Testcase 1191
	Given User login to "QA" Environment
	When User navigate to "Admin"
	And User expand "Company Templates"
	And User selects "Preclear Rule" "Gift & Entertainment"
	Then "Preclear Rule" "Gift & Entertainment" page displays expected columns and export button