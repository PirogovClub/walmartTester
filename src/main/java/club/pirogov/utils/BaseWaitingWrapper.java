package club.pirogov.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseWaitingWrapper {
	 
    private static WebDriver jsWaitDriver;
    private static WebDriverWait jsWait;
    private static JavascriptExecutor jsExec;
    protected static Logger logger = LogManager.getLogger();
    private static Integer timeOutToWaitInSec = 10;
 
    //Get the driver 
    public static void setDriver (WebDriver driver) {
        jsWaitDriver = driver;
        jsWait = new WebDriverWait(jsWaitDriver, 10);
        jsExec = (JavascriptExecutor) jsWaitDriver;
    }
    
    protected static WebDriver getDriver() {
		return jsWaitDriver;
	}
 
    //Wait for JQuery Load
    public static void waitForJQueryLoad() {
        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) jsWaitDriver)
                .executeScript("return jQuery.active") == 0);
 
        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
 
        //Wait JQuery until it is Ready!
        if(!jqueryReady) {
        	 logger.debug("JQuery is NOT Ready!");
            //Wait for jQuery to load
            jsWait.until(jQueryLoad);
        } else {
        	 logger.debug("JQuery is Ready!");
        }
    }
 
 
    //Wait for Angular Load
    public static void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(jsWaitDriver,15);
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;
 
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
 
        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
                .executeScript(angularReadyScript).toString());
 
        //Get Angular is Ready
        boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());
 
        //Wait ANGULAR until it is Ready!
        if(!angularReady) {
        	 logger.debug("ANGULAR is NOT Ready!");
            //Wait for Angular to load
            wait.until(angularLoad);
        } else {
        	 logger.debug("ANGULAR is Ready!");
        }
    }
 
    //Wait Until JS Ready
    public static void waitUntilJSReady() {
        WebDriverWait wait = new WebDriverWait(jsWaitDriver,15);
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;
 
        //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) jsWaitDriver)
                .executeScript("return document.readyState").toString().equals("complete");
 
        //Get JS is Ready
        boolean jsReady =  (Boolean) jsExec.executeScript("return document.readyState").toString().equals("complete");
 
        //Wait Javascript until it is Ready!
        if(!jsReady) {
        	 logger.debug("JS in NOT Ready!");
            //Wait for Javascript to load
            wait.until(jsLoad);
        } else {
        	 logger.debug("JS is Ready!");
        }
    }
 
    //Wait Until JQuery and JS Ready
    public static void waitUntilJQueryReady() {
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;
 
        //First check that JQuery is defined on the page. If it is, then wait AJAX
        Boolean jQueryDefined = (Boolean) jsExec.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
            //Pre Wait for stability (Optional)
            sleep(20);
 
            //Wait JQuery Load
            waitForJQueryLoad();
 
            //Wait JS Load
            waitUntilJSReady();
 
            //Post Wait for stability (Optional)
            sleep(20);
        }  else {
            logger.debug("jQuery is not defined on this site!");
        }
    }
 
    //Wait Until Angular and JS Ready
    public static void waitUntilAngularReady() {
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;
 
        //First check that ANGULAR is defined on the page. If it is, then wait ANGULAR
        Boolean angularUnDefined = (Boolean) jsExec.executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) jsExec.executeScript("return angular.element(document).injector() === undefined");
            if(!angularInjectorUnDefined) {
                //Pre Wait for stability (Optional)
                sleep(20);
 
                //Wait Angular Load
                waitForAngularLoad();
 
                //Wait JS Load
                waitUntilJSReady();
 
                //Post Wait for stability (Optional)
                sleep(20);
            } else {
            	 logger.debug("Angular injector is not defined on this site!");
            }
        }  else {
        	 logger.debug("Angular is not defined on this site!");
        }
    }
 
    //Wait Until JQuery Angular and JS is ready
    public static void waitJQueryAngular() {
        waitUntilJQueryReady();
        waitUntilAngularReady();
    }
 
    public static void sleep (Integer seconds) {
        long secondsLong = (long) seconds;
        logger.debug("Going to sleep for :"+seconds);
        try {
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("wakeup from sleep for :"+seconds);
    }
    
    //Other implementation
    
    public static boolean waitForJSandJQueryToLoad() {

	    WebDriverWait wait = new WebDriverWait(jsWaitDriver, 30);

	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	          return ((Long)((JavascriptExecutor)getDriver()).executeScript("return jQuery.active") == 0);
	        }
	        catch (Exception e) {
	          // no jQuery present
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return ((JavascriptExecutor)getDriver()).executeScript("return document.readyState")
	        .toString().equals("complete");
	      }
	    };

	  return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
    
	public static boolean waitForElementToBeHidden(By webElement, WebDriver driver) {
		logger.debug("Wait For " + webElement + " to be hidden");
		return waitForElementState("ToBeHidden", webElement, driver);
	}

	public static boolean waitForElementToBeVisible(By webElement, WebDriver driver) {
		logger.debug("Wait For " + webElement + " to be ToBeVisibile");
		return waitForElementState("ToBeVisibile", webElement, driver);
	}

	public static boolean waitForElementToBeClickable(By webElement, WebDriver driver) {
		logger.debug("Wait For " + webElement + " to be elementToBeClickable");
		return waitForElementState("ToBeClickable", webElement, driver);
	}
	
	public static boolean waitForElementState(String waitFor, By webElement, WebDriver driver) {
		return waitForElementState(waitFor, webElement, driver, true, "");
	}
	
	public static boolean waitForElementState(String waitFor, By webElement, WebDriver driver, String textToFind) {
		return waitForElementState(waitFor, webElement, driver, true, textToFind);
	}
	
	public static boolean waitForElementState(String waitFor, WebDriver driver, String textToFind) {
		By webElement = By.xpath("");
		return waitForElementState(waitFor, webElement, driver, true, textToFind);
	}
	
	private static boolean waitForElementState(String waitFor, By webElement, WebDriver driver, boolean stateToBe, String textToFind) {
		try {
			switch (waitFor) {
			case "ToBeClickable":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.elementToBeClickable(webElement));
				break;

			case "ToBeVisibile":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.visibilityOfElementLocated(webElement));
				break;
			case "ToBeHidden":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.invisibilityOfElementLocated(webElement));
				break;
			case "SelectionStateToBe":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.elementSelectionStateToBe(webElement,stateToBe));
				break;
			case "textToBePresentInElement":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.textToBePresentInElementLocated(webElement,textToFind));
				break;
			case "textToBePresentInElementValue":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.textToBePresentInElementValue(webElement,textToFind));
				break;
			case "titleContains":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.titleContains(textToFind));
				break;
			case "titleIs":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.titleIs(textToFind));
				break;
			case "exist":
				new WebDriverWait(driver, timeOutToWaitInSec)
						.until(ExpectedConditions.presenceOfAllElementsLocatedBy(webElement));
				break;
			}
			logger.info("Find element " + webElement + " in state "+waitFor);
			return true;
		} catch (TimeoutException e) {
			logger.error("Miss element " + e);
			return false;
		}
	}
    
}
