package on_2012.on2012_4_26.ellysxors;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("long getXor(long L, long R)",
                "on_2012.on2012_4_26.ellysxors.EllysXors",
                "3;;10;;8;;true::5;;5;;5;;true::13;;42;;39;;true::666;;1337;;0;;true::1234567;;89101112;;89998783;;true")) {
            Assert.fail();
        }
    }
}
