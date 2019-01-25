package club.pirogov.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataTable {

	protected List<Map<String, String>> tableBody = new ArrayList<Map<String, String>>();
	protected static Logger logger = LogManager.getLogger();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableBody == null) ? 0 : tableBody.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// Sorting is SIGNIFICANT taken into account
		logger.info("Checking tables for equality");
		if (this == obj)
			return true;
		if (obj == null) {
			logger.debug("Object to compare == null");
			return false;
		}
		if (getClass() != obj.getClass()) {
			logger.debug("Objects classes are not the same == null");
			return false;
		}
		DataTable other = (DataTable) obj;
		if (tableBody == null) {
			if (other.tableBody != null) {
				logger.debug("Initial Object == null");
				return false;
			}
		}
		if (tableBody.size() != other.tableBody.size()) {
			logger.debug("Tables are different size");
			return false;
		}
		Map<String, String> tblRow;
		for (int index = 0; index < tableBody.size(); index++) {
			tblRow = tableBody.get(index);
			for (Map.Entry<String, String> entry : tblRow.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				PrintOuts.doString("key:" + key + " value:" + value);
				PrintOuts.doString("Compare: " + other.tableBody.get(index).get(key));

				if (!value.equals((String) other.tableBody.get(index).get(key))) {
					logger.debug("Original");
					logger.debug("For key " + key + " result is " + value);
					logger.debug("Not Equal to");
					logger.debug("For key " + key + " result is " + other.tableBody.get(index).get(key));

					return false;
				}

			}
		}

		return true;
	}

	public List<Map<String, String>> getTableBody() {
		return tableBody;
	}

	public void setTableBody(List<Map<String, String>> tableBody) {
		this.tableBody = tableBody;
		for (Map<String, String> tblRow : tableBody) {
			for (Map.Entry<String, String> entry : tblRow.entrySet()) {
				if (entry.getValue() == null) {
					entry.setValue("not specified");
				}
			}
		}
	}

	public void printTable() {

		PrintOuts.doListOfMap(tableBody);
	}

	public void renameKeys(String fromColName, String toColName) {
		PrintOuts.doString("Enter rename from:" + fromColName + " to:" + toColName);
		for (Map<String, String> row : tableBody) {
			if (row.containsKey(fromColName)) {
				// PrintOuts.doString("Before Rename");
				// PrintOuts.doMap(tableHeaderBaseOnValue);
				// PrintOuts.doMap(tableHeaderBaseOnCol);
				row.put(toColName, row.get(fromColName));
				row.remove(fromColName);
				// PrintOuts.doString("After Rename");
				// PrintOuts.doMap(tableHeaderBaseOnValue);
				// PrintOuts.doMap(tableHeaderBaseOnCol);
			}
		}
	}

	public void removeCol(String ColName) {
		for (Map<String, String> row : tableBody) {
			if (row.containsKey(ColName)) {
				row.remove(ColName);
			}
		}
	}

	public void convertInt2Bool(String colName) {
		// TODO Auto-generated method stub
		PrintOuts.doString("Enter convertInt2Bool: ");
		for (Map<String, String> tableRow : tableBody) {
			PrintOuts.doString("Convert Int from " + colName + ": " + tableRow.get(colName));
			String toReturnBool = "NO";
			switch (Integer.valueOf(tableRow.get(colName))) {
			case 0:
				toReturnBool = "NO";
				break;
			default:
				toReturnBool = "YES";
				break;
			}
			tableRow.put(colName, toReturnBool);
			PrintOuts.doString("Converted Bool:" + tableRow.get(colName));
		}
		PrintOuts.doListOfMap(tableBody);
	}
}
