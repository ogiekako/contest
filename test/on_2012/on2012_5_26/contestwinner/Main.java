package on_2012.on2012_5_26.contestwinner;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int getWinner(int[] events)",
                "on_2012.on2012_5_26.contestwinner.ContestWinner",
                "4,7,4,1;;4;;true::10,20,30,40,50;;10;;true::123,123,456,456,456,123;;456;;true::1,2,2,3,3,3,4,4,4,4;;4;;true")) {
            Assert.fail();
        }
    }
}
