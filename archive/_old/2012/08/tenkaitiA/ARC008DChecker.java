package tmp;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.Verdict;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Collection;
import java.util.Collections;

public class ARC008DChecker {
    public Verdict check(MyScanner input, MyScanner expected, MyScanner actual) {
        return Verdict.UNDECIDED;
    }

    public double getCertainty() {
        return 1e-6;
    }

    public Collection<? extends Test> generateTests() {
        return Collections.emptyList();
    }
}
