package on_2012.on2012_6_7.guessthesubstring;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("double solve(String[] pieces)",
                "on_2012.on2012_6_7.guessthesubstring.GuessTheSubstring",
                "abc;;2.6666666666666665;;true::a,bc;;2.6666666666666665;;true::aa;;1.0;;true::a,a,a,a,a;;2.1999999999999997;;true")) {
            Assert.fail();
        }
    }
}
