package on_2012.on2012_6_9.theblackjackdivone;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("double expected(String[] cards)",
                "on_2012.on2012_6_9.theblackjackdivone.TheBlackJackDivOne",
                "JS;;2.105854341736695;;true::KD,8S;;1.08;;true::KD,2S,2C,2D,2H;;1.0;;true::AS,KS,9S,JC,2D;;0.0;;true")) {
            Assert.fail();
        }
    }
}
