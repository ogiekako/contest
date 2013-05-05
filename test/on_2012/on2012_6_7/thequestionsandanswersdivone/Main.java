package on_2012.on2012_6_7.thequestionsandanswersdivone;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("int find(int questions, String[] answers)",
                "on_2012.on2012_6_7.thequestionsandanswersdivone.TheQuestionsAndAnswersDivOne",
                "2;;No,Yes;;2;;true::2;;No,No,No;;6;;true::3;;Yes,No,No,Yes;;12;;true::3;;Yes,Yes,Yes,No;;18;;true")) {
            Assert.fail();
        }
    }
}
