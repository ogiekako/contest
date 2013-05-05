package on_2012.on2012_6_5.favouritenumbers;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_5.favouritenumbers.FavouriteNumbers",
                "SINGLE",
                "1 1000000000 4 2/__62/__63/__;;163/__;;true::1 1 1 1/__2/__;;no such number/__;;true::1 1000 15 2/__6/__22/__;;67/__;;true::1 1000000000 1000000000 10/__0 1 2 3 4 5 6 7 8 9;;1000000000;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
