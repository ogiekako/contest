package on_2012.on2012_5_19.goldentrees;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_5_19.goldentrees.GoldenTrees",
                "MULTI_NUMBER",
                "3/__1 3 2 4 4/__1 3 2 4 7/__1 3 2 4 9/__;;4/__1536/__18811834/__;;true::3/__50 50 50 101 1000000000/__50 50 50 101 1000000000/__50 50 50 101 1000000000;;31220332/__31220332/__31220332/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
