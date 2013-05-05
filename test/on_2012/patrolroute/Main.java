package on_2012.patrolroute;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int countRoutes(int X, int Y, int minT, int maxT)",
                "on_2012.patrolroute.PatrolRoute",
                "3;;3;;1;;20000;;6;;true::3;;3;;4;;7;;0;;true::4;;6;;9;;12;;264;;true::7;;5;;13;;18;;1212;;true::4000;;4000;;4000;;14000;;859690013;;true")) {
            Assert.fail();
        }
    }
}
