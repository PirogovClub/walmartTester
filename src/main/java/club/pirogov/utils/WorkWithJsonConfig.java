package club.pirogov.utils;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import club.pirogov.utils.DisableSslCheck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WorkWithJsonConfig implements WorkWithConfing {
	
	private JsonObject rootobj;
	private JSONArray array;
	private List<String> list = new ArrayList<String>();;
	private JSONObject workingObject;
	private Integer ParamiterListLengh = 0;
	private List<Map<String, String>> dataArray = new ArrayList<Map<String, String>>();
	private Map<String, String> dataArrayElement = new HashMap<String, String>();
	private String remote_ip =""; 
	

	

	private Map<String, String> toStatisticElement = new HashMap<String, String>();
	protected static Logger logger = LogManager.getLogger();
	private By ipAddressLocation = By.xpath(".//pre[contains(text(),'{\"remote_ip\"')]");
	
	public Integer getParamiterListLengh() {
		return ParamiterListLengh;
	}

	private void setParamiterListLengh(Integer paramiterListLengh) {
		ParamiterListLengh = paramiterListLengh;
	}
	
	public String getRemote_ip() {
		return remote_ip;
	}
	
	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}

	public WorkWithJsonConfig(String sURL){
		try {
				
				DisableSslCheck.disableSslCheck();
				logger.debug("URL to get conf from:"+sURL);
			    workingObject = JsonReader.readJsonFromUrl(sURL);
			    array = workingObject.getJSONArray("data");
			    for(int i = 0 ; i < array.length() ; i++){
			    	
			    	dataArrayElement.clear();
			    	dataArrayElement.put("ProductID", array.getJSONObject(i).getString("ProductID"));
			    	dataArrayElement.put("Keyword", array.getJSONObject(i).getString("Keyword"));
			    	dataArray.add(new HashMap<String, String>(dataArrayElement));
			    	
			    }
			    setParamiterListLengh(array.length());
			   

		} catch (Throwable e) {
			System.out.println("caught:\r\n" + e);
			e.printStackTrace();
			Assertions.fail("Test Failed");
		}
	}
	public WorkWithJsonConfig(String sURL, WebDriver driver){
		try {
				
				DisableSslCheck.disableSslCheck();
				logger.debug("URL to get conf from:"+sURL);
				
				driver.get(sURL);
				String jsonText = driver.findElement(ipAddressLocation).getText();
				logger.debug("jsonText:"+jsonText);
				workingObject = JsonReader.readJsonFromText(jsonText);
				setRemote_ip(workingObject.getString("remote_ip"));
				logger.debug("Come from IP:"+getRemote_ip());
			    array = workingObject.getJSONArray("data");
			    
			    
			    for(int i = 0 ; i < array.length() ; i++){
			    	dataArrayElement.clear();
			    	dataArrayElement.put("ProductID", array.getJSONObject(i).getString("ProductID"));
			    	dataArrayElement.put("Keyword", array.getJSONObject(i).getString("Keyword"));
			    	dataArray.add(new HashMap<String, String>(dataArrayElement));
			    }
			    
			    setParamiterListLengh(array.length());
			    logger.debug("dataArray.size():"+dataArray.size());

		} catch (Throwable e) {
			System.out.println("caught:\r\n" + e);
			e.printStackTrace();
			Assertions.fail("Test Failed");
		}
	}
	

	@Override
	public String getConfigProp(String key, Integer index) {
		// TODO Auto-generated method stub
		//return rootobj.get(key).getAsString(); //just grab the zipcode
		return dataArray.get(index).get(key);
		
	}

	@Override
	public String getTestDataProp(String key) {
		// TODO Auto-generated method stub
		String stringToReturn ="";
		try {
			stringToReturn = workingObject.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringToReturn; //just grab the zipcode
	}

	@Override
	public String getDbDataProp(String key) {
		// TODO Auto-generated method stub
		return rootobj.get(key).getAsString(); //just grab the zipcode
	}

	@Override
	public Object setTestDataProp(String key, String value) {
		// TODO Auto-generated method stub
		toStatisticElement.put(key, value);
		return null; //just grab the zipcode
	}

	@Override
	public void saveTestDataPropToFile() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getConfigProp(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
