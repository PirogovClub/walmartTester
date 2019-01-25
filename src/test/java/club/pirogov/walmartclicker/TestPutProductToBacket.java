package club.pirogov.walmartclicker;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import club.pirogov.utils.AssertWrapper;
import club.pirogov.utils.JSWaiter;
import club.pirogov.utils.WorkWithJsonConfig;
import club.pirogov.wlmclc.pageobjects.*;



public class TestPutProductToBacket extends BaseTest  {
	
	
	public void start() {
		Random random = new Random();
		RepetitionInfo repetitionInfo;
		Map<String,String> productTrackMap = new HashMap<String,String>();
		
		WorkWithJsonConfig productsConfig = new WorkWithJsonConfig("https://sellerise.com/api/v1/wc/products",driver);
		
		Integer i =random.nextInt(productsConfig.getParamiterListLengh());
		
		
		FrontPage frontpage = new FrontPage(driver);
		setTargetPageUrl(config.getConfigProp("TestLoginUrl"));
		setTargetExistingPageElement(frontpage.getSearchInput());
		if (GetToPage(config.getConfigProp("TestLoginUrl")) != LoadingStage.STOP) {

			
			frontpage.typeInSearchBox(productsConfig.getConfigProp("Keyword",i));
			frontpage.setProductLink(productsConfig.getConfigProp("ProductID",i));

			do {
				if (frontpage.clickToProduct()) {
					ProductPage productpage = new ProductPage(driver);
					productpage.setCorrectSellerName(config.getConfigProp("soldBy"));
					productpage.addToCart();
					productpage.clickCheckOut();
					logger.info("Found on page: " + frontpage.getCurrentPageNumber());
					break;
				}
				frontpage.scrollToPaganator();
			} while (frontpage.clickNextSearchPage());
			productsConfig.setTestDataProp("FindOnPage", frontpage.getCurrentPageNumber());
			productsConfig.setTestDataProp("OverAllPosition", frontpage.getProductPositionNumber().toString());
			productTrackMap.put("FindOnPage", frontpage.getCurrentPageNumber());
			productTrackMap.put("OverAllPosition", frontpage.getProductPositionNumber().toString());
		} else {
			productTrackMap.put("FindOnPage", "0");
			productTrackMap.put("OverAllPosition", "0");
			
			productsConfig.setTestDataProp("FindOnPage", "0");
			productsConfig.setTestDataProp("OverAllPosition", "0");
		}
		productsConfig.setTestDataProp("ProduсtId", productsConfig.getConfigProp("ProductID",i));
		productsConfig.setTestDataProp("Keyword", productsConfig.getConfigProp("Keyword",i));
		productsConfig.setTestDataProp("CheckFromIp", productsConfig.getRemote_ip());
		productsConfig.setTestDataProp("UsedProxy", getProxyString());
		
		productTrackMap.put("ProduсtId", productsConfig.getConfigProp("ProductID",i));
		productTrackMap.put("Keyword", productsConfig.getConfigProp("Keyword",i));
		
		productTrackMap.put("CheckFromIp", productsConfig.getRemote_ip());
		productTrackMap.put("UsedProxy", getProxyString());
		logger.info(productTrackMap);
		AssertWrapper.assertToLog(OutProductClickStat.OutProductStat(productTrackMap));
		
	}
	
	@Test
	public void runTest() {
		start();
	}
	
}
