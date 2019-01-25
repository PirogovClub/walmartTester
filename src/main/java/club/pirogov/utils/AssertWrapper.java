package club.pirogov.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

public class AssertWrapper {
	protected static Logger logger = LogManager.getLogger();

	public static void assertToLog(boolean isTestPassed, String showMsg) {
		if (!isTestPassed) {
			logger.error("TEST FAILED "+showMsg);
			Assertions.fail();
		}
		logger.info("TEST SUCCESSFULLY COMPLETED");
		// return isTestPassed;
		

	}
	
	public static void assertToLog(boolean isTestPassed) {
		assertToLog(isTestPassed, "");
	}

}
