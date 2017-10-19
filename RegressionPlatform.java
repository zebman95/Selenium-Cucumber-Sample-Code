import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import RegressionFramework.Admin.Regression_Group_CRUD;
import RegressionFramework.Admin.Regression_User_CreateUpdate;
import RegressionFramework.Certifications.Regression_CertificationHeader_CreateUpdate;
import RegressionFramework.Certifications.Regression_CertificationQuestionnaire_CreateUpdate;
import RegressionFramework.Certifications.Regression_CertificationWorkflow;
import RegressionFramework.DataRecords.Regression_Accounts_CRUD;
import RegressionFramework.DataRecords.Regression_Accounts_CRUD_Mobile;
import RegressionFramework.DataRecords.Regression_Holdings_CRUD;
import RegressionFramework.DataRecords.Regression_Transactions_CRUD;
import RegressionFramework.DataRecordsAdvance.Regression_DataRecordTemplates_CreateUpdate;
import RegressionFramework.DataRecordsAdvance.Regression_Workflow_GE;
import RegressionFramework.Preclear.Templates.Regression_Workflow_GE_Preclear;
import RegressionFramework.Preclear.Templates.Regression_Workflow_NBA_Preclear;
import RegressionFramework.Preclear.Templates.Regression_Workflow_OBA_Preclear;
import RegressionFramework.Preclear.Templates.Regression_Workflow_PP_Preclear;
import RegressionFramework.Preclear.Templates.Regression_Workflow_PolCon_Preclear;
import RegressionFramework.Preclear.Templates.Regression_Workflow_Trade_Preclear;
import RegressionFramework.Preclear.Templates.Regression_Workflow_UBA_Preclear;
import Utility.ConstantVariables;
import Utility.ExcelUtils;
import Utility.ProgressTracker;



public class RegressionPlatform extends JFrame {
	ProgressTracker tracker;
	
	// configuration inputs
	JTextField trialLabel;	
	JRadioButton choiceChrome;
	JRadioButton choiceFirefox;
	JRadioButton choiceIE;
	JComboBox environments;
	
	JTextField username;
	JPasswordField password;
	
	// buttons
	JButton runCmd;
	JButton clearCmd;

	/**** checkboxes  ****/
	// data records
	JCheckBox accountCRUD, accountCRUD_mobile;
	JCheckBox holdingCRUD;
	JCheckBox transactionCRUD;
	
	JCheckBox dataRecordCertExemption;
	JCheckBox dataRecordCertified;
	JCheckBox managedAccount;
	
	JCheckBox accountPreclearExempt;
	JCheckBox accountManaged;
	JCheckBox accountFileAttach;
	JCheckBox accountErrorChecking;
	// holdings
	JCheckBox holdingPreclearExempt;
	JCheckBox holdingErrorChecking;
	// transactions
	JCheckBox transactionPreclearExempt;
	JCheckBox transactionErrorChecking;
	// advance data records
	JCheckBox templatesCU;
	JCheckBox geWorkflow;
	JCheckBox obaWorkflow;
	JCheckBox polconWorkflow;
	JCheckBox ppWorkflow;
	JCheckBox pageTiming;
	
	// G&E
	JCheckBox geTemplate;
	JCheckBox geCRUD;
	JCheckBox geCustomFields;
	JCheckBox geFileAttach;
	JCheckBox geErrorChecking;
	// OBA
	JCheckBox obaTemplate;
	JCheckBox obaCRUD;
	JCheckBox obaCustomFields;
	JCheckBox obaFileAttach;
	JCheckBox obaErrorChecking;
	// PolCon
	JCheckBox polconTemplate;
	JCheckBox polconCRUD;
	JCheckBox polconCustomFields;
	JCheckBox polconFileAttach;
	JCheckBox polconErrorChecking;
	// Private Placement (holdings and transactions)
	JCheckBox ppTemplate;
	JCheckBox ppHoldingsCRUD;
	JCheckBox ppTransactionsCRUD;
	JCheckBox ppHoldingsCustomFields;
	JCheckBox ppTransactionCustomFields;
	JCheckBox ppFileAttach;
	JCheckBox ppHoldingErrorChecking;
	JCheckBox ppTransactionErrorChecking;
	
	// certifications
	JCheckBox certHeaderCreateUpdate;
	JCheckBox certQuestionnaireCreateUpdate;
	JCheckBox questionnaireCheck;
	JCheckBox certWorkflow;
	
	// admin
	JCheckBox groupCRUD;
	JCheckBox approvalGroups;
	JCheckBox userCreateUpdate;
	JCheckBox userInviteRegister;
	
	 // preclear
	JCheckBox ruleListedSecurity, ruleShortSwing, ruleSecurityType, ruleTransactionType, ruleDefaultRule, ruleExemptBySecurityType, ruleFirmTradesBlackoutPeriod,
			  ruleExternalRule, ruleIndustryRule, ruleMarketCap;
	JCheckBox ruleIPO, rulePP, ruleGE, ruleOBA, rulePolCon, ruleMM, ruleNewBrokerAccount, ruleUpdateBrokerAccount;
	JCheckBox companyListCRUD;
	JCheckBox GEpreclearRQ, OBApreclearRQ, PolConpreclearRQ, PPpreclearRQ, NBApreclearRQ, UBApreclearRQ, TradepreclearRQ;
	
	/**** checkboxes  ****/
	

	public static void main(String[] args) {
		new RegressionPlatform();
	}	

	public RegressionPlatform() {
	     setTitle("ComplySci Selenium Regression Platform");
	     
		JPanel frontend = new JPanel();
		frontend.setLayout(new BoxLayout(frontend, BoxLayout.Y_AXIS));
	     
	     // create the tabbed regression panes
	     JTabbedPane platformTabs = new JTabbedPane();	     
	     platformTabs.addTab("Data Records", null, addDataRecordsTab(), "Regression Test Data Records");
	     platformTabs.addTab("Admin", null, addAdminTab(), "Regression Test Admin Items");
	     platformTabs.addTab("Certifications", null, addCertificationsTab(), "Regression Test Certifications");	
	     platformTabs.addTab("Preclearance", null, addPreclearTab(), "Regression Test Preclearance");
	     platformTabs.addTab("Communications", null, addCommunicationsTab(), "Regression Test Communications");
	     platformTabs.addTab("Emulation", null, addEmulationTab(), "Regression Test Emulation");
	     platformTabs.addTab("Reporting", null, addReportingTab(), "Regression Test Reporting");
	     platformTabs.addTab("Mobile", null, addMobileTab(), "Regression Test Mobile View");
	     platformTabs.setPreferredSize(new Dimension(1050, 500));
	     
	     // create the configuration options at the bottom
	     frontend.add(platformTabs);
	     frontend.add(addConfigPanel());
	     frontend.add(addExecutionPanel());
	     
	     getContentPane().add(frontend);

		// start a new tracker
		tracker = new ProgressTracker();	        

		setLocation(100, 50);
	     setSize(new Dimension(950, 600));
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     setVisible(true);   
	}

	// returns data record tab panel with regression testing for data recors
	private JComponent addDataRecordsTab() {
		JPanel DataRecordsPanel = new JPanel();
		DataRecordsPanel.setLayout(new BoxLayout(DataRecordsPanel, BoxLayout.Y_AXIS));

		// section for all account-related regression testing
		JPanel dataRecordOptions = new JPanel();
		dataRecordOptions.setBorder(BorderFactory.createTitledBorder("Data Records"));
		dataRecordOptions.setLayout(new BoxLayout(dataRecordOptions, BoxLayout.X_AXIS));
		dataRecordOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel dataRecordsCRUDOptions = new JPanel();
		dataRecordsCRUDOptions.setLayout(new BoxLayout(dataRecordsCRUDOptions, BoxLayout.Y_AXIS));
		dataRecordsCRUDOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		accountCRUD = new JCheckBox("Accounts CRUD");
		holdingCRUD = new JCheckBox("Holdings CRUD");
		transactionCRUD = new JCheckBox("TransactionsCRUD");
		dataRecordsCRUDOptions.add(accountCRUD);
		dataRecordsCRUDOptions.add(holdingCRUD);
		dataRecordsCRUDOptions.add(transactionCRUD);
		dataRecordOptions.add(dataRecordsCRUDOptions);

		JPanel dataRecordsOtherOptions = new JPanel();
		dataRecordsOtherOptions.setLayout(new BoxLayout(dataRecordsOtherOptions, BoxLayout.Y_AXIS));
		dataRecordsOtherOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		dataRecordCertified = new JCheckBox("Certified");		
		dataRecordCertExemption = new JCheckBox("Cert Exemption");		dataRecordCertExemption.setEnabled(false);
		managedAccount = new JCheckBox("Managed Account");				managedAccount.setEnabled(false);
		dataRecordsOtherOptions.add(dataRecordCertified);
		dataRecordsOtherOptions.add(dataRecordCertExemption);
		dataRecordsOtherOptions.add(managedAccount);
		dataRecordOptions.add(dataRecordsOtherOptions);
		

		// jpanel to set data records menu across from left to right
		JPanel advanceDataRecordsMenu = new JPanel();
		advanceDataRecordsMenu.setLayout(new BoxLayout(advanceDataRecordsMenu, BoxLayout.Y_AXIS));
		advanceDataRecordsMenu.setBorder(BorderFactory.createTitledBorder("Advance Data Records"));
		advanceDataRecordsMenu.setAlignmentX(Component.LEFT_ALIGNMENT);

		// section for all G&E-related regression testing
		JPanel dataRecordAdvanceOptions = new JPanel();
		dataRecordAdvanceOptions.setLayout(new BoxLayout(dataRecordAdvanceOptions, BoxLayout.X_AXIS));
		dataRecordAdvanceOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		templatesCU = new JCheckBox("Templates (Create, Update)");
		pageTiming = new JCheckBox("Time Page Loads");
		dataRecordAdvanceOptions.add(templatesCU);
		dataRecordAdvanceOptions.add(pageTiming);
		advanceDataRecordsMenu.add(dataRecordAdvanceOptions);

		// workflow for advance data records
		JPanel dataRecordsWorkflowOptions = new JPanel();
		dataRecordsWorkflowOptions.setLayout(new BoxLayout(dataRecordsWorkflowOptions, BoxLayout.X_AXIS));
		dataRecordsWorkflowOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		geWorkflow = new JCheckBox("Workflow G&E");
		obaWorkflow = new JCheckBox("Workflow OBA");
		polconWorkflow = new JCheckBox("Workflow PolCon");
		ppWorkflow = new JCheckBox("Workflow PP (include transactions)");
		dataRecordsWorkflowOptions.add(geWorkflow);
		dataRecordsWorkflowOptions.add(obaWorkflow);
		dataRecordsWorkflowOptions.add(polconWorkflow);
		dataRecordsWorkflowOptions.add(ppWorkflow);
		advanceDataRecordsMenu.add(dataRecordsWorkflowOptions);
				
		// add all operation categories into panel
		DataRecordsPanel.add(dataRecordOptions);
		DataRecordsPanel.add(advanceDataRecordsMenu);
		
		return DataRecordsPanel;
	}
	
	// returns admin panel with regression testing for admin menu
	private JComponent addAdminTab() {
		JPanel adminPanel = new JPanel();
		adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
		
		// section for user options
		JPanel userOptions = new JPanel();
		userOptions.setBorder(BorderFactory.createTitledBorder("User"));
		userOptions.setLayout(new BoxLayout(userOptions, BoxLayout.Y_AXIS));
		userOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		userCreateUpdate = new JCheckBox("Create & Update");
		userInviteRegister = new JCheckBox("Invite & Register");	userInviteRegister.setEnabled(false);
		userOptions.add(userCreateUpdate);
		userOptions.add(userInviteRegister);

		// section for group options
		JPanel groupOptions = new JPanel();
		groupOptions.setBorder(BorderFactory.createTitledBorder("Group"));
		groupOptions.setLayout(new BoxLayout(groupOptions, BoxLayout.Y_AXIS));
		groupOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		groupCRUD = new JCheckBox("CRUD");
		approvalGroups = new JCheckBox("Approval Sequence");		approvalGroups.setEnabled(false);
		groupOptions.add(groupCRUD);
		groupOptions.add(approvalGroups);

		// add all operation categories into panel
		adminPanel.add(userOptions);		
		adminPanel.add(groupOptions);
		
		return adminPanel;
	}
	
	// returns certification panel with regression testing for certifications
	private JComponent addCertificationsTab() {
		JPanel CertificationPanel = new JPanel();
		CertificationPanel.setLayout(new BoxLayout(CertificationPanel, BoxLayout.Y_AXIS));

		// section for cert header options
		JPanel certHeaderQuestionnaireOptions = new JPanel();
		certHeaderQuestionnaireOptions.setBorder(BorderFactory.createTitledBorder("Header & Questionnaire"));
		certHeaderQuestionnaireOptions.setLayout(new BoxLayout(certHeaderQuestionnaireOptions, BoxLayout.Y_AXIS));
		certHeaderQuestionnaireOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		certHeaderCreateUpdate = new JCheckBox("Header Create & Update");
		certQuestionnaireCreateUpdate = new JCheckBox("Questionnaire Create & Update");
		certWorkflow = new JCheckBox("Certification Workflow");
		certHeaderQuestionnaireOptions.add(certHeaderCreateUpdate);
		certHeaderQuestionnaireOptions.add(certQuestionnaireCreateUpdate);
		certHeaderQuestionnaireOptions.add(certWorkflow);
		
		// add all operation categories into panel
		CertificationPanel.add(certHeaderQuestionnaireOptions);

		return CertificationPanel;
	}
	
	// returns certification panel with regression testing for certifications
	private JComponent addPreclearTab() {
		JPanel PreclearPanel = new JPanel();
		PreclearPanel.setLayout(new BoxLayout(PreclearPanel, BoxLayout.Y_AXIS));
		PreclearPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel PreclearRulesPanel = new JPanel();
		PreclearRulesPanel.setLayout(new BoxLayout(PreclearRulesPanel, BoxLayout.X_AXIS));
		PreclearRulesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		// section for preclear rule trade options
		JPanel preclearTradeRulesOptions = new JPanel();
		preclearTradeRulesOptions.setBorder(BorderFactory.createTitledBorder("Trade Rules (Create & Update)"));
		preclearTradeRulesOptions.setLayout(new BoxLayout(preclearTradeRulesOptions, BoxLayout.Y_AXIS));
		preclearTradeRulesOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		ruleListedSecurity = new JCheckBox("Listed Security");							ruleListedSecurity.setEnabled(false);
		ruleShortSwing = new JCheckBox("Short-Swing");									ruleShortSwing.setEnabled(false);
		ruleSecurityType = new JCheckBox("Security Type");								ruleSecurityType.setEnabled(false);
		ruleTransactionType = new JCheckBox("Transaction Type");						ruleTransactionType.setEnabled(false);
		ruleDefaultRule = new JCheckBox("Default Rule");								ruleDefaultRule.setEnabled(false);
		ruleExemptBySecurityType = new JCheckBox("Exempt by Security Type");			ruleExemptBySecurityType.setEnabled(false);
		ruleFirmTradesBlackoutPeriod = new JCheckBox("Firm Trades Blackout Period");	ruleFirmTradesBlackoutPeriod.setEnabled(false);
		ruleExternalRule = new JCheckBox("External");									ruleExternalRule.setEnabled(false);
		ruleIndustryRule = new JCheckBox("Industry");									ruleIndustryRule.setEnabled(false);
		ruleMarketCap = new JCheckBox("Market Cap");									ruleMarketCap.setEnabled(false);
		preclearTradeRulesOptions.add(ruleListedSecurity);
		preclearTradeRulesOptions.add(ruleShortSwing);
		preclearTradeRulesOptions.add(ruleSecurityType);
		preclearTradeRulesOptions.add(ruleTransactionType);
		preclearTradeRulesOptions.add(ruleDefaultRule);
		preclearTradeRulesOptions.add(ruleExemptBySecurityType);
		preclearTradeRulesOptions.add(ruleFirmTradesBlackoutPeriod);
		preclearTradeRulesOptions.add(ruleExternalRule);
		preclearTradeRulesOptions.add(ruleIndustryRule);
		preclearTradeRulesOptions.add(ruleMarketCap);
		PreclearRulesPanel.add(preclearTradeRulesOptions);
		

		// section for non-trade preclear rule options
		JPanel preclearRuleOptions = new JPanel();
		preclearRuleOptions.setBorder(BorderFactory.createTitledBorder("Non-Trade Rules (Create & Update)"));
		preclearRuleOptions.setLayout(new BoxLayout(preclearRuleOptions, BoxLayout.Y_AXIS));
		preclearRuleOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		ruleIPO = new JCheckBox("IPO");									ruleIPO.setEnabled(false);
		rulePP = new JCheckBox("Private Placement");					rulePP.setEnabled(false);
		ruleGE = new JCheckBox("Gift & Entertainment");					ruleGE.setEnabled(false);
		ruleOBA = new JCheckBox("Outside Affiliation");					ruleOBA.setEnabled(false);
		rulePolCon = new JCheckBox("Political Contribution");			rulePolCon.setEnabled(false);
		ruleMM = new JCheckBox("Market Material");						ruleMM.setEnabled(false);
		ruleNewBrokerAccount = new JCheckBox("New Broker Account");		ruleNewBrokerAccount.setEnabled(false);
		ruleUpdateBrokerAccount = new JCheckBox("Update Broker Account");	ruleUpdateBrokerAccount.setEnabled(false);
		preclearRuleOptions.add(ruleIPO);
		preclearRuleOptions.add(rulePP);
		preclearRuleOptions.add(ruleGE);
		preclearRuleOptions.add(ruleOBA);
		preclearRuleOptions.add(rulePolCon);
		preclearRuleOptions.add(ruleMM);
		preclearRuleOptions.add(ruleNewBrokerAccount);
		preclearRuleOptions.add(ruleUpdateBrokerAccount);
		PreclearRulesPanel.add(preclearRuleOptions);

		// section for non-trade preclear rule options
		JPanel companyListOptions = new JPanel();
		companyListOptions.setBorder(BorderFactory.createTitledBorder("Company List"));
		companyListOptions.setLayout(new BoxLayout(companyListOptions, BoxLayout.Y_AXIS));
		companyListOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		companyListCRUD = new JCheckBox("CRUD Company List");
		companyListOptions.add(companyListCRUD);
		
		JPanel PreclearWorkflow = new JPanel();
		PreclearWorkflow.setBorder(BorderFactory.createTitledBorder("Preclear Workflows (templates, questionnaire, rule, requests)"));
		PreclearWorkflow.setLayout(new BoxLayout(PreclearWorkflow, BoxLayout.X_AXIS));
		PreclearWorkflow.setAlignmentX(Component.LEFT_ALIGNMENT);
		GEpreclearRQ = new JCheckBox("G&E");
		OBApreclearRQ = new JCheckBox("OBA");
		PolConpreclearRQ = new JCheckBox("PolCon");
		PPpreclearRQ = new JCheckBox("PP");
		NBApreclearRQ = new JCheckBox("New Broker Account");
		UBApreclearRQ = new JCheckBox("Update Broker Account");
		TradepreclearRQ = new JCheckBox("Trade");
		PreclearWorkflow.add(GEpreclearRQ);
		PreclearWorkflow.add(OBApreclearRQ);
		PreclearWorkflow.add(PolConpreclearRQ);
		PreclearWorkflow.add(PPpreclearRQ);
		PreclearWorkflow.add(NBApreclearRQ);
		PreclearWorkflow.add(UBApreclearRQ);
		PreclearWorkflow.add(TradepreclearRQ);
		
		PreclearPanel.add(PreclearRulesPanel);
		PreclearPanel.add(companyListOptions);
		PreclearPanel.add(PreclearWorkflow);
		
		return PreclearPanel;		
	}

	// returns certification panel with regression testing for certifications
	private JComponent addCommunicationsTab() {
		
		return new JPanel();
	}
	
	// returns certification panel with regression testing for certifications
	private JComponent addEmulationTab() {
		
		return new JPanel();
	}

	// returns certification panel with regression testing for certifications
	private JComponent addReportingTab() {
		
		return new JPanel();
	}
	
	private JComponent addMobileTab() {
		JPanel MobilePanel = new JPanel();
		MobilePanel.setLayout(new BoxLayout(MobilePanel, BoxLayout.Y_AXIS));

		// section for all account-related regression testing
		JPanel mobileOptions = new JPanel();
		mobileOptions.setBorder(BorderFactory.createTitledBorder("Data Records (mobile)"));
		mobileOptions.setLayout(new BoxLayout(mobileOptions, BoxLayout.X_AXIS));
		mobileOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel mobileDataRecordsCRUDOptions = new JPanel();
		mobileDataRecordsCRUDOptions.setLayout(new BoxLayout(mobileDataRecordsCRUDOptions, BoxLayout.Y_AXIS));
		mobileDataRecordsCRUDOptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		accountCRUD_mobile = new JCheckBox("Accounts CRUD (mobile)");
		mobileDataRecordsCRUDOptions.add(accountCRUD_mobile);
		mobileOptions.add(mobileDataRecordsCRUDOptions);		
		
		MobilePanel.add(mobileOptions);
		return MobilePanel;
	}
	
	// return panel with configuration options to be applied to all regression tests
	private JPanel addConfigPanel() {
		JPanel environmentPanel = new JPanel();
		environmentPanel.setPreferredSize(new Dimension(1050, 100));
		environmentPanel.setBorder(BorderFactory.createTitledBorder("Testing Configuration"));
		// add environment components ////////////////////////////////////////
		// section for selecting environment
		JPanel env = new JPanel();
		env.setBorder(BorderFactory.createTitledBorder("Environment"));
		env.setLayout(new BoxLayout(env, BoxLayout.X_AXIS));
		// radio buttons to select environment to run tests
		  // the environment dropdown listing
		String[] envs = {"Select...","QA","Preview","Production","Jefferies UAT"};
		environments = new JComboBox(envs);	
		environments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  try {
				if(environments.getSelectedItem().equals("QA"))	{
					ExcelUtils.setExcelFile("QA", System.getProperty("user.dir")+"\\settings.xlsx");
					ConstantVariables.Environment = "QA";
				} else if(environments.getSelectedItem().equals("Preview")) {
					ExcelUtils.setExcelFile("Preview", System.getProperty("user.dir")+"\\settings.xlsx");
					ConstantVariables.Environment = "Preview";
				} else if(environments.getSelectedItem().equals("Production")) {
					ExcelUtils.setExcelFile("Production", System.getProperty("user.dir")+"\\settings.xlsx");
					ConstantVariables.Environment = "Production";
				} else if(environments.getSelectedItem().equals("Jefferies UAT")) {
					ExcelUtils.setExcelFile("UAT", System.getProperty("user.dir")+"\\settings.xlsx");
					ConstantVariables.Environment = "UAT";
				}
				ConstantVariables.Site = ExcelUtils.getCellData(0, 0);
				ConstantVariables.Username = ExcelUtils.getCellData(1, 0);
				ConstantVariables.Password = ExcelUtils.getCellData(2, 0);
				username.setText(ConstantVariables.Username);
				password.setText(ConstantVariables.Password);
				
			  } catch(Exception e1) {System.out.println("Error: reading from settings file");}
			}
		});
		env.add(new JLabel("Choose Environment: "));
		env.add(environments);

		// radio buttons to select browser to run tests
		JPanel browse = new JPanel();
		browse.setBorder(BorderFactory.createTitledBorder("Browser"));
		browse.setLayout(new BoxLayout(browse, BoxLayout.X_AXIS));
		choiceChrome = new JRadioButton("Chrome");
		choiceChrome.setSelected(true);
		choiceFirefox = new JRadioButton("Firefox");
		choiceIE = new JRadioButton("IE");				choiceIE.setEnabled(false);
		ButtonGroup browsers = new ButtonGroup();
		browsers.add(choiceChrome);
		browsers.add(choiceFirefox);
		browsers.add(choiceIE);
		browse.add(choiceChrome);
		browse.add(choiceFirefox);
		browse.add(choiceIE);
		// username and password inquiry
		JPanel userdetails = new JPanel();
		userdetails.setBorder(BorderFactory.createTitledBorder("User Details"));
		userdetails.setLayout(new BoxLayout(userdetails, BoxLayout.Y_AXIS));
     	username = new JTextField("", 10);
     	password = new JPasswordField(10);
     	JPanel top = new JPanel();
     	top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
     	top.add(new JLabel("username: "));
     	top.add(username);
     	JPanel bottom = new JPanel();
     	bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
     	bottom.add(new JLabel("password: "));
     	bottom.add(password);
     	userdetails.add(top);
     	userdetails.add(bottom);
		
		// trial label
		JPanel trial = new JPanel();
		trial.setBorder(BorderFactory.createTitledBorder("Trial Label"));
		trial.setLayout(new BoxLayout(trial, BoxLayout.X_AXIS));
		trialLabel = new JTextField(10);
		trial.add(new JLabel("Enter Identifier: "));
		trial.add(trialLabel);
		
		// END add environment components ////////////////////////////////////////
		environmentPanel.add(env);
		environmentPanel.add(browse);
		environmentPanel.add(userdetails);
		environmentPanel.add(trial);
		
		return environmentPanel;
	}
	
	// returns panel for execution buttons and contains the action listeners to run operations
	private JPanel addExecutionPanel() {
		JPanel executionPanel = new JPanel();
		executionPanel.setPreferredSize(new Dimension(1050, 50));
		executionPanel.setLayout(new BoxLayout(executionPanel, BoxLayout.X_AXIS));
		runCmd = new JButton("Run");
		runCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// prepare the selected environment
				ConstantVariables.Username = username.getText();
				ConstantVariables.Password = new String(password.getPassword());

				if(environments.getSelectedItem().equals("QA"))	{
					ConstantVariables.Environment = "QA";
					ConstantVariables.Site = "https://vce.qa.complysci.com"; 
				}
				else if(environments.getSelectedItem().equals("Preview")) {
					ConstantVariables.Environment = "Preview";
					ConstantVariables.Site = "https://preview-balyasny.complysci.com"; 
				}
				else if(environments.getSelectedItem().equals("Production")) {
					ConstantVariables.Environment = "Production";
					ConstantVariables.Site = "https://productioncsi1.complysci.com";
				}
				else if(environments.getSelectedItem().equals("Jefferies UAT")) {
					ConstantVariables.Environment = "UAT";
					ConstantVariables.Site = "https://jefferiesuat.complysci.com/Membership/LogInNoSSO";
				}	
				System.out.println("environment: "+ConstantVariables.Environment);
				System.out.println("site: "+ConstantVariables.Site);
				System.out.println("User: "+ConstantVariables.Username);
				System.out.println("Identifier: "+trialLabel.getText());
												
				// assign the selected browser
				if(choiceChrome.isSelected())		ConstantVariables.Browser = "Chrome";
				else if(choiceFirefox.isSelected())	ConstantVariables.Browser = "Firefox";
				else if(choiceIE.isSelected())	ConstantVariables.Browser = "IE";

				// assign the trial label identifier
				ConstantVariables.Trial = trialLabel.getText();

				// hide the UI and begin the regression testing
				setVisible(false);

				// create new report
				ConstantVariables.Record = true;
				String[] tabs = {"Data Records","Admin","Certifications","Preclearance","Communications","Emulation","Performance"};
				try { ExcelUtils.createReport(System.getProperty("user.dir")+"\\Reports\\Selenium Results_"+(new Date().toString().replaceAll(":", "-"))+".xlsx", tabs); } catch(Exception e2) { System.out.println("Exception thrown at clean slate: "+e2.getMessage()); }	
				
				// launch the operations selected (where the threads will run)
				// Data Records panel
				
				ExecutorService threadPool = Executors.newFixedThreadPool(6);
				
				if(accountCRUD.isSelected())						threadPool.execute((new Regression_Accounts_CRUD()));	
				if(holdingCRUD.isSelected())						threadPool.execute((new Regression_Holdings_CRUD()));	
				if(transactionCRUD.isSelected())					threadPool.execute((new Regression_Transactions_CRUD()));	
/*				if(dataRecordCertified.isSelected())				Regression_Certified.test();
*/				if(templatesCU.isSelected())						threadPool.execute((new Regression_DataRecordTemplates_CreateUpdate()));	
				if(geWorkflow.isSelected())							threadPool.execute((new Regression_Workflow_GE()));
/*				if(obaWorkflow.isSelected())						Regression_Workflow_OBA.test();
				if(polconWorkflow.isSelected())						Regression_Workflow_PolCon.test();
				if(ppWorkflow.isSelected())							Regression_Workflow_PP.test();
				if(pageTiming.isSelected())							Performance_PageTiming.test();
*/				// Certifications panel
				if(certHeaderCreateUpdate.isSelected())				threadPool.execute((new Regression_CertificationHeader_CreateUpdate()));
				if(certQuestionnaireCreateUpdate.isSelected())		threadPool.execute((new Regression_CertificationQuestionnaire_CreateUpdate()));
				if(certWorkflow.isSelected())						threadPool.execute((new Regression_CertificationWorkflow()));
				// Admin panel
				if(userCreateUpdate.isSelected())					threadPool.execute((new Regression_User_CreateUpdate()));
				if(groupCRUD.isSelected())							threadPool.execute((new Regression_Group_CRUD()));
/*				// Preclear panel
				if(companyListCRUD.isSelected())					Regression_CompanyList_CRUD.test();
*/				if(GEpreclearRQ.isSelected())						threadPool.execute((new Regression_Workflow_GE_Preclear()));
				if(OBApreclearRQ.isSelected())						threadPool.execute((new Regression_Workflow_OBA_Preclear()));
				if(PolConpreclearRQ.isSelected())					threadPool.execute((new Regression_Workflow_PolCon_Preclear()));
				if(PPpreclearRQ.isSelected())						threadPool.execute((new Regression_Workflow_PP_Preclear()));
				if(NBApreclearRQ.isSelected())						threadPool.execute((new Regression_Workflow_NBA_Preclear()));
				if(UBApreclearRQ.isSelected())						threadPool.execute((new Regression_Workflow_UBA_Preclear()));
				if(TradepreclearRQ.isSelected())					threadPool.execute((new Regression_Workflow_Trade_Preclear()));

				// mobile versions
				if(accountCRUD_mobile.isSelected())		{	System.out.println("run mobile version");		threadPool.execute((new Regression_Accounts_CRUD_Mobile())); }
				
				threadPool.shutdown();
				while(!threadPool.isTerminated()) {}		// wait until all selected threads are done
				
				tracker.appendText("ENDING Testing Session.\n");
				try { Thread.sleep(3000); } catch(Exception e3) { System.out.println("could not sleep for 2 seconds."); }
	
				setVisible(true);				
			}
		});
		
		// Clear button that unchecks all the checkboxes
		clearCmd = new JButton("Clear");
		clearCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					// enter in actions for clearing checkboxes
			}
		});

		executionPanel.add(runCmd);
		executionPanel.add(new JLabel("   "));
		executionPanel.add(clearCmd);
		
		return executionPanel;		
	}
	
}