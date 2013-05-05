package on_2012.on2012_6_8.crossingtheriver;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("String isItEvenPossible(int waterWidth, int landWidth, int[] blockHeight, int depth)",
                "on_2012.on2012_6_8.crossingtheriver.CrossingTheRiver",
                "3;;3;;3,4,4,5,5,6;;2;;POSSIBLE;;true::3;;3;;3,4,4,5,6,6;;2;;IMPOSSIBLE;;true::5;;2;;4,4,4,4,4;;4;;POSSIBLE;;true::5;;5;;5,5,5,5,5,5,2,3,4,4,6,7,3,2;;2;;POSSIBLE;;true::5;;7;;6,6,6,6,6,6,6,6,6,6,6,6,7,8,9,10;;5;;POSSIBLE;;true::1;;1;;5,3,4;;2;;IMPOSSIBLE;;true")) {
            Assert.fail();
        }
    }
}
