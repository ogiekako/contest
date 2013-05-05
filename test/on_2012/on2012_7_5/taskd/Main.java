package on_2012.on2012_7_5.taskd;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_7_5.taskd.TaskD",
                "SINGLE",
                "5/__S..../__##.##/__...../__.#.#./__.#.#G;;5.937500000;;true::9/__S......../__####.####/__........./__.#####.##/__...#.#.../__.#.#.####/__.#...#.../__.#.#.#.#./__.#.#...#G;;18.029407501;;true::13/__S............/__####.#.#.####/__.....#.#...#./__.###########./__.....#.#...../__.#.###.###.##/__.#...#.#...../__##.#.#.#####./__.#.#.....#.../__.#.###.#.#.##/__...#...#...../__.#.#.#.#.###./__.#.#.#.#...#G;;35.000000000;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
