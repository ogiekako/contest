package on2012_5_12.taskb;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_5_12.taskb.TaskB",
			"SINGLE",
			"UTC/__A B C D E F G H I J K L M N O P Q R S T U V W X/__1/__UTC 00/:00 Akashi 09/:00/__Akashi/__00/:00;;P;;true::Tokyo/__P Q R S T U V W X A B C D E F G H I J K L M N O/__3/__Tokyo 00/:00 Connecticut 11/:00/__Moskow 19/:30 Connecticut 11/:30/__India 21/:30 Moskow 20/:00/__India/__00/:39;;T;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
