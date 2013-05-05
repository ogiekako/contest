package on_2012.on2012_6_10.magicalhats;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int findMaximumReward(String[] board, int[] coins, int numGuesses)",
                "on_2012.on2012_6_10.magicalhats.MagicalHats",
                "H;;1;;1;;1;;true::HHH,H.H,HH.;;9;;1;;9;;true::HH,HH;;1,2,3,4;;3;;6;;true::HHH,HHH,H.H;;13,13,13,13;;2;;13;;true::HHH,HHH,H.H;;13,13,13,13;;3;;26;;true::H.H.,.H.H,H.H.;;17;;6;;-1;;true::HHH,H.H,HHH,H.H,HHH;;33,337,1007,2403,5601,6003,9999;;5;;1377;;true::.............,.............,.............,.............,.............,.............,.....H.H.....,......H......,.....H.H.....,.............,.............,.............,.............;;22;;3;;22;;true::HH;;1;;1;;-1;;true")) {
            Assert.fail();
        }
    }
}
