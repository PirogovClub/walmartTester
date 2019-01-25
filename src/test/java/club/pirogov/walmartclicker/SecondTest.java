package club.pirogov.walmartclicker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * Created on 20.08.2018.
 * <p>
 * TODO: replace on javadoc
 *
 * @author Korovin Anatoliy
 */
@Execution(ExecutionMode.CONCURRENT)
class SecondTest {

    @Test
    void name() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("one more! "+Thread.currentThread().getName());
    }
    @Test
    void name2() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("one more! "+Thread.currentThread().getName());
    }
    @Test
    void name3() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("one more! "+Thread.currentThread().getName());
    }
}
