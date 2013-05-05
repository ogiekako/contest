package on_2012.on2012_6_5.thegraysimilarcode;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_5.thegraysimilarcode.TheGraySimilarCode",
                "SINGLE",
                "5/__1 0 2 3 7/__;;Yes/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
