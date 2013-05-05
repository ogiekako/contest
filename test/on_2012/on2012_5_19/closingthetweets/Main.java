package on_2012.on2012_5_19.closingthetweets;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_5_19.closingthetweets.ClosingTheTweets",
                "SINGLE",
                "3 6/__CLICK 1/__CLICK 2/__CLICK 3/__CLICK 2/__CLOSEALL/__CLICK 1/__;;1/__2/__3/__2/__0/__1/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
