package club.pirogov.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomData {
	
	private String UsAlphabetLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String UsAlphabetLettersLowReg = "abcdefghijklmnopqrstuvwxyz";
	private String UsAlphabetLettersHighReg = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String RussianAlphabetLetters="���������������������������������������������������������������޸�";
	private String ArabicNumbers="0123456789";
	private String UsAlphabetLettersAndArabicNumbers=UsAlphabetLetters+ArabicNumbers;
	private Random random = new Random();
	static public enum LanguageSets{
		RUSSIAN,
		ENGLISH,
		ENGLISH_LOW,
		ENGLISH_HIGH,
		NUMBERS,
		ENGLISH_AND_NUMBERS
	}
	
	
	public String getRandomString(int lenght, LanguageSets LanguageSet) {
		 
		String outString="";
		String alphabet="";
		switch (LanguageSet){
			case  RUSSIAN:
				alphabet=RussianAlphabetLetters;
				break;
			case  ENGLISH:
				alphabet=UsAlphabetLetters;
				break;
			case  ENGLISH_LOW:
				alphabet=UsAlphabetLettersLowReg;
				break;
			case  ENGLISH_HIGH:
				alphabet=UsAlphabetLettersHighReg;
				break;
			case  NUMBERS:
				alphabet=ArabicNumbers;
				break;
			case ENGLISH_AND_NUMBERS:
				alphabet=UsAlphabetLettersAndArabicNumbers;
				break;
			default:
				alphabet=UsAlphabetLetters;
		}
		
		for (int i=1;i<=lenght;i++) {
			outString=outString+alphabet.charAt(random.nextInt(alphabet.length()));
		}
		
		return outString;
	}
	
	public Boolean getRandomBoolean() {
		return random.nextBoolean();
	}
	
	public int getRandomInt(int intFrom, int intTo) {
		return ThreadLocalRandom.current().nextInt(Math.min(intFrom, intTo), Math.max(intFrom, intTo) + 1);
	}
	
	public int getRandomInt() {
		return ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	
	public float getRandomFloat(float flFrom, float flTo, int dec) {
		float newFloat = new BigDecimal(Math.min(flTo,flFrom)+(Math.max(flTo,flFrom)-Math.min(flTo,flFrom))*random.nextFloat()).setScale(dec,RoundingMode.UP).floatValue();
		return newFloat;
	}
	
	public float getRandomFloat(float flFrom, float flTo) {
		
		return (Math.min(flTo,flFrom)+(Math.max(flTo,flFrom)-Math.min(flTo,flFrom))*random.nextFloat());
	}
	
	

}
