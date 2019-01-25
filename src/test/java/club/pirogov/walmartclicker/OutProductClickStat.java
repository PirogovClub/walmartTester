package club.pirogov.walmartclicker;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import club.pirogov.utils.DisableSslCheck;
import club.pirogov.utils.PrintOuts;
import club.pirogov.utils.ReadStringFromUrl;

public class OutProductClickStat {
	
	protected static Logger logger = LogManager.getLogger(OutProductClickStat.class);
	
	public static boolean OutProductStat(Map<String,String> productMap) {
		String url;
		
		url = "https://sellerise.com/api/v1/wc/products/save/"
		+productMap.get("ProduсtId")
		+"?token=181fd42227b4f9f83d9752918b5c7854&keyword="
		+productMap.get("Keyword")
		+"&check_from_ip="
		+productMap.get("CheckFromIp")
		+ "&ever_all_position="
		+productMap.get("OverAllPosition")
		+"&used_proxy="
		+productMap.get("UsedProxy")
		+"&find_on_page="
		+productMap.get("FindOnPage");
		logger.info("Url to send:" + url);
		
		try {
			DisableSslCheck.disableSslCheck();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String out = null;
		try {
			out = ReadStringFromUrl.getString(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (out.equals("true")) ? true : false;
		
	}

}
