package on2012_4_26.spoj8791dynamiclca;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_4_26.spoj8791dynamiclca.SPOJ8791DynamicLCA",
			"SINGLE",
			"5 9/__lca 1 1/__link 1 2/__link 3 2/__link 4 3/__lca 1 4/__lca 3 4/__cut 4/__link 5 3/__lca 1 5;;1/__2/__3/__2/__;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
