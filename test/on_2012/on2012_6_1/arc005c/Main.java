package on_2012.on2012_6_1.arc005c;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_1.arc005c.ARC005C",
                "SINGLE",
                "4 5/__s####/__....#/__#####/__#...g;;YES;;true::6 6/__.....s/__###.../__###.../__######/__...###/__g.####;;YES;;true::10 10/__s........./__#########./__#.......#./__#..####.#./__##....#.#./__#####.#.#./__g##.#.#.#./__###.#.#.#./__###.#.#.#./__#.....#...;;YES;;true::4 4/__...s/__..../__..../__.g..;;YES;;true::1 10/__s..####..g;;NO;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
