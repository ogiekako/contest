package on_2012.on2012_6_4.chefsdream;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_4.chefsdream.ChefsDream",
                "SINGLE",
                "5 3/__40 30 40 30 40/__;;3/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
