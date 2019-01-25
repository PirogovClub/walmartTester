package club.pirogov.walmartclicker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class TestRunnerFewClicks extends BaseTest {
	
	@Test
	@DisplayName("Add operation test")
	//@RepeatedTest(1)
	public void test1() {
		TestPutProductToBacket testPutProductToBacket = new TestPutProductToBacket();
		testPutProductToBacket.start();
		
	}
	/*
	@Test
	@DisplayName("Add operation test2")
	//@RepeatedTest(1)
	public void test2() {
		TestPutProductToBacket testPutProductToBacket = new TestPutProductToBacket();
		testPutProductToBacket.start();
		
	}*/

}
