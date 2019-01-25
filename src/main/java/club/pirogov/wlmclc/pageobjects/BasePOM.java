package club.pirogov.wlmclc.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import club.pirogov.utils.BaseWaitingWrapper;
import club.pirogov.utils.DataTable;

public class BasePOM {
	protected WebDriver driver;
	protected static Logger logger = LogManager.getLogger();
	private By toBeVisiablePageElement;
	private By targetExistingPageElement;

	public By getTargetExistingPageElement() {
		return targetExistingPageElement;
	}

	protected void setTargetExistingPageElement(By targetExistingPageElement) {
		this.targetExistingPageElement = targetExistingPageElement;
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected void setDriver(WebDriver driver) {
		this.driver = driver;

	}

	public By getToBeVisiablePageElement() {
		return toBeVisiablePageElement;
	}

	public void setToBeVisiablePageElement(By toBeVisiablePageElement) {
		this.toBeVisiablePageElement = toBeVisiablePageElement;
	}

	public void waitModalWindow(By waitForElement) {
		//setToBeVisiablePageElement(waitForElement);
		//logger.trace("Wait for" + waitForElement);
		//waitForModalOpen();
		BaseWaitingWrapper.waitForElementToBeVisible(waitForElement, driver);
		

	}

	private boolean findVisiblePageElement() {
			new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOfElementLocated(toBeVisiablePageElement));
			return true;
	}

	private boolean waitForElementHidden() {
		BaseWaitingWrapper.waitForElementToBeHidden(toBeVisiablePageElement, driver);
		return true;
	}
	
	protected boolean waitForElementHidden(By element) {
		BaseWaitingWrapper.waitForElementToBeHidden(element, driver);
		return true;
	}
	
	 public static boolean waitForElement(By webElement, WebDriver driver) {
			logger.debug("Wait For " + webElement);
			try {
				(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(webElement));

				return true;
			} catch (TimeoutException e) {
				logger.error("Miss element "+e);
				return false;
			} 
			
		}
	

	public void setTextToTestField(By fieldLocator, String textToSet, String fildShortNameToTrace) {
		setTextToTestField(fieldLocator, textToSet, fildShortNameToTrace, true, 0);
	}

	public void setTextToTestFieldAndWait(By fieldLocator, String textToSet, String fildShortNameToTrace,
			Integer waitForSecAfterInput) {
		setTextToTestField(fieldLocator, textToSet, fildShortNameToTrace, true, waitForSecAfterInput * 1000);
	}

	public void setTextToTestField(By fieldLocator, String textToSet, String fildShortNameToTrace, boolean Overwrite,
			Integer waitForMiliSecAfterInput) {
		logger.trace("waitForSecAfterInput:" + waitForMiliSecAfterInput);
		logger.info("Typing " + textToSet + " into " + fieldLocator.toString() + " Overwrite:" + Overwrite
				+ " wait after input mls: " + waitForMiliSecAfterInput);
		waitForElement2BeVisible(fieldLocator);
		if (Overwrite) {
			driver.findElement(fieldLocator).clear();
		}
		driver.findElement(fieldLocator).sendKeys(textToSet);
		BaseWaitingWrapper.setDriver(driver);
		BaseWaitingWrapper.waitJQueryAngular();

		if (waitForMiliSecAfterInput > 0) {
			BaseWaitingWrapper.sleep(waitForMiliSecAfterInput);
		}

	}

	public void waitForElementToHide(By waitForElement) {
		setToBeVisiablePageElement(waitForElement);
		waitForElementHidden();

	}

	public boolean checkIfElementNotDisplayed(By elementToCheck) {
		try {
			return !driver.findElement(elementToCheck).isDisplayed();
		} catch (ElementNotVisibleException e) {
			return true;
		}

	}

	public void clickHrefWithText(String hrefToClink) {
		By hrefToClinkOn = By.xpath(".//a[contains(text(),'" + hrefToClink + "')]");
		logger.info("Wait For " + hrefToClinkOn);
		waitForElement(hrefToClinkOn,driver);
		if (driver.findElement(hrefToClinkOn).isDisplayed()) {
			driver.findElement(hrefToClinkOn).click();
		} else {
			logger.debug(hrefToClinkOn + " field is not displayed");
		}
	}

	public void clickHrefWithLink(String hrefToClink) {
		By hrefToClinkOn = By.xpath(".//a[@href='" + hrefToClink + "']");
		logger.info("Wait For " + hrefToClinkOn);
		waitForElement2BeVisible(hrefToClinkOn);
		if (driver.findElement(hrefToClinkOn).isDisplayed()) {
			driver.findElement(hrefToClinkOn).click();
		} else {
			logger.debug(hrefToClinkOn + " field is not displayed");
		}
	}

	public void waitForElement2BeVisible(By targetExistingPageElement) {
		BaseWaitingWrapper.waitForElementToBeVisible(targetExistingPageElement, driver);
	}

	protected void WaitForLoad() {
		while (FindPageElement())
			;
	}

	private boolean FindPageElement() {
		try {
			driver.findElement(targetExistingPageElement);
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}

	}

	public boolean locateScrollClick(By webElement) {

		if (scrollToElement(webElement)) {
			clickOnElement(webElement);
			return true;
		} else {
			return false;
		}
	}
	public boolean ckeckElementExist(By webElement) {

		if (BaseWaitingWrapper.waitForElementState("exist", webElement, driver)){
			return true;
		} else {
			return false;
		}
	}

	public boolean clickOnElement(By webElement) {
		logger.info("Doing click on " + webElement);
		if (BaseWaitingWrapper.waitForElementToBeVisible(webElement, driver)) {
			try {
				driver.findElement(webElement).click();
			} catch (WebDriverException e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click()", driver.findElement(webElement));
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean scrollToElement(By webElement) {
		// Add humanism in scroll http://internetka.in.ua/selenium-webdriver-scrolling/
		logger.info("Doing scroll to " + webElement);
		if (BaseWaitingWrapper.waitForElementToBeVisible(webElement, driver)) {
			WebElement element = driver.findElement(webElement);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-50);", element);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkPageTableDbAndUI(DataTable DbTable, DataTable UITable) {
		
		return DbTable.equals(UITable);
		
	}
	
	public void stopLoadingPage() {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("return window.stop");
    }

}
