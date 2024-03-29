package net.firstpartners.sample.ExcelDataRules;

import java.io.File;
import java.io.IOException;

import net.firstpartners.drools.FileRuleLoader;
import net.firstpartners.drools.IRuleLoader;
import net.firstpartners.drools.SpreadSheetRuleRunner;
import net.firstpartners.drools.data.RuleSource;
import net.firstpartners.spreadsheet.SpreadSheetOutputter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.DroolsParserException;

/**
 * Sample showing how we can read and manipulate data from excel
 * Read Ranges from Excel, Convert to a format that rules can use
 * 
 * Based on Sample from Apache POI
 * 
 * @author paulbrowne
 * 
 */
public class ExcelDataExample {

	private static Log log = LogFactory.getLog(ExcelDataExample.class);

	private static final String EXCEL_DATA_FILE = "war/sampleresources/ExcelDataRules/chocolate-data.xls";

	private static final String EXCEL_OUTPUT_FILE = "war/sampleresources/ExcelDataRules/chocolate-output.xls";

	// the name of the sheet the we log files to
	private static final String EXCEL_LOG_WORKSHEET_NAME = "log";

	//The rule files that we are using
	private static final String[] RULES_FILES = new String[] {
	"war/sampleresources/ExcelDataRules/log-then-modify-rules.drl"};

	//Handle to common utility file
	private final  SpreadSheetRuleRunner commonSpreadsheetUtils = new SpreadSheetRuleRunner(new FileRuleLoader());


	/**
	 * Read an excel file and spit out what we find.
	 * 
	 * @param args
	 *            Expect one argument that is the file to read.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ExcelDataExample thisSample = new ExcelDataExample();
		thisSample.runExcelDataExample();
	}

	/**
	 * The Actual Test
	 * @return
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 */
	public HSSFWorkbook runExcelDataExample() throws IOException, DroolsParserException, ClassNotFoundException{

		//Handle to the Spreadsheet Rule Runner and Rule file loader
		IRuleLoader ruleLoader = new FileRuleLoader();
		SpreadSheetRuleRunner ruleRunner = new SpreadSheetRuleRunner(ruleLoader);

		//Start Integrate in new RuleRunner
		//Identify where the rules are stored
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);


		//Get the URL
		File excelDataFile = new File(EXCEL_DATA_FILE);

		//Call the rule engine passing in the excel data file, the rules we want to use, and name of the spreadsheet that we log rules to
		HSSFWorkbook wb = ruleRunner.callRules(excelDataFile,ruleSource, EXCEL_LOG_WORKSHEET_NAME);


		//Output the workbook as a file,
		SpreadSheetOutputter.outputToFile(wb, EXCEL_LOG_WORKSHEET_NAME);

		//Return the same workbook to the calling method
		return wb;




	}

}
