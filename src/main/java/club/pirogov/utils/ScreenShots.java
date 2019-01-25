package club.pirogov.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShots {
	protected static Logger logger = LogManager.getLogger();

	public void makeSimpleShot(WebDriver driver, String prefix) {

		logger.info("Resizing Page");
		driver.manage().window().maximize();
		
		
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Integer windMaxH = (int) (long) executor.executeScript(" return Math.max(\r\n" + 
				"  document.body.scrollHeight, document.documentElement.scrollHeight,\r\n" + 
				"  document.body.offsetHeight, document.documentElement.offsetHeight,\r\n" + 
				"  document.body.clientHeight, document.documentElement.clientHeight\r\n);");
		
		Dimension targetSize = new Dimension(driver.manage().window().getSize().width,windMaxH);
		driver.manage().window().setSize(targetSize);
		
		logger.info("Making screenshot");
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile //method
			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
			String text = date.format(formatter);
			FileUtils.copyFile(src, new File("testsScrShots/" + prefix + text + ".png"));
		}

		catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

}
