package club.pirogov.utils;

import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Properties;

public class WorkWithMainConfig implements WorkWithConfing {
	
	private  String currentPropName= null;
	private  final String configPropFile = "src/main/resources/config.properties";
	private  final String dataPropFile = "src/main/resources/TestsData.properties";
	private  final String dbPropFile = "src/main/resources/Db.properties";
	private  Properties configProp = new Properties();
	private  Properties testDataProp = new Properties();
	private  Properties dbDataProp = new Properties();
	
	public WorkWithMainConfig(){
		try {
			   InputStream input = new FileInputStream(configPropFile);
				// load a properties file
				configProp.load(input);
				input.close();
				
				input = new FileInputStream(dataPropFile);
				testDataProp.load(input);
				input.close();
				
				input = new FileInputStream(dbPropFile);
				dbDataProp.load(input);
				input.close();
				

		} catch (Throwable e) {
			System.out.println("caught:\r\n" + e);
			Assertions.fail("Test Failed");
		}
	}
	
	/* (non-Javadoc)
	 * @see utils.WorkWithConfing#getConfigProp(java.lang.String)
	 */
	@Override
	public String getConfigProp(String key) {
		return configProp.getProperty(key);
	}
	
	/* (non-Javadoc)
	 * @see utils.WorkWithConfing#getTestDataProp(java.lang.String)
	 */
	@Override
	public String getTestDataProp(String key) {
		return testDataProp.getProperty(key);
	}
	
	/* (non-Javadoc)
	 * @see utils.WorkWithConfing#getDbDataProp(java.lang.String)
	 */
	@Override
	public String getDbDataProp(String key) {
		return dbDataProp.getProperty(key);
	}

	/* (non-Javadoc)
	 * @see utils.WorkWithConfing#setTestDataProp(java.lang.String, java.lang.String)
	 */
	@Override
	public  Object setTestDataProp(String key, String value) {
		return testDataProp.setProperty(key, value);
	}

	/* (non-Javadoc)
	 * @see utils.WorkWithConfing#saveTestDataPropToFile()
	 */
	@Override
	public void saveTestDataPropToFile() throws IOException {
		FileOutputStream output = new FileOutputStream(dataPropFile);
		// save a properties To file
		testDataProp.store(output, "check it out");
		// closing ImportStream
		output.close();
	}

	@Override
	public String getConfigProp(String key, Integer Index) {
		// TODO Auto-generated method stub
		return null;
	}
  
}
