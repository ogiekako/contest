package on_2012.on2012_5_26.kleofastail;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("long countGoodSequences(long K, long A, long B)",
                "on_2012.on2012_5_26.kleofastail.KleofasTail",
                "3;;4;;8;;2;;true::1;;23457;;123456;;100000;;true::1234567890123456;;10;;1000000;;0;;true::0;;0;;2;;3;;true::2;;3;;3;;1;;true::13;;12345;;67890123;;8387584;;true")) {
            Assert.fail();
        }
    }
}
