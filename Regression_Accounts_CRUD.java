package RegressionFramework.DataRecords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import ActionModules.Supervisor_Action;
import ActionModules.DataRecords.Accounts.AccountActions_CRUD;
import Utility.ConstantVariables;
import Utility.Labels;

public class Regression_Accounts_CRUD implements Runnable {
	private WebDriver driver = null;
	private Supervisor_Action Supervisor_Actions;
	private String chromeDriverLoc = "chromedriver.exe";
	
	public Regression_Accounts_CRUD() {
		  Supervisor_Actions = new Supervisor_Action();		
	}
	
	public void setChromeDriverLocation(String chromedriverLocation) {
		chromeDriverLoc = chromedriverLocation;
	}
	
    @Override
	public void run() {		
	  try {
		if(ConstantVariables.Browser.equals("Firefox")) 	driver = new FirefoxDriver();  
		else if(ConstantVariables.Browser.equals("Chrome")) {
			String exePath = chromeDriverLoc;
			System.setProperty("webdriver.chrome.driver", exePath);
			
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("-incognito");
			
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver", exePath);		
			driver = new ChromeDriver(capabilities);
		} else if(ConstantVariables.Browser.equals("IE")) {
			System.setProperty("webdriver.ie.driver","IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if(ConstantVariables.Browser.equals("Headless")) {
		}
		driver.manage().window().maximize();
		driver.get(ConstantVariables.Site);

		// login to supervisor account
		Supervisor_Actions.Login_User(driver, ConstantVariables.Username, ConstantVariables.Password);
		AccountActions_CRUD AccountAction_CRUDs = new AccountActions_CRUD();
		// CRUD Account
		AccountAction_CRUDs.createAccount(driver, "Account "+ConstantVariables.Trial+" "+Labels.todayDateLabel(), 0, 0, 0);
		AccountAction_CRUDs.verifyAccount(driver, "Create Account", "Account "+ConstantVariables.Trial+" "+Labels.todayDateLabel(), 0);
		AccountAction_CRUDs.updateAccount(driver, "Account "+ConstantVariables.Trial+" "+Labels.todayDateLabel(), "Account Edited "+ConstantVariables.Trial+" "+Labels.todayDateLabel(), 0, 0, 0, 0);
		AccountAction_CRUDs.verifyAccount(driver, "Edit Account", "Account Edited "+ConstantVariables.Trial+" "+Labels.todayDateLabel(), 0);
		AccountAction_CRUDs.deleteAccount(driver, "Account Edited "+ConstantVariables.Trial+" "+Labels.todayDateLabel());
		AccountAction_CRUDs.verifyAccount(driver, "Delete Account", "Account Edited "+ConstantVariables.Trial+" "+Labels.todayDateLabel(), 0);
/*
		// check for the broker email
		Supervisor_Action.recordResult("New Broker Account", 4, 0, "Data Records"); 
		ConstantVariables.tracker.appendText("Verify New Broker Notification...");
		if(Supervisor_Action.dialogChoice("Check your email, did you receive a Broker Account notification for the accounts you just created?\nNOTE: this may take a few minutes.")) {
			System.out.println("SUCCESS: new broker account notification received");  
			Supervisor_Action.recordResult(Boolean.TRUE, 4, 1, "Data Records"); 
			ConstantVariables.tracker.appendText("PASSED: New Broker Account notification received\n");
		} else {
			Supervisor_Action.recordResult(Boolean.FALSE, 4, 1, "Data Records"); 
			System.out.println("New Broker Notification FAILED");
			ConstantVariables.tracker.appendText("FAILED: New Broker Notification NOT Received\n");
		}
*/
		// logout and close browser
		Supervisor_Actions.Logout_User(driver);
		driver.quit();
	  } catch(Exception e) { System.out.println("LOCKED browser"); driver.close(); }
	}
}
