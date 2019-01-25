package club.pirogov.wlmclc.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import club.pirogov.utils.JSWaiter;

public class FrontPage extends BasePOM {
	
	By searchInput = By.xpath(".//input[@id='global-search-input']");
	By paganator = By.xpath(".//div[contains(@class,\"paginator\")]");
	By nextPage = By.xpath(".//a[contains(@aria-label,'Page')][preceding::a[1][@class='active']]");
	By spiner = By.xpath(".//div[@class='spinner-backdrop spinner-backdrop-fixed']");
	
	By productsElements = By.xpath(".//a[contains(@class,'product-title-link')]");
	String productWalmartId="";
	By productLink ;
	
	private String currentPageNumber = "";
	private Integer productPositionNumber;

	public Integer getProductPositionNumber() {
		return productPositionNumber;
	}



	private void setProductPositionNumber(Integer productPositionNumber) {
		this.productPositionNumber = productPositionNumber;
	}



	public void setCurrentPageNumber(String currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}



	public String getCurrentPageNumber() {
		return currentPageNumber;
	}
	
	

	public void setProductLink(By productLink) {
		this.productLink = productLink;
	}
	
	public void setProductLink(String configProp) {
		// TODO Auto-generated method stub
		productWalmartId = configProp;
		this.productLink =  By.xpath(".//a[contains(@href,'"+configProp+"')]");
	}

	public By getSearchInput() {
		return searchInput;
	}

	public  FrontPage(WebDriver driver) {
		this.driver = driver;
		setCurrentPageNumber("0");
		setProductPositionNumber(0);
	}
	
	public void typeInSearchBox(String stringToType) {
		logger.debug("typing in serch box"+ stringToType);
		setTextToTestFieldAndWait(searchInput,stringToType+Keys.RETURN,"Search Input",1);
		//JSWaiter.waitForElement(paganator,driver);
	}
	
	public void pressEnter() {
		
	}
	
	public void scrollToPaganator() {
		//JSWaiter.waitJQueryAngular();
		scrollToElement(paganator);
	}
	
	public boolean clickNextSearchPage() {
		
		try {
			WebElement element = driver.findElement(nextPage);
			scrollToPaganator();

			if (element.isDisplayed()) {
				waitForElementToHide(spiner);
				stopLoadingPage();
				setCurrentPageNumber(element.getText());
				logger.debug("Page N:"+getCurrentPageNumber());
				element.click();
				waitModalWindow(nextPage);
				waitForElement(paganator,driver);
				
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		
	}

	public void getPaganatorInfo() {
		// TODO Auto-generated method stub
		
	}
	
	public void takeProductPositionOnThePage() {
		List<WebElement> linklist = driver.findElements(productsElements);
		Integer runningProductN=0;
		logger.debug("Amount of links on the page:"+linklist.size());
		for (WebElement linkElement : linklist) {
			runningProductN++;
			if (linkElement.getAttribute("href").contains(productWalmartId)) {
				break;
			}
		}
		setProductPositionNumber((Integer.valueOf(currentPageNumber)-1)*linklist.size() + runningProductN);
	}

	public boolean clickToProduct() {
		// TODO Auto-generated method stub
		if (ckeckElementExist(productLink)) {
			if (getCurrentPageNumber().equals("0"))
				setCurrentPageNumber("1");
			takeProductPositionOnThePage();
			
			if (locateScrollClick(productLink)) {

				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
	
	
	

	
	


	
	
	
}
