package on_2012.on2012_6_7.dom2012e;


import net.egork.chelper.task.Test;
import net.egork.chelper.tester.Verdict;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Collection;
import java.util.Collections;

public class Dom2012EChecker {
    public Verdict check(MyScanner input, MyScanner expected, MyScanner actual) {
        return Math.abs(expected.nextDouble() - actual.nextDouble()) < 1e-6 ? Verdict.OK : Verdict.WA;
    }

    public double getCertainty() {
        return 0;
    }

    public Collection<? extends Test> generateTests() {
        return Collections.emptyList();
    }
}
