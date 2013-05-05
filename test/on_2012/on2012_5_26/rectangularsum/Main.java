package on_2012.on2012_5_26.rectangularsum;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("long minimalArea(int height, int width, long S)",
                "on_2012.on2012_5_26.rectangularsum.RectangularSum",
                "2;;3;;8;;4;;true::3;;3;;10;;-1;;true::3;;3;;36;;9;;true::25;;25;;16000;;32;;true::1000000;;1000000;;1000000000000;;2;;true::2;;4;;12;;-1;;true")) {
            Assert.fail();
        }
    }
}
