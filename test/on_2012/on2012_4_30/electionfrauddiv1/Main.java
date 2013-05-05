package on_2012.on2012_4_30.electionfrauddiv1;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int MinimumVoters(int[] percentages)",
                "on_2012.on2012_4_30.electionfrauddiv1.ElectionFraudDiv1",
                "33,33,33;;3;;true::29,29,43;;7;;true::12,12,12,12,12,12,12,12;;-1;;true::13,13,13,13,13,13,13,13;;8;;true::0,1,100;;200;;true::3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3,8,4;;97;;true::100;;1;;true::0,0,0,0,0,0,0,0,0,0,0,0,0,0,9;;-1;;true::49,48,0,0,0;;-1;;true")) {
            Assert.fail();
        }
    }
}
