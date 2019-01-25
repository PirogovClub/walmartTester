package club.pirogov.utils;

import org.junit.jupiter.api.Assertions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HtmlTable {

	protected WebDriver driver;
	protected Map<String, String> tableHeaderBaseOnCol = new HashMap<String, String>();
	protected Map<String, String> tableHeaderBaseOnValue = new HashMap<String, String>();
	protected Map<String, String> tableRow = new HashMap<String, String>();
	protected List<Map<String, String>> tableBody = new ArrayList<Map<String, String>>();
	protected static Logger logger = LogManager.getLogger();

	public List<Map<String, String>> getNamedTableBody() {
		// Return table body but with column names instead of column number
		List<Map<String, String>> tableBodyToReturn = new ArrayList<Map<String, String>>();
		Map<String, String> workingtableRow = new HashMap<String, String>();

		for (Map<String, String> mapElement : tableBody) {
			for (Map.Entry<String, String> pair : mapElement.entrySet()) {
				workingtableRow.put(tableHeaderBaseOnCol.get(pair.getKey()), pair.getValue());
			}
			tableBodyToReturn.add(new HashMap<String, String>(workingtableRow));
		}

		return tableBodyToReturn;

	}

	public List<Map<String, String>> getTableBody() {
		return tableBody;
	}

	protected void setTableBody(List<Map<String, String>> tableBody) {
		this.tableBody = tableBody;
	}

	public HtmlTable(By tablesToParse, WebDriver driver) {
		this.driver = driver;
		Integer row_num, col_num;

		WebElement table_element = driver.findElement(tablesToParse);

		String tmpString = "./thead/tr/th";

		List<WebElement> th_collection = table_element.findElements(By.xpath(tmpString));

		col_num = 1;
		tableHeaderBaseOnCol.clear();
		tableHeaderBaseOnValue.clear();
		for (WebElement tdElement : th_collection) {
			this.tableHeaderBaseOnCol.put(col_num.toString(), tdElement.getText());
			this.tableHeaderBaseOnValue.put(tdElement.getText(), col_num.toString());
			col_num++;
		}

		tmpString = "./tbody/tr";

		List<WebElement> tr_collection = table_element.findElements(By.xpath(tmpString));

		row_num = 1;
		tableBody.clear();
		for (WebElement trElement : tr_collection) {

			List<WebElement> td_collection = trElement.findElements(By.xpath("td"));

			col_num = 1;
			tableRow.clear();
			for (WebElement tdElement : td_collection) {
				tableRow.put(col_num.toString(), tdElement.getText());

				col_num++;
			}
			tableBody.add(new HashMap<String, String>(tableRow));

			row_num++;
		}
		logger.debug("Read table " + tablesToParse.toString());
	}

	public int getRows() {
		return tableBody.size();
	}

	public int getCols() {
		return tableBody.get(0).size();
	}

	public String getCellValue(int col, int row) {
		String returnString = "";
		returnString = tableBody.get(row - 1).get(col);
		return returnString;
	}

	public String getCellValue(String col, int row) {
		String returnString = "";

		returnString = tableBody.get((Integer) row - 1).get(tableHeaderBaseOnValue.get(col));
		return returnString;
	}

	public String getRowString(int row) {
		String returnString = "";
		for (int j = 1; j < getCols() + 1; j++) {
			returnString = returnString + getCellValue(j, row) + "|";
		}

		return returnString;
	}

	public void printTable() {

		PrintOuts.doMap(tableHeaderBaseOnCol);
		PrintOuts.doMap(tableHeaderBaseOnValue);
		PrintOuts.doListOfMap(tableBody);

	}

	public void renameHeader(String fromColName, String toColName) {
		PrintOuts.doString("Enter rename from:" + fromColName + " to:" + toColName);
		if (tableHeaderBaseOnValue.containsKey(fromColName)) {

			tableHeaderBaseOnCol.put(tableHeaderBaseOnValue.get(fromColName), toColName);
			tableHeaderBaseOnValue.put(toColName, tableHeaderBaseOnValue.get(fromColName));
			tableHeaderBaseOnValue.remove(fromColName);

		}
	}

	public void convertDateCol(String colName, String toFormat) {
		convertDateCol(colName,toFormat,Locale.forLanguageTag("RU"));
	}
	
	public void convertDateCol(String colName, String toFormat, Locale fromLocale) {
		printTable();
		for (Map<String, String> tableRow : tableBody) {
			PrintOuts.doString(
					"Convert date from " + colName + ": " + tableRow.get(tableHeaderBaseOnValue.get(colName)));
			String newDate = ConverDates.readDateFromNonRULocaleToString(
					tableRow.get(tableHeaderBaseOnValue.get(colName)), toFormat, Locale.ENGLISH);
			tableRow.put(tableHeaderBaseOnValue.get(colName), newDate);
			PrintOuts.doString("Converted date:" + tableRow.get(tableHeaderBaseOnValue.get(colName)));
		}
	}
	
	public void convertDateColEng2Eng(String colName, String fromFormat, String toFormat) {
		printTable();
		for (Map<String, String> tableRow : tableBody) {
			PrintOuts.doString(
					"Convert date from " + colName + ": " + tableRow.get(tableHeaderBaseOnValue.get(colName)));
			String newDate = ConverDates.convertFieldsInLocale(
					tableRow.get(tableHeaderBaseOnValue.get(colName)),
						fromFormat, toFormat, Locale.US, Locale.US);
			
			tableRow.put(tableHeaderBaseOnValue.get(colName), newDate);
			PrintOuts.doString("Converted date:" + tableRow.get(tableHeaderBaseOnValue.get(colName)));
		}
		
	}
	
	public void convertStub(String colName, String fromStub, String toStub) {
		printTable();
		for (Map<String, String> tableRow : tableBody) {
			PrintOuts.doString("Check :" + tableRow.get(tableHeaderBaseOnValue.get(colName))+" on Stub:"+fromStub);
			if (tableRow.get(tableHeaderBaseOnValue.get(colName)).equals(fromStub))
			tableRow.put(tableHeaderBaseOnValue.get(colName), toStub);
			PrintOuts.doString("After check :" + tableRow.get(tableHeaderBaseOnValue.get(colName)));
			
		}
	}

	

}
