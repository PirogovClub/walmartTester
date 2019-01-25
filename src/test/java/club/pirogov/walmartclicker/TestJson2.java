package club.pirogov.walmartclicker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import club.pirogov.utils.WorkWithJsonConfig;


public class TestJson2 {
	
	protected WorkWithJsonConfig config = new WorkWithJsonConfig("https://185.26.49.219/api/v1/wc/products");;
	protected static Logger logger = LogManager.getLogger();

	
	@Test
	public void test() {
		logger.info("Get into Test :"+config.getConfigProp("Keyword",2));
		logger.info("Get into Test :"+config.getConfigProp("ProductID",2));
		
	}

}
