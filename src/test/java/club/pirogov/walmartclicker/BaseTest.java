package club.pirogov.walmartclicker;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import club.pirogov.utils.JSWaiter;
import club.pirogov.utils.ScreenShots;
import club.pirogov.utils.WorkWithMainConfig;

public class BaseTest {

	protected WebDriver driver;

	protected String targetPageUrl;
	protected String targetPageNameToTrace;
	protected By targetExistingPageElement;
	protected WorkWithMainConfig config = new WorkWithMainConfig();

	protected static Logger logger = LogManager.getLogger();

	private List<Integer> proxyPort = new ArrayList<Integer>();
	private List<String> proxyAddr = new ArrayList<String>();
	private String proxyString;

	private String proxyURI;
	private Integer proxyPor;
	private Random random = new Random();
	private Integer pageLoadIteration = 0;
	
	protected enum LoadingStage { BADPAGE,GOODPAGE,STOP};
	private LoadingStage runStage;
	
	final static int PageReLoadingIterationLimits=4;
	final static int PageLoadingValidationErrors=3;
	protected boolean disableImagesInBrowser = false;
	

	public boolean isDisableImagesInBrowser() {
		return disableImagesInBrowser;
	}

	public void setDisableImagesInBrowser(boolean disableImagesInBrowser) {
		this.disableImagesInBrowser = disableImagesInBrowser;
	}

	public String getProxyString() {
		return proxyString;
	}

	private void SetproxyParam() {
		proxyPort.clear();
		proxyAddr.clear();
		for (int i = 9151; i <= 9160; i++) {
			proxyPort.add(i);
		}
		proxyAddr.add("94.237.35.41");
		proxyAddr.add("94.237.39.83");
		proxyString = proxyAddr.get(random.nextInt(proxyAddr.size())) + ":"
				+ proxyPort.get(random.nextInt(proxyPort.size()));
	}

	private void setNewProxy() {
		proxyURI = proxyAddr.get(random.nextInt(proxyAddr.size()));
		proxyPor = proxyPort.get(random.nextInt(proxyPort.size()));
		proxyString = proxyURI + ":" + proxyPor;
		//System.setProperty("socks5.proxyHost", proxyURI.toString());
		//System.setProperty("socks5.proxyPort", proxyPor.toString());
	}

	public BaseTest() {
		SetproxyParam();
	}

	public By getTargetExistingPageElement() {
		return targetExistingPageElement;
	}

	public void setTargetExistingPageElement(By targetExistingPageElement) {
		logger.debug("targetExistingPageElement:"+targetExistingPageElement);
		this.targetExistingPageElement = targetExistingPageElement;
	}

	public String getTargetPageUrl() {
		return targetPageUrl;
	}

	public void setTargetPageUrl(String targetPageUrl) {
		this.targetPageUrl = targetPageUrl;
	}

	public String getTargetPageNameToTrace() {
		return targetPageNameToTrace;
	}

	public void setTargetPageNameToTrace(String targetPageNameToTrace) {
		this.targetPageNameToTrace = targetPageNameToTrace;
	}

	protected void openUrl(String pageUrl) {
		driver.get(pageUrl);
	}

	public LoadingStage GetToPage(String targetPageUrl) {
		
		do {
			
			loadPage(targetPageUrl);
			WaitForLoad();
		} while (runStage==LoadingStage.BADPAGE);
		
		return runStage;
	}
	
	private void loadPage(String targetPageUrl) {
		
		pageLoadIteration++;
		logger.debug("Try to load " + targetPageUrl + ", wating for load pageLoadIteration:"+pageLoadIteration);
		runStage = LoadingStage.GOODPAGE;
		if (pageLoadIteration > PageReLoadingIterationLimits) {
			runStage = LoadingStage.STOP;
		} else {
			logger.debug("Navigating to "+targetPageUrl);
			driver.get(targetPageUrl);
			logger.debug("come to "+targetPageUrl);
			PageLoadingSatisfaction pageLoadingSatisfaction = new PageLoadingSatisfaction(driver);
			Integer score = pageLoadingSatisfaction.validateLog();
			logger.debug("score:"+score);
			if ( score> PageLoadingValidationErrors)
				runStage = LoadingStage.BADPAGE;
		}
	}

	public void WaitForLoad(By targetExistingPageElement) {
		WaitForLoad();

	}

	public void WaitForLoad() {
		JSWaiter.waitForElement(targetExistingPageElement, driver);
	}

	// call with String "host:port"
	private FirefoxDriver createDriverWithProxy(String proxyIpPort) {

		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setSocksVersion(5);
		proxy.setSocksProxy(proxyIpPort);
		proxy.setProxyType(Proxy.ProxyType.MANUAL);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, proxy);
		logger.info("Starting firefox");
		FirefoxOptions options = new FirefoxOptions(cap);
		FirefoxDriver driver = new FirefoxDriver();
		logger.info("Started firefox");
		return driver;
	}

	private WebDriver createDriverWithProxy2(String proxyIpPort) {

		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("network.proxy.type", 1);
		profile.setPreference("network.proxy.http", proxyURI);
		profile.setPreference("network.proxy.http_port", proxyPor);
		// FirefoxDriver driver = new FirefoxDriver(profile);

		return driver;
	}

	
	
	public void getDriverLog() {
		logger.debug("Reading Broweser Log");
		LogEntries logs = driver.manage().logs().get("browser");
		List<LogEntry> logLines = logs.getAll();
		
		for (LogEntry logLine : logLines) {
			logger.debug(logLine.getMessage());
		}
	}
	
	
	
	

	@BeforeEach
	// We will open browser and pass login page to be able to switch to any page we
	// need
	public void startBrowser() throws Exception {

		String Browser = config.getConfigProp("browserToTest");
		String TestLoginUrl = config.getConfigProp("TestLoginUrl");
		String SiteLogin = config.getConfigProp("SiteLogin");
		String SitePass = config.getConfigProp("SitePass");
		System.setProperty(" -Dlog4j.configurationFile", "src/config");

		setNewProxy();

		switch (Browser) {
		case "Chrome":
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--proxy-server=socks5://" + proxyString);
			option.addArguments("disable-infobars");
			
			if (disableImagesInBrowser) {
				HashMap<String, Object> images = new HashMap<String, Object>();
				images.put("images", 2);
				HashMap<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values", images);
				option.setExperimentalOption("prefs", prefs);
			}            
			
			// option.addArguments("--headless");
			driver = new ChromeDriver(option);
			break;
		
		case "Firefox":
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
			/*
			 * Proxy proxy = new Proxy(); //proxy.setHttpProxy("94.237.35.41:9152");
			 * proxy.setSocksProxy("94.237.35.41:9152"); proxy.setSocksVersion(5);
			 * FirefoxOptions firefoxOptions = new FirefoxOptions();
			 * firefoxOptions.setCapability("marionette", true);
			 * firefoxOptions.setCapability(CapabilityType.PROXY, proxy);
			 */

			driver = createDriverWithProxy(proxyString);
			// driver = new FirefoxDriver(firefoxOptions);
			break;
		}

		// Check if this is login page, by default we should get to login page
		// LoginPage loginPage = new LoginPage(driver,TestLoginUrl);

		// Check if we need to login
		// if (loginPage.isThisIsLoginPage()) {
		// if this is login page login and get to Dashboard
		// loginPage.loginAs(SiteLogin, SitePass);
		// }
	}
	
	

	@AfterEach
	public void quitBrowser() {
		ScreenShots screenShot = new ScreenShots();
		screenShot.makeSimpleShot(driver, "currentTest");
		driver.quit();
	}

}
