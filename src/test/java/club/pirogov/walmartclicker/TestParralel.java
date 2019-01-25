package club.pirogov.walmartclicker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class TestParralel {
	
	@Test
	//@RepeatedTest(1)
	public void runTest1() {
		TestPutProductToBacket testPutProductToBacket = new TestPutProductToBacket();
		try {
			testPutProductToBacket.startBrowser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testPutProductToBacket.start();
	//	testPutProductToBacket.openUrl("https://ifconfig.co/");
		testPutProductToBacket.quitBrowser();
	}
	@Test
	//@RepeatedTest(1)
	public void runTest2() {
		TestPutProductToBacket testPutProductToBacket = new TestPutProductToBacket();
		try {
			testPutProductToBacket.startBrowser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testPutProductToBacket.start();
	//	testPutProductToBacket.openUrl("https://ifconfig.co/");
		testPutProductToBacket.quitBrowser();
	}
	@Test
	//@RepeatedTest(1)
	public void runTest4() {
		TestPutProductToBacket testPutProductToBacket = new TestPutProductToBacket();
		try {
			testPutProductToBacket.startBrowser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testPutProductToBacket.start();
	//	testPutProductToBacket.openUrl("https://ifconfig.co/");
		testPutProductToBacket.quitBrowser();
	}
	
}
