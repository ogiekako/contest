package tenkad;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"tenkad.TenkaD",
			"SINGLE",
			"4 4/__...H/__#H../__...#/__H..H;;5;;true::4 4/__..../__..../__.HHH/__....;;10;;true::4 4/__...#/__.#.#/__.#.#/__H#.H;;0;;true::4 4/__HH.H/__H##./__.##./__H..H;;-1;;true::10 10/__..##.H..../__.H.#....../__...#####../__##......#./__.#...#.H../__.....#..../__.##H..####/__..##....H./__.H..#...../__...#......;;36;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
