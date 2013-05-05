package on_2012.foxandgreed;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int count(int H, int W, int S)",
                "on_2012.foxandgreed.FoxAndGreed",
                "2;;2;;1;;4;;true::2;;2;;2;;9;;true::2;;2;;0;;1;;true::47;;58;;100;;1301;;true::1234;;2345;;97;;8894;;true::3;;3;;1;;28;;true")) {
            Assert.fail();
        }
    }
}
