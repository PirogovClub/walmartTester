package club.pirogov.walmartclicker;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import club.pirogov.utils.DisableSslCheck;
import club.pirogov.utils.ReadStringFromUrl;

public class TestGetUrlFromSellerise {
	
	public static void main(String arg[]) {
	
		try {
			DisableSslCheck.disableSslCheck();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	String url="https://185.26.49.219/api/v1/wc/products/save/455162627?token=181fd42227b4f9f83d9752918b5c7854&keyword=collagen&check_from_ip=192.160.102.170&ever_all_position=22&used_proxy=94.237.39.83:9155&find_on_page=11";
	
	
		
		String out = null;
		try {
			out = ReadStringFromUrl.getString(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	System.out.println(out);
	if (out.equals("true")) {
		 System.out.println(out);
	} else {
		System.out.println("Not equal"); 
	}
	
	}
}
