package on_2012.on2012_6_7.chromaticnumber;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int minColors(String[] graph)",
                "on_2012.on2012_6_7.chromaticnumber.ChromaticNumber",
                "N;;1;;true::NYY,YNN,YNN;;2;;true::NYNN,YNNN,NNNY,NNYN;;2;;true::NYNY,YNYY,NYNN,YYNN;;3;;true::NYYYYYYY,YNYYYYYY,YYNYYYYY,YYYNYYYY,YYYYNYYY,YYYYYNYY,YYYYYYNY,YYYYYYYN;;8;;true")) {
            Assert.fail();
        }
    }
}
