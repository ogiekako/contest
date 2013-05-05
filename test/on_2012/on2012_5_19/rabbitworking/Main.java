package on_2012.on2012_5_19.rabbitworking;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("double getMaximum(String[] profit)",
                "on_2012.on2012_5_19.rabbitworking.RabbitWorking",
                "071,702,120;;0.017676767676767676;;true::061,602,120;;0.015228426395939087;;true::0;;0.0;;true::013040,100010,300060,000008,416000,000800;;0.021996615905245348;;true::06390061,60960062,39090270,96900262,00000212,00222026,66761201,12022610;;0.06871794871794872;;true")) {
            Assert.fail();
        }
    }
}
