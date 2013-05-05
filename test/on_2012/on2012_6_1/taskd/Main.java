package on_2012.on2012_6_1.taskd;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_1.taskd.TaskD",
                "SINGLE",
                "1/__1/__2/__2/__;;YES;;true::3/__1 0 0/__0 1 0/__0 0 1/__0 0 1/__0 1 0/__1 0 0/__-1 -1 -1/__-1 -1 -1/__-1 -1 -1;;NO;;true::3/__1 2 3/__4 5 6/__7 8 9/__11 12 13/__14 15 16/__17 18 19/__90 96 102/__216 231 246/__342 366 390;;YES;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
