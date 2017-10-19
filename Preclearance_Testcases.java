package Cucumber;

import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import ActionModules.Supervisor_Action;
import Utility.ConstantVariables;
import Utility.ExcelUtils;
import Utility.TestcaseVariables;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Preclearance_Testcases {
	private static WebDriver driver = null;
	private static Supervisor_Action Supervisor_Actions = null;
	private static WebDriverWait wait = null;
	
	@Before
	public void startupDriver() {
		
		String exePath = "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);	
		ChromeOptions options = new ChromeOptions();
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");		
		
		Supervisor_Actions = new Supervisor_Action();
		
		driver = new ChromeDriver(capabilities);				
		wait = new WebDriverWait(driver, 7);
	}
	
	@After
	public void shutdownDriver() {
		Supervisor_Actions.Logout_User(driver);
		driver.quit();		
	}
		
	
	@Given("^Start of testing create report file$")
	public void start_of_testing_create_report_file() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		String[] tabs = {"Preclearance"};
		try { ExcelUtils.createReport("\\TestcaseReports\\Selenium Testcase Results_"+(new Date().toString().replaceAll(":", "-"))+".xlsx", tabs); } catch(Exception e2) { System.out.println("Exception thrown at clean slate: "+e2.getMessage()); }			
		System.out.println("create report file");
	}	
	
	
	@Given("^User login to \"([^\"]*)\" Environment$")
	public void user_login_to_Environment(String environment) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		ConstantVariables.Username = "cdub0";
		ConstantVariables.Password = "P455word";
		ConstantVariables.Site = "https://vce.qa.complysci.com";
//		ConstantVariables.Trial = "A";
		ConstantVariables.Record = true;
		
		System.out.println("login to the site");
		driver.manage().window().maximize();
		driver.get(ConstantVariables.Site);		
		Supervisor_Actions.Login_User(driver, ConstantVariables.Username, ConstantVariables.Password);
	}

	@When("^User navigate to \"([^\"]*)\"$")
	public void user_navigate_to(String menu) throws Throwable {
		if(menu.equals("Admin"))		Supervisor_Actions.navigateAdmin(driver);
		
		System.out.println("navigate to "+menu);
	}

	@When("^User expand \"([^\"]*)\"$")
	public void user_expand(String menu) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Supervisor_Actions.nextActionClick(driver, wait, "Company Templates", 4);
		System.out.println("expand "+menu);
	}

	@Then("^\"([^\"]*)\" options appear$")
	public void options_appear(String options) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		if(options.equals("Preclear Rules")) {
			String rule1 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"csi-body\"]/div/div[2]/div/div/div[2]/div/div[2]/ul/li[3]/ul/li[1]/a");
			String rule2 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"csi-body\"]/div/div[2]/div/div/div[2]/div/div[2]/ul/li[3]/ul/li[2]/a");
			String rule3 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"csi-body\"]/div/div[2]/div/div/div[2]/div/div[2]/ul/li[3]/ul/li[3]/a");
			String rule4 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"csi-body\"]/div/div[2]/div/div/div[2]/div/div[2]/ul/li[3]/ul/li[4]/a");
			String rule5 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"csi-body\"]/div/div[2]/div/div/div[2]/div/div[2]/ul/li[3]/ul/li[7]/a");
			String rule6 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"csi-body\"]/div/div[2]/div/div/div[2]/div/div[2]/ul/li[3]/ul/li[8]/a");
			
			System.out.println("rule1: |"+rule1+"|");
			System.out.println("rule2: |"+rule2+"|");
			System.out.println("rule3: |"+rule3+"|");
			System.out.println("rule4: |"+rule4+"|");
			System.out.println("rule5: |"+rule5+"|");
			System.out.println("rule6: |"+rule6+"|");
			
			int validation = 0;
			Supervisor_Actions.recordResult("Gift And Entertainment", TestcaseVariables.TC_1190_GE, 0, "Preclearance");
			Supervisor_Actions.recordResult("check Gift and Entertainment appears as a selection", TestcaseVariables.TC_1190_GE, 2, "Preclearance");
			if(rule1.equals("Gift And Entertainment")) {	Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1190_GE, 1, "Preclearance"); validation++; }
			else										Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1190_GE, 1, "Preclearance");

			Supervisor_Actions.recordResult("Outside Affiliation", TestcaseVariables.TC_1190_OBA, 0, "Preclearance");
			Supervisor_Actions.recordResult("check Outside Affilation appears as a selection", TestcaseVariables.TC_1190_OBA, 2, "Preclearance");
			if(rule2.equals("Outside Affiliation")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1190_OBA, 1, "Preclearance"); validation++; }
			else									Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1190_OBA, 1, "Preclearance");

			Supervisor_Actions.recordResult("Political Contribution", TestcaseVariables.TC_1190_PolCon, 0, "Preclearance");
			Supervisor_Actions.recordResult("check Political Contribution appears as a selection", TestcaseVariables.TC_1190_PolCon, 2, "Preclearance");
			if(rule3.equals("Political Contribution")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1190_PolCon, 1, "Preclearance"); validation++; }
			else									   Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1190_PolCon, 1, "Preclearance");
			
			Supervisor_Actions.recordResult("Private Placement", TestcaseVariables.TC_1190_PP, 0, "Preclearance");
			Supervisor_Actions.recordResult("check Private Placement appears as a selection", TestcaseVariables.TC_1190_PP, 2, "Preclearance");
			if(rule4.equals("Private Placement")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1190_PP, 1, "Preclearance"); validation++; }
			else								  Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1190_PP, 1, "Preclearance");
			
			Supervisor_Actions.recordResult("New Broker Accounts", TestcaseVariables.TC_1190_NBA, 0, "Preclearance");
			Supervisor_Actions.recordResult("check New Broker Accounts appears as a selection", TestcaseVariables.TC_1190_NBA, 2, "Preclearance");
			if(rule5.equals("New Broker Accounts")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1190_NBA, 1, "Preclearance"); validation++; }
			else									Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1190_NBA, 1, "Preclearance");

			Supervisor_Actions.recordResult("Update Broker Accounts", TestcaseVariables.TC_1190_UBA, 0, "Preclearance");
			Supervisor_Actions.recordResult("check Update Broker Accounts appears as a selection", TestcaseVariables.TC_1190_UBA, 2, "Preclearance");
			if(rule6.equals("Update Broker Accounts")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1190_UBA, 1, "Preclearance"); validation++; }
			else									   Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1190_UBA, 1, "Preclearance");


			Assert.assertTrue(validation == 6);
		}		
		System.out.println("view Preclear Rule Options");
	}

	@When("^User selects \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_selects(String menuType, String menuOption) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		if(menuType.equals("Preclear Rule")) {
			if(menuOption.equals("Gift & Entertainment")) {
				Supervisor_Actions.nextActionClick(driver, wait, "//*[@id=\"csi-body\"]/div/div[2]/div/div/div[2]/div/div[2]/ul/li[3]/ul/li[1]/a");
				String pageHeader = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"breadcrumb-list\"]/ol/li[3]");
				
				Supervisor_Actions.recordResult("Gift and Entertainment page", 7, 0, "Preclearance");
				Supervisor_Actions.recordResult("check Gift and Entertainment page appears when selected from Preclearance Rule", 7, 2, "Preclearance");
				if(pageHeader.equals("Gift and Entertainment")) Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_GEPAGE, 1, "Preclearance");
				else											Supervisor_Actions.recordResult(Boolean.FALSE, 7, 1, "Preclearance");

				Assert.assertTrue(pageHeader.equals("Gift and Entertainment"));
			}
		}
		System.out.println("view "+menuType+"->"+menuOption);
	}

	@Then("^\"([^\"]*)\" \"([^\"]*)\" page displays expected columns and export button$")
	public void page_displays_expected_columns_and_export_button(String menuType, String ruleType) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		String col1 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[3]/div/table/thead/tr/th[2]/a[2]");
		String col2 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[3]/div/table/thead/tr/th[3]/a[2]");
		String col3 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[3]/div/table/thead/tr/th[4]/a[2]");
		String col4 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[3]/div/table/thead/tr/th[5]/a[2]");
		String col5 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[3]/div/table/thead/tr/th[6]/a[2]");
		String col6 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[3]/div/table/thead/tr/th[7]/a[2]");
		String col7 = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[3]/div/table/thead/tr/th[8]/a[2]");
		
		System.out.println("col1: |"+col1+"|");
		System.out.println("col2: |"+col2+"|");
		System.out.println("col3: |"+col3+"|");
		System.out.println("col4: |"+col4+"|");
		System.out.println("col5: |"+col5+"|");
		System.out.println("col6: |"+col6+"|");
		System.out.println("col7: |"+col7+"|");
				
		int validation = 0;
		Supervisor_Actions.recordResult("Rule Template column", TestcaseVariables.TC_1191_COL_RULETEMPLATE, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Rule Template column exists", TestcaseVariables.TC_1191_COL_RULETEMPLATE, 2, "Preclearance");
		if(col1.equals("Rule Template")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_COL_RULETEMPLATE, 1, "Preclearance"); validation++; }
		else				 			 Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_COL_RULETEMPLATE, 1, "Preclearance");
		
		Supervisor_Actions.recordResult("Version column", TestcaseVariables.TC_1191_COL_VERSION, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Version column exists", TestcaseVariables.TC_1191_COL_VERSION, 2, "Preclearance");
		if(col2.equals("Version")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_COL_VERSION, 1, "Preclearance"); validation++; }
		else						Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_COL_VERSION, 1, "Preclearance");

		Supervisor_Actions.recordResult("Date Created column", TestcaseVariables.TC_1191_COL_DATECREATED, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Date Created column exists", TestcaseVariables.TC_1191_COL_DATECREATED, 2, "Preclearance");
		if(col3.equals("Date Created")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_COL_DATECREATED, 1, "Preclearance"); validation++; }
		else							Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_COL_DATECREATED, 1, "Preclearance");
		
		Supervisor_Actions.recordResult("Created By column", TestcaseVariables.TC_1191_COL_CREATEDBY, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Created By column exists", TestcaseVariables.TC_1191_COL_CREATEDBY, 2, "Preclearance");
		if(col4.equals("Created By")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_COL_CREATEDBY, 1, "Preclearance"); validation++; }
		else							Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_COL_CREATEDBY, 1, "Preclearance");

		Supervisor_Actions.recordResult("Date Modified column", TestcaseVariables.TC_1191_COL_DATEMODIFIED, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Date Modified column exists", TestcaseVariables.TC_1191_COL_DATEMODIFIED, 2, "Preclearance");
		if(col5.equals("Date Modified")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_COL_DATEMODIFIED, 1, "Preclearance"); validation++; }
		else							  Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_COL_DATEMODIFIED, 1, "Preclearance");

		Supervisor_Actions.recordResult("Modified By column", TestcaseVariables.TC_1191_COL_MODIFIEDBY, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Modified By column exists", TestcaseVariables.TC_1191_COL_MODIFIEDBY, 2, "Preclearance");
		if(col6.equals("Modified By")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_COL_MODIFIEDBY, 1, "Preclearance"); validation++; }
		else							Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_COL_MODIFIEDBY, 1, "Preclearance");
		
		Supervisor_Actions.recordResult("Status column", TestcaseVariables.TC_1191_COL_STATUS, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Status column exists", TestcaseVariables.TC_1191_COL_STATUS, 2, "Preclearance");
		if(col7.equals("Status")) { Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_COL_STATUS, 1, "Preclearance"); validation++; }
		else						Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_COL_STATUS, 1, "Preclearance");
		
		String button = (String)Supervisor_Actions.retrieveFieldValue(driver, wait, "//*[@id=\"TradeList\"]/div[1]/div/div[2]/button");
		System.out.println("button is "+button);
		
		Supervisor_Actions.recordResult("Export button", TestcaseVariables.TC_1191_BUTTON_EXPORT, 0, "Preclearance");
		Supervisor_Actions.recordResult("check Export button is on the page", TestcaseVariables.TC_1191_BUTTON_EXPORT, 2, "Preclearance");
		if(button.equals("Export"))	{ Supervisor_Actions.recordResult(Boolean.TRUE, TestcaseVariables.TC_1191_BUTTON_EXPORT, 1, "Preclearance"); validation++; }
		else						Supervisor_Actions.recordResult(Boolean.FALSE, TestcaseVariables.TC_1191_BUTTON_EXPORT, 1, "Preclearance");
		
		System.out.println("check G&E Preclear Rule page");
	}
	
}
