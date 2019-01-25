package club.pirogov.walmartclicker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

public class PageLoadingSatisfaction {
	protected WebDriver driver;
	protected static Logger logger = LogManager.getLogger();
	private List<LogEntry> logLines;
	private Integer riskLevel=0;
	
	public PageLoadingSatisfaction(WebDriver driver) {
		this.driver = driver;
		riskLevel=0;
	}
	
	private void loadlogs() {
		logger.debug("Reading Browser Log");
		LogEntries logs = driver.manage().logs().get("browser");
		logLines = logs.getAll();
		
	}
	
	private void validate(String inLogLine, String module, String error) {
		if (inLogLine.contains(module) & inLogLine.contains(error)) riskLevel++;
	}
	
	public Integer validateLog() {
		loadlogs();
		riskLevel=0;
		for (LogEntry logLine : logLines) {
			validate(logLine.getMessage(),"/BogleWeb.css","status of 403");
			validate(logLine.getMessage(),"/capabilities.min.js","status of 403");
			validate(logLine.getMessage(),"/homepage_styles.style","status of 403");
			validate(logLine.getMessage(),"/dn-perf.min.js","status of 403");
			validate(logLine.getMessage(),"/ft.js","status of 403");
		}
		return riskLevel;
	}
}
