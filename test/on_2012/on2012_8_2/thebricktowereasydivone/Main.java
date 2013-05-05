package on_2012.on2012_8_2.thebricktowereasydivone;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int find(int redCount, int redHeight, int blueCount, int blueHeight)",
                "on_2012.on2012_8_2.thebricktowereasydivone.TheBrickTowerEasyDivOne",
                "1;;2;;3;;4;;4;;true::4;;4;;4;;7;;12;;true::7;;7;;4;;4;;13;;true::47;;47;;47;;47;;94;;true")) {
            Assert.fail();
        }
    }
}
