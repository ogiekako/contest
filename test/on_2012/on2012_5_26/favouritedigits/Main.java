package on_2012.on2012_5_26.favouritedigits;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("long findNext(long N, int digit1, int count1, int digit2, int count2)",
                "on_2012.on2012_5_26.favouritedigits.FavouriteDigits",
                "47;;1;;0;;2;;0;;47;;true::47;;5;;0;;9;;1;;49;;true::47;;5;;0;;3;;1;;53;;true::47;;2;;1;;0;;2;;200;;true::123456789012345;;1;;2;;2;;4;;123456789012422;;true::92;;1;;1;;0;;0;;100;;true")) {
            Assert.fail();
        }
    }
}
