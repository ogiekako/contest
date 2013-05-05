package on_2012.on2012_8_24.pizzatossing;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_24.pizzatossing.PizzaTossing",
                "MULTI_NUMBER",
                "3/__1 a/__0 a/__3 a/__2 a/__8 An/__3 B/__;;2/__8/__254/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
