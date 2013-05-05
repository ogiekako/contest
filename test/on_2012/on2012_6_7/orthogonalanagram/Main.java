package on_2012.on2012_6_7.orthogonalanagram;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("String solve(String S)",
                "on_2012.on2012_6_7.orthogonalanagram.OrthogonalAnagram",
                "dcba;;abcd;;true::edcba;;abdce;;true::aaaaa;;;;true::abba;;baab;;true")) {
            Assert.fail();
        }
    }
}
