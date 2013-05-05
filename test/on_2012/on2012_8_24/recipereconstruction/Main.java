package on_2012.on2012_8_24.recipereconstruction;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_24.recipereconstruction.RecipeReconstruction",
                "MULTI_NUMBER",
                "5/__?/__??/__ab?/__a?c/__aba/__;;26/__26/__1/__0/__1/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
