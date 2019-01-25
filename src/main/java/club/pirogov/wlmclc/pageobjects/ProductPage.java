package club.pirogov.wlmclc.pageobjects;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import club.pirogov.utils.JSWaiter;

public class ProductPage extends BasePOM {
	
	By sellerName = By.xpath(".//a[@class='seller-name']");
	By addToCardBtn = By.xpath(".//button//*[contains(text(),'Add to Cart') or contains(text(),'Buy from this Seller')] | .//button//*[contains(text(),'Add to Cart') or contains(text(),'Buy from this Seller')] | .//button/*[contains(text(),'Add to Cart')]");
	By checkOutBtn = By.xpath(".//div[@class='cart-pos-proceed-to-checkout new-ny-styling']//div//button[1]");
	By checkOutBtn2 = By.xpath(".//div[@class='cart-pos-proceed-to-checkout new-ny-styling']//div//button[2]");
	By checkOutBtn3 = By.xpath(".//button[contains(text(),'Continue')] | .//button//*[contains(text(),'Continue')]");
	By modalCheckOut =  By.xpath(".//div[@class='modal-content']");
	
	By correctSellerLink;
	String correctSellerName;
	
	
	
	public String getCorrectSellerName() {
		return correctSellerName;
	}

	public void setCorrectSellerName(String correctSellerName) {
		this.correctSellerName = correctSellerName;
		this.correctSellerLink = By.xpath("//a[contains(text(),'"+correctSellerName+"')]");
	}

	public  ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	public void addToCart() {
		// TODO Auto-generated method stub
		logger.info("Get into " + this.getClass().getName());
		if (!checkSeller()) {
			changeSeller();
		}
		addToProductCart();
	}

	private boolean changeSeller() {
		// TODO Auto-generated method stub
		logger.info("Get into Test" + this.getClass().getName());
		return locateScrollClick(correctSellerLink);
		
	}

	private void addToProductCart() {
		// TODO Auto-generated method stub
		
		clickToAddToCart();
		
	}

	private boolean checkSeller() {
		// TODO Auto-generated method stub
		logger.info("Get into Test" + this.getClass().getName());
		WebElement element = driver.findElement(sellerName);
		return element.getText().equalsIgnoreCase(correctSellerName) ? true : false;
		
	}
	
	public void clickToAddToCart() {
		logger.info("Clicking Add To Cart " + this.getClass().getName());
		clickOnElement(addToCardBtn);
	}
	
	public void clickCheckOut() {
		
			if (!clickOnElement(checkOutBtn)){
				clickOnElement(checkOutBtn2);
			}
			
			clickOnElement(checkOutBtn3);
			clickOnElement(checkOutBtn3);
		
	}
}
