package on2012_6_5.littleelephantandbubblesort;



import net.ogiekako.algorithm.io.MyScanner;
import net.egork.chelper.task.Test;
import net.egork.chelper.tester.Verdict;

import java.util.Collection;
import java.util.Collections;

public class LittleElephantAndBubbleSortChecker {
	public Verdict check(MyScanner input, MyScanner expected, MyScanner actual) {
//		return Verdict.UNDECIDED;
        return Math.abs(expected.nextDouble() - actual.nextDouble()) < 1e-9 ? Verdict.OK : Verdict.WA;
	}

	public double getCertainty() {
		return 0;
	}

	public Collection<? extends Test> generateTests() {
		return Collections.emptyList();
	}
}
