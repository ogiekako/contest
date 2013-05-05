package on_2012.on2012_8_15.leftrightdigitsgame2;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("String minNumber(String digits, String lowerBound)",
                "on_2012.on2012_8_15.leftrightdigitsgame2.LeftRightDigitsGame2",
                "565;;556;;556;;true::565;;566;;655;;true::565;;656;;;;true::9876543210;;5565565565;;5678943210;;true::8016352;;1000000;;1086352;;true")) {
            Assert.fail();
        }
    }
}
