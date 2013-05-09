package on2013_04.on2013_04_23_Croc_Champ_2013___Round_2.D___Ksusha_and_Square;


import net.egork.chelper.checkers.Checker;
import net.egork.chelper.tester.Verdict;

public class TaskDChecker implements Checker {
    public TaskDChecker(String parameters) {
    }

    public Verdict check(String input, String expectedOutput, String actualOutput) {
        double exp = Double.valueOf(expectedOutput);
        double actual = Double.valueOf(actualOutput);
        return accept(exp, actual, 1e-6) ? Verdict.OK : Verdict.WA;
    }

    private boolean accept(double expected, double actual, double eps) {
        if (Math.abs(expected - actual) < eps) return true;
        double relative = (actual - expected) / expected;
        return Math.abs(relative) < eps;
    }
}
