package ActionModules.DataRecords.Accounts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import ActionModules.Supervisor_Action;
import Utility.ConstantVariables;
import Utility.Labels;
import Utility.ProgressTracker;

public class AccountActions_CRUD extends Supervisor_Action {	
	private ProgressTracker tracker;
	
	public AccountActions_CRUD() {
		tracker = new ProgressTracker();
	}

	public void createAccount(WebDriver driver, String accountName, int preclearExempt, int managedAccount, int certExempt) {
		  try {
				WebDriverWait wait = new WebDriverWait(driver, 7);
				// go to data records
				navigateDataRecords(driver);
				// select Add button
				nextActionClick(driver, wait, "+ Add",4);
				try { Thread.sleep(5000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
				// enter Account Name field			
				nextFieldEnter(driver, wait, "//*[@id=\"AccountName\"]", accountName, true);
				// enter Account # field
				nextFieldEnter(driver, wait, "//*[@id=\"AccountNumber\"]", accountName, true);
				// select Broker/Custodian dropdown											   
//				nextActionClick(driver, wait, "//*[@id=\"body_container\"]/form/div/div[2]/div/div/div[5]/div/span/span/span[2]");
				this.nextFieldEnter(driver, wait, "AccountProviderId_input", "Citigroup", 1, false);
				try { Thread.sleep(700); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
				this.dropdownSelect(driver, "AccountProviderId_listbox", "Citigroup", true);
				
				// select from dropdown
//				nextActionClick(driver, wait, "//*[@id=\"AccountProviderId_listbox\"]/li[1]");
				// select Relationship dropdown	
//				nextActionClick(driver, wait, "//*[@id=\"body_container\"]/form/div/div[2]/div/div/div[6]/div/span/span/span[2]/span");
				// select from dropdown
//				nextActionClick(driver, wait, "//*[@id=\"RelationshipId_listbox\"]/li");
				// select Today link for Date Opened	
				nextActionClick(driver, wait, "Today", 4);
				// select checkboxes if applicable
				if(preclearExempt == 1)		nextActionClick(driver, wait, "//label[contains(@for,'isPreclearExempt')]");
				if(managedAccount == 1)		nextActionClick(driver, wait, "//label[contains(@for,'IsManagedAccount')]");
				if(certExempt == 1)			nextActionClick(driver, wait, "//label[contains(@for,'isExcludedFromCert')]");
				// select Add button
				nextActionClick(driver, wait, "//input[contains(@value, 'Add')]");
				// update progress tracker
				tracker.appendText("Created Account....");
		  } catch(Exception e) { 
			  recordResult(Boolean.FALSE, ConstantVariables.CREATE_ACCOUNT, 1, "Data Records");
			  System.out.println("FAILED create account with EXCEPTION"); 
			  tracker.appendText("ERROR: failed create Account\n");
		  }
		}
	
	
	public void updateAccount(WebDriver driver, String accountSearchName, String newAccountName, int preclearExempt, int managedAccount, int certExempt, int emulation) {
		  try {
			WebDriverWait wait = new WebDriverWait(driver, 7);
			navigateDataRecords(driver);
			try { Thread.sleep(5000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
			// retrieve record from grid
			retrieve_account(driver, wait, accountSearchName);			
			// select link to expected result search
			try { Thread.sleep(3000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
			nextActionClick(driver, wait, accountSearchName, 4);
			// select edit button
			nextActionClick(driver, wait, "Edit", 4);
			// update account # field		
			nextFieldEnter(driver, wait, "//*[@id=\"AccountNumber\"]", newAccountName, true);
			nextFieldEnter(driver, wait, "//*[@id=\"AccountName\"]", newAccountName, true);
			// select checkboxes if applicable
			if(preclearExempt == 1)		nextActionClick(driver, wait, "//label[contains(@for,'isPreclearExempt')]");
			if(managedAccount == 1)		nextActionClick(driver, wait, "//label[contains(@for,'IsManagedAccount')]");
			if(certExempt == 1)			nextActionClick(driver, wait, "//label[contains(@for,'isExcludedFromCert')]");
			// enter in employee and supervisor notes (if not in emulation mode)
			if(emulation == 0) {
				nextFieldEnter(driver, wait, "SupervisorNote", "Sup Note", 1, true);
				nextActionClick(driver, wait, "AddNoteButton", 3);
//				nextFieldEnter(driver, wait, "EmployeeNote", "Emp Note", 1, true);
//				nextActionClick(driver, wait, "AddNoteButtonEmp", 3);
			} else {
				nextFieldEnter(driver, wait, "EmployeeNote", "Emp Note", 1, true);
				nextActionClick(driver, wait, "AddNoteButtonEmp", 3);
			}
			// save changes									 
			nextActionClick(driver, wait, "Save", 1);
			// update progress tracker
			tracker.appendText("Update Account...");

		  } catch(Exception e) { 
			  	recordResult(Boolean.FALSE, ConstantVariables.UPDATE_ACCOUNT, 1, "Data Records"); 
			  	System.out.println("FAILED update account with EXCEPTION at Update Account");
				tracker.appendText("ERROR: failed update Account\n");
		  }
		}
	
	
	public void deleteAccount(WebDriver driver, String accountname) {
	      try {
			WebDriverWait wait = new WebDriverWait(driver, 7);
			navigateDataRecords(driver);
			try { Thread.sleep(5000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
			// retrieve record from grid
			retrieve_account(driver, wait, accountname);			
			try { Thread.sleep(3000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
			// select link to expected result search
			nextActionClick(driver, wait, accountname, 4);
			try { Thread.sleep(3000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
			// select delete button
			nextActionClick(driver, wait, "DeleteAccount", 1);
			try { Thread.sleep(700); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }
			// select to verify to delete account
			
			try { 
				nextActionClick(driver, wait, "//button[@class='btn main-button-negative delete-confrim']");
			} catch (Exception e1) {
				System.out.println("Exception: attempt secondary delete");
				nextActionClick(driver, wait, "#myModalHasData > div > div > div.modal-footer > button.csi-btn.delete-confrim", 2);
			}
			
			// update progress tracker
			tracker.appendText("Delete Account...");

		  } catch(Exception e) { 
			  	WebDriverWait wait = new WebDriverWait(driver, 7);
			    String isHoldingDetails = (String)retrieveFieldValue(driver, wait, "//*[@id=\"body-content\"]/form/div[1]/div[2]/div[2]/div/h5");
			    if(isHoldingDetails.contains("Holding Details")) 
			    	nextActionClick(driver, wait, "//*[@id=\"myModalHasData\"]/div/div/div[3]/button[2]");
			    else {
			    	recordResult(Boolean.FALSE, ConstantVariables.DELETE_ACCOUNT, 1, "Data Records"); 
			    	System.out.println("FAILED delete account with EXCEPTION");
			    	tracker.appendText("ERROR: failed delete Account\n");
			    }
		  }
	}
	
	public void verifyAccount(WebDriver driver, String stage, String account, int emulate) {
		WebDriverWait wait = new WebDriverWait(driver, 7);
		navigateDataRecords(driver);
		try { Thread.sleep(6000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }	
		
		if(stage.equals("Create Account")) {
		  // enter how account is verified for creation in report
		  recordResult("Create Account", ConstantVariables.CREATE_ACCOUNT, 0, "Data Records");
		  recordResult("account# and account name.", ConstantVariables.CREATE_ACCOUNT, 2, "Data Records");
		  try {
			// retrieve record from grid
			retrieve_account(driver, wait, account);			
			// get cert status											  
			String certStatus = (String)retrieveFieldValue(driver, wait, "//*[@id=\"AccountList\"]/div[4]/table/tbody/tr/td[12]/span");
			// select link to expected result search
			nextActionClick(driver, wait, account, 4);
			
			// check value to confirm creation (using xpath)				
			String accountNumber = (String)retrieveFieldValue(driver, wait, "#body_container > div > form > div.csi-form.col-sm-12.clearfix > div.col-lg-7.col-sm-12.form-view > div > table:nth-child(1) > tbody > tr.list-group-item.main-subTitle.clearfix > td:nth-child(2)", 2);
			String accountName = (String)retrieveFieldValue(driver, wait, "#body_container > div > form > div.csi-form.col-sm-12.clearfix > div.col-lg-7.col-sm-12.form-view > div > table:nth-child(1) > tbody > tr:nth-child(4) > td:nth-child(2)", 2);
			if(accountNumber.contains(account) && accountName.contains(account) && certStatus.contains("New")) {	
				System.out.println("SUCCESS: account created");  
				tracker.appendText("PASSED: account created\n");
				recordResult(Boolean.TRUE, ConstantVariables.CREATE_ACCOUNT, 1, "Data Records"); 
			} else { 
				recordResult(Boolean.FALSE, ConstantVariables.CREATE_ACCOUNT, 1, "Data Records"); 
				System.out.println("Create Account FAILED");
				tracker.appendText("FAILED: account not created\n");
			}

		  } catch(Exception e) { 
			  recordResult(Boolean.FALSE, ConstantVariables.CREATE_ACCOUNT, 1, "Data Records"); 
			  System.out.println("FAILED account create with EXCEPTION");
			  tracker.appendText("ERROR: account not created\n");
		  }

		} else if(stage.equals("Edit Account")) {
		  // enter how account is verified for update in report
		  recordResult("Update Account", ConstantVariables.UPDATE_ACCOUNT, 0, "Data Records");
		  recordResult("account #, account name, employee note, supervisor note", ConstantVariables.UPDATE_ACCOUNT, 2, "Data Records");
		  try {
				// retrieve record from grid
				retrieve_account(driver, wait, account);			
				// get cert status
				String certStatus = (String)retrieveFieldValue(driver, wait, "#AccountList > div.k-grid-content > table > tbody > tr:nth-child(1) > td:nth-child(12) > span", 2);
				// select link to expected result search
				nextActionClick(driver, wait, account, 4);

				// allow for page to fully load with the employee and supervisor notes
				try { Thread.sleep(5000); } catch(Exception e) { System.out.println("could not sleep for 2 seconds."); }	
	
				// gather data from account view		
				String AccountNum = (String)retrieveFieldValue(driver, wait, "#body_container > div > form > div.csi-form.col-sm-12.clearfix > div.col-lg-7.col-sm-12.form-view > div > table:nth-child(1) > tbody > tr.list-group-item.main-subTitle.clearfix > td:nth-child(2)", 2);
				String AccountName = (String)retrieveFieldValue(driver, wait, "#body_container > div > form > div.csi-form.col-sm-12.clearfix > div.col-lg-7.col-sm-12.form-view > div > table:nth-child(1) > tbody > tr:nth-child(4) > td:nth-child(2)", 2);

				String SupervisorNote = (emulate == 1) ? "Sup Note" : (String)retrieveFieldValue(driver, wait, "#body_container > div > form > div.csi-form.col-sm-12.clearfix > div.col-lg-7.col-sm-12.form-view > div > table:nth-child(1) > tbody > tr:nth-child(11) > td:nth-child(2) > div > ul > li > i > span", 2);
				// compare values to determine if account was updated
				if(AccountNum.equals(account+"  ") && AccountName.equals(account+"  ") &&
					SupervisorNote.contains("Sup Note") && certStatus.contains("Updated")) {	
					System.out.println("SUCCESS: account edited");  
					tracker.appendText("PASSED: account updated\n");
					recordResult(Boolean.TRUE, ConstantVariables.UPDATE_ACCOUNT, 1, "Data Records"); 
				} else { 
					recordResult(Boolean.FALSE, ConstantVariables.UPDATE_ACCOUNT, 1, "Data Records"); 
					System.out.println("Edit Account FAILED");
				}
				
		  } catch(Exception e) { 
			  recordResult(Boolean.FALSE, ConstantVariables.UPDATE_ACCOUNT, 1, "Data Records"); 
			  System.out.println("FAILED account edit with EXCEPTION"); 
			  tracker.appendText("ERROR: account not updated\n");
		  }
		} else if(stage.equals("Delete Account")) {
		  // enter how account is verified as deleted in report
		  recordResult("Delete Account", ConstantVariables.DELETE_ACCOUNT, 0, "Data Records");
		  recordResult("No Results Found", ConstantVariables.DELETE_ACCOUNT, 2, "Data Records");
		  try {
			// retrieve record from grid
			retrieve_account(driver, wait, account);						  
			// check No Results Found! is returned
			String result = (String)retrieveFieldValue(driver, wait, "//*[@id=\"AccountList\"]/div[4]/table/tbody/tr/td");
			if(result.equals("No Results Found!")) {	
				System.out.println("SUCCESS: account delete");  
				tracker.appendText("PASSED: account deleted\n");
				recordResult(Boolean.TRUE, ConstantVariables.DELETE_ACCOUNT, 1, "Data Records"); 
			} else { 
				recordResult(Boolean.FALSE, ConstantVariables.DELETE_ACCOUNT, 1, "Data Records"); 
				System.out.println("Delete Account FAILED");
				tracker.appendText("FAILED: account not delete\n");
			}
		  } catch(Exception e) { 
			  recordResult(Boolean.FALSE, ConstantVariables.DELETE_ACCOUNT, 1, "Data Records");   
			  System.out.println("FAILED account delete with EXCEPTION");
			  tracker.appendText("ERROR: account not delete\n");
		  }
		}
	}			
	
	
	public void retrieve_account(WebDriver driver, WebDriverWait wait, String theAccount) {
		// open Account # filter from account grid
		nextActionClick(driver, wait, "//*[@id=\"AccountList\"]/div[3]/div/table/thead/tr/th[7]/a[1]/span");
		// enter search criteria
		nextFieldEnter(driver, wait, "body > div.k-animation-container > form > div:nth-child(1) > input:nth-child(3)", theAccount, 2, true);
		// submit search criteria
		nextActionClick(driver, wait, "body > div.k-animation-container > form > div:nth-child(1) > div:nth-child(7) > button.k-button.k-primary", 2);
		
	}
	
}
