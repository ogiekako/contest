package on_2012.on2012_6_7.dom2012a;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_7.dom2012a.Dom2012A",
                "SINGLE",
                "8/__1 1 1/__344 3 1/__696 5 1/__182 9 5/__998 8 7/__344 2 19/__696 4 19/__999 10 20;;196470/__128976/__59710/__160715/__252/__128977/__59712/__1;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
