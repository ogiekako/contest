package on_2012.on2012_6_3.kupc_g;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_3.kupc_g.KUPC_G",
                "SINGLE",
                "5 3.000/__1.000 0.000/__0.000 0.000/__-1.000 0.000/__10.000 0.000/__-10.000 0.000;;3;;true::12 1.234/__0.500 0.000/__-0.500 0.000/__0.000 0.000/__0.000 -0.500/__55.500 55.000/__-55.500 55.000/__55.000 55.000/__55.000 -55.500/__99.500 99.000/__-99.500 99.000/__99.000 99.000/__99.000 -99.500;;7;;true::5 99.999/__0.000 0.000/__49.999 0.001/__0.000 0.000/__-49.999 -0.001/__0.000 0.000;;1;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
