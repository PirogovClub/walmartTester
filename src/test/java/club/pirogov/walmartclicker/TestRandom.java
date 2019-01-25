package club.pirogov.walmartclicker;

import java.util.Random;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class TestRandom {
	
	@Test
	@RepeatedTest(10)
	public void testRandom() {
		Random random = new Random();
		System.out.println(random.nextInt(3)+1);
	}

}
