package on_2012.on2012_8_2.thebricktowermediumdivone;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int[] find(int[] heights)",
                "on_2012.on2012_8_2.thebricktowermediumdivone.TheBrickTowerMediumDivOne",
                "4,7,5;;0,2,1;;true::4,4,4,4,4,4,4;;0,1,2,3,4,5,6;;true::2,3,3,2;;0,3,1,2;;true::13,32,38,25,43,47,6;;0,6,3,1,2,4,5;;true")) {
            Assert.fail();
        }
    }
}
