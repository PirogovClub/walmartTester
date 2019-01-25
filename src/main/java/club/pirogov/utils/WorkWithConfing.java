package club.pirogov.utils;

import java.io.IOException;

public interface WorkWithConfing {

	String getConfigProp(String key);
	
	String getConfigProp(String key, Integer Index);

	String getTestDataProp(String key);

	String getDbDataProp(String key);

	Object setTestDataProp(String key, String value);

	void saveTestDataPropToFile() throws IOException;

}