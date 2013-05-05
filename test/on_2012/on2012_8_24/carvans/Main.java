package on_2012.on2012_8_24.carvans;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_24.carvans.Carvans",
                "MULTI_NUMBER",
                "3/__1/__10/__3/__8 3 6/__5/__4 5 1 2 3/__;;1/__2/__2/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
