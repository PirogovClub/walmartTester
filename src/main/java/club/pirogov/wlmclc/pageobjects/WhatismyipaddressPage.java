package club.pirogov.wlmclc.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import club.pirogov.utils.JSWaiter;

public class WhatismyipaddressPage extends BasePOM{
	By ipString = By.xpath("//p[@class='hello']");
	By goodIp = By.xpath(".//span[class='cf-footer-item'][contains(text(),'Your IP']");
	
	protected static Logger logger = LogManager.getLogger();
	
	public By getIpString() {
		return ipString;
	}

	public WhatismyipaddressPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getIpAddress() {
		logger.info("Get into Method" + this.getClass().getName());
		
		logger.info("Looking for element "+ipString);
		WebElement webelement = driver.findElement(ipString);
		String returnString = webelement.getText();
		returnString = returnString.substring("Hello ".length(), returnString.length());
		
		return returnString;
	}
	
	public String getIpAddressFromCloudflare() {
		return null;
		
	}

}
