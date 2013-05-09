package net.ogiekako.algorithm.string.tests;

import net.ogiekako.algorithm.dataStructure.persistent.PersistentLinkedList;
import net.ogiekako.algorithm.string.AhoCorasick;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Pair;
import net.ogiekako.algorithm.utils.TestUtils;
import net.ogiekako.algorithm.utils.interfaces.Function;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/04/02
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class AhoCorasickTest {
    @Test
    public void test() throws Exception {
        int n = 10;
        Random rnd = new Random(120941820498L);
        for (int i = 1; i <= 10000; i++) {
            AhoCorasick ac = new AhoCorasick("ACGT");
            String[] patterns = new String[n];
            for (int j = 0; j < n; ) {
                int len = rnd.nextInt(5) + 1;
                patterns[j] = TestUtils.generateRandomString("ACGT", len, rnd);
                boolean ok = true;
                for (int k = 0; k < j; k++) if (patterns[k].equals(patterns[j])) ok = false;
                if (!ok) continue;
                j++;
            }
            String str = TestUtils.generateRandomString("ACGT", 500, rnd);
            ac.construct(patterns);
            final ArrayList<String>[] lists = ArrayUtils.createArray(str.length(), new ArrayList<String>());
            for (int k = 0; k < lists.length; k++) lists[k] = new ArrayList<String>();
            ac.match(str, new Function<Pair<Integer, PersistentLinkedList<String>>, Void>() {
                public Void f(Pair<Integer, PersistentLinkedList<String>> argument) {
                    int id = argument.first;
                    PersistentLinkedList<String> matchList = argument.second;
                    int befLen = Integer.MAX_VALUE;
                    while (!matchList.isEmpty()) {
                        String str = matchList.car();
                        if (str.length() >= befLen) throw new AssertionError();
                        befLen = str.length();
                        lists[id].add(str);
                        matchList = matchList.cdr();
                    }
                    return null;
                }
            });
            ArrayList<String>[] exp = calcStupid(patterns, str);
            Assert.assertArrayEquals(lists, exp);
        }
    }
    private ArrayList<String>[] calcStupid(String[] patterns, String str) {
        ArrayList<String>[] lists = ArrayUtils.createArray(str.length(), new ArrayList<String>());
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<String>();
        }
        for (int i = 0; i < str.length(); i++) {
            for (String p : patterns) {
                if (p.length() <= i + 1 && str.substring(i + 1 - p.length(), i + 1).equals(p)) {
                    lists[i].add(p);
                }
            }
            Collections.sort(lists[i], new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return -(o1.length() - o2.length());
                }
            });
        }
        return lists;
    }
}
