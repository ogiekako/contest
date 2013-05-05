package on_2012.on2012_6_4.pillars;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("double getExpectedLength(int w, int x, int y)",
                "on_2012.on2012_6_4.pillars.Pillars",
                "1;;1;;1;;1.0;;true::1;;5;;1;;2.387132965131785;;true::2;;3;;15;;6.737191281760445;;true::10;;15;;23;;12.988608956320535;;true")) {
            Assert.fail();
        }
    }
}
