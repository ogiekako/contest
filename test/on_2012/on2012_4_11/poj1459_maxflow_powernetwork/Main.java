package on_2012.on2012_4_11.poj1459_maxflow_powernetwork;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_4_11.poj1459_maxflow_powernetwork.POJ1459_MaxFlow_PowerNetwork",
                "MULTI_EOF",
                "2 1 1 2 (0,1)20 (1,0)10 (0)15 (1)20/__7 2 3 13 (0,0)1 (0,1)2 (0,2)5 (1,0)1 (1,2)8 (2,3)1 (2,4)7/__         (3,5)2 (3,6)5 (4,2)7 (4,3)5 (4,5)1 (6,0)5/__         (0)5 (1)2 (3)2 (4)1 (5)4;;15/__6;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
