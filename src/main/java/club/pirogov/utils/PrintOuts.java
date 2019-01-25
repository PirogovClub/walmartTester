 package club.pirogov.utils;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrintOuts {
	protected static Logger logger = LogManager.getLogger();

	public static void doMap(Map<String, String> resultMap, String CommentString) {
		logger.debug("Printing Map with comment:"+ CommentString);
		doMap(resultMap);
	}
	
	
	
	public static void doListOfMap(List<Map<String,String>> varToPrint) {
			logger.debug(getListOfMap(varToPrint));
			
	}
	
	public static String getListOfMap(List<Map<String,String>> varToPrint) {
		String toReturn = "\r\n";
		for(Map<String,String> mapElement : varToPrint) {
			for (Map.Entry<String, String> pair : mapElement.entrySet()) {
				toReturn=toReturn+pair.getKey()+":"+pair.getValue()+"|";
			}
			toReturn=toReturn+"\r\n";
		}
		return toReturn;
		
	}

	public static void doMap(Map<?,?> resultMap) {
		// TODO Auto-generated method stub
		for(Map.Entry<?, ?> entry : resultMap.entrySet()) {
			String key = (String) entry.getKey();
		    String value = (String) entry.getValue();
		    logger.debug("For key "+ key +" result is "+ value);
		    // do what you have to do here
		    // In your case, another loop.
		}
	}
	
	public static void doString(String value) {
		logger.debug(value);
	}


	
}
