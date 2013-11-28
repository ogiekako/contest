package tmp;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.Verdict;

import java.util.Collection;
import java.util.Collections;

public class CarefulCalculationChecker {
    public Verdict check(net.ogiekako.algorithm.io.MyScanner a, net.ogiekako.algorithm.io.MyScanner b, net.ogiekako.algorithm.io.MyScanner c) {
        return Verdict.UNDECIDED;
    }

    public double getCertainty() {
        return 0;
    }

    public Collection<? extends Test> generateTests() {
        return Collections.emptyList();
    }
}
