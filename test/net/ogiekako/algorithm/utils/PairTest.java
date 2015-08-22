package net.ogiekako.algorithm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PairTest {

    @Test
    public void compareShouldWork() throws Exception {
        List<Pair<Integer, Double>> ps = new ArrayList<Pair<Integer, Double>>();
        ps.add(new Pair<Integer, Double>(2, 0.1));
        ps.add(new Pair<Integer, Double>(1, 0.2));
        ps.add(new Pair<Integer, Double>(2, 0.2));
        ps.add(new Pair<Integer, Double>(4, 0.1));
        ps.add(new Pair<Integer, Double>(1, 0.1));
        ps.add(new Pair<Integer, Double>(2, 0.1));

        Collections.sort(ps);
        List<Pair<Integer, Double>> expected = new ArrayList<Pair<Integer, Double>>();
        expected.add(new Pair<Integer, Double>(1, 0.1));
        expected.add(new Pair<Integer, Double>(1, 0.2));
        expected.add(new Pair<Integer, Double>(2, 0.1));
        expected.add(new Pair<Integer, Double>(2, 0.1));
        expected.add(new Pair<Integer, Double>(2, 0.2));
        expected.add(new Pair<Integer, Double>(4, 0.1));

        Assert.assertEquals(expected, ps);
    }
}