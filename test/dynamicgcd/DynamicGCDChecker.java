package dynamicgcd;



import net.ogiekako.algorithm.io.MyScanner;
import net.egork.chelper.task.Test;
import net.egork.chelper.tester.Verdict;

import java.util.Collection;
import java.util.Collections;

public class DynamicGCDChecker {
	public Verdict check(MyScanner input, MyScanner expected, MyScanner actual) {
        class test{

            public int get() {
                return 0;
            }
        }
        int a = new test().get();
		return Verdict.UNDECIDED;
	}

	public double getCertainty() {
		return 0;
	}

	public Collection<? extends Test> generateTests() {
		return Collections.emptyList();
	}
}
