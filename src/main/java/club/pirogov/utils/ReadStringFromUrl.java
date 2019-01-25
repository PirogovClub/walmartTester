package club.pirogov.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ReadStringFromUrl {
	
		public static String getString(String requestURL) throws IOException
		{
		    try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
		            StandardCharsets.UTF_8.toString()))
		    {
		        scanner.useDelimiter("\\A");
		        return scanner.hasNext() ? scanner.next() : "";
		    }
		}
}
