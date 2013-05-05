package on_2012.xorboarddivtwo;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int theMax(String[] board)",
                "on_2012.xorboarddivtwo.XorBoardDivTwo",
                "101,010,101;;9;;true::111,111,111;;5;;true::0101001,1101011;;9;;true::000,001,010,011,100,101,110,111;;15;;true::000000000000000000000000,011111100111111001111110,010000000100000001000000,010000000100000001000000,010000000100000001000000,011111100111111001111110,000000100000001000000010,000000100000001000000010,000000100000001000000010,011111100111111001111110,000000000000000000000000;;105;;true")) {
            Assert.fail();
        }
    }
}
