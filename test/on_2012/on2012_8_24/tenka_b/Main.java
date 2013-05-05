package on_2012.on2012_8_24.tenka_b;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_24.tenka_b.Tenka_B",
                "SINGLE",
                "4/__0 0/__0 1/__1 0/__1 1;;1;;true::5/__0 0/__0 1/__0 2/__2 0/__2 2;;3;;true::5/__4 0/__3 1/__2 2/__1 3/__0 4;;0;;true::5/__0 0 /__1 1/__2 2/__3 3/__4 4/__5 5/__;;0;;true::5/__0 0/__1 2/__2 4/__3 6/__4 8/__;;0;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
