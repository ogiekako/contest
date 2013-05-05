package on_2012.on2012_6_8.linearkingdomparkinglot;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int borrowKeys(int[] exitOrder)",
                "on_2012.on2012_6_8.linearkingdomparkinglot.LinearKingdomParkingLot",
                "4,1,0,2,3;;1;;true::0,1;;0;;true::1,0,3,2,4;;1;;true::4,0,2,1,5,3;;2;;true::0,2,4,1,3;;2;;true")) {
            Assert.fail();
        }
    }
}
