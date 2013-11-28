package tmp;

import net.ogiekako.algorithm.utils.TestUtils;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/06
 * Time: 2:36
 * To change this template use File | Settings | File Templates.
 */
public class TaskCTest {
    public static void main(String[] args) {
        Random rnd = new Random(120491824L);
        for (int o = 0; o < 50; o++) {
            int n = 500;
            long[] S = new long[n];
            for (int i = 0; i < n; i++) S[i] = TestUtils.randomLong(1000000000000L, rnd);
            long start = System.currentTimeMillis();
            new TaskC().solve(S);
            long time = System.currentTimeMillis() - start;
            System.err.println(time);
        }
    }


}
