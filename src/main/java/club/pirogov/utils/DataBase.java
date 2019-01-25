package club.pirogov.utils;

import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBase {
	 //  Database credentials
	
	  
	private Connection connection = null;
	private Statement st;
	private WorkWithMainConfig config = new WorkWithMainConfig();
	protected static Logger logger = LogManager.getLogger();
	
	public void connectToDb() {

		logger.trace("open connection to PostgreSQL JDBC");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("PostgreSQL JDBC Driver is not found. Include it in your library path ");
			e.printStackTrace();
			return;
		}

		logger.debug("PostgreSQL JDBC Driver successfully connected");
		String connectionString = "jdbc:postgresql://" + config.getDbDataProp("dbAddress") + ":"
				+ config.getDbDataProp("dbPort") + "/" + config.getDbDataProp("dbName");
		try {

			connection = DriverManager.getConnection(connectionString, config.getDbDataProp("dbUserName"),
					config.getDbDataProp("dbUserPass"));

		} catch (SQLException e) {
			logger.fatal("Connection Failed");
			logger.trace(e);
			Assertions.fail("Test Failed");
			return;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			logger.fatal("Get error while connection");
			logger.error("in "+this.getClass().getName()+" caught:\r\n" + e);
	        Assertions.fail("Test Failed");
		}

		if (connection != null) {
			logger.debug("You successfully connected to database now");
		} else {
			logger.error("Failed to make connection to database");
		}

	}
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("DB Connection close");
		} else {
			logger.error("Failed to DB Connection close");
		}
	}
	
	public String executeQueryToString(String SQLquery) {
		String resultString = "";
		if (connection != null) {
			try {
				
				st = connection.createStatement();
				
				st.executeQuery(SQLquery);
				ResultSet rs = st.getResultSet();
				
				int x = rs.getMetaData().getColumnCount();
				
				while (rs.next()) {
					for (int i = 1; i <= x; i++) {
						resultString=resultString+rs.getString(i) + "\t";
					}
					resultString=resultString+"\n\r";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	public List<String> executeQueryToList(String SQLquery) {
		List<String> resultList = new ArrayList<String>();
		String resultString="";
		if (connection != null) {
			try {
				
				st = connection.createStatement();
				
				st.executeQuery(SQLquery);
				ResultSet rs = st.getResultSet();
				
				int x = rs.getMetaData().getColumnCount();
				
				while (rs.next()) {
					resultString="";
					for (int i = 1; i <= x; i++) {
						resultString=resultString+rs.getString(i) + "\t";
					}
					resultList.add(resultString);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	public Map<String,String> executeQueryToMap(String SQLquery) {
		Map<String,String> resultMap = new HashMap<String,String>();
		
		if (connection != null) {
			try {
				
				st = connection.createStatement();
				st.executeQuery(SQLquery);
				ResultSet rs = st.getResultSet();
				
				int x = rs.getMetaData().getColumnCount();
				
				while (rs.next()) {
					for (int i = 1; i <= x; i++) {
						resultMap.put(rs.getMetaData().getColumnName(i),rs.getString(i));
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	
	public List<Map<String,String>> executeQueryToListOfMap(String SQLquery) {
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		Map<String,String> rowMap = new HashMap<String,String>();
		
		if (connection != null) {
			try {
				
				st = connection.createStatement();
				
				st.executeQuery(SQLquery);
				ResultSet rs = st.getResultSet();
				
				int x = rs.getMetaData().getColumnCount();
				
				while (rs.next()) {
					rowMap.clear();
					for (int i = 1; i <= x; i++) {
						rowMap.put(rs.getMetaData().getColumnName(i),rs.getString(i));
						
					}
					
					resultList.add(new HashMap<String,String>(rowMap));
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			logger.error("no connection, can't run query DB");
		}
		return resultList;
	}
	
	
	
}

