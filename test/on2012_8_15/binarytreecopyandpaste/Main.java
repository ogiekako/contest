package on2012_8_15.binarytreecopyandpaste;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_8_15.binarytreecopyandpaste.BinaryTreeCopyAndPaste",
			"SINGLE",
			"((()())(((()())(()()))()))/__(((()())(()()))(()()))/__((()(()()))((()(()()))()))/__(()())/__#;;3/__4/__3/__0;;true::((()())((()())()))/__(((()())(()()))(()()))/__((()(()()))((()(()()))()))/__((()())((()())()));;2/__4/__3/__2;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
