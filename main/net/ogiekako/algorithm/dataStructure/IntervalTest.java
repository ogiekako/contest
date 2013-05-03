package net.ogiekako.algorithm.dataStructure;

import junit.framework.Assert;
import org.junit.Test;

public class IntervalTest {
    int TO = 6;
    private Interval[] intervals(){
        Interval[] res = new Interval[TO * TO];
        int ptr = 0;
        for (int i = 0; i < TO; i++) for (int j = 0; j < TO; j++){
            res[ptr++] = Interval.of(j,i);
        }
        return res;
    }

    @Test
    public void testLength() throws Exception {
        Interval[] is = intervals();
        for(Interval i : is){
            long exp = i.right - i.left;
            long res = i.length();
            Assert.assertEquals(exp, res);
        }
    }

    @Test
    public void testCanUnion() throws Exception {
        Interval[] is = intervals();
        int variationMask = 0;
        for(Interval i : is)for(Interval j : is){
            long maxLeft = Math.max(i.left, j.left);
            long minRight = Math.min(i.right, j.right);
            boolean exp = maxLeft <= minRight;
            boolean res = i.canUnion(j);
            Assert.assertEquals("" + i + " " + j,exp, res);
            if(res)variationMask |= 1;
            else variationMask |= 2;
        }
        Assert.assertEquals(3, variationMask);
    }

    @Test
    public void testUnion() throws Exception {
        Interval[] is = intervals();
        for(Interval i : is)for(Interval j : is) if(i.canUnion(j)){
            Interval res = i.union(j);
            long left = Math.min(i.left, j.left);
            long right = Math.max(i.right, j.right);
            Interval exp = Interval.of(left, right);
            Assert.assertEquals(exp, res);
        }
    }

    @Test
    public void testIntersection() throws Exception {
        Interval[] is = intervals();
        for(Interval i : is)for(Interval j : is) {
            Interval res = i.intersection(j);
            long maxLeft = Math.max(i.left, j.left);
            long minRight = Math.min(i.right, j.right);
            Interval exp;
            if(maxLeft < minRight)exp = Interval.of(maxLeft, minRight);
            else exp = Interval.of(maxLeft, maxLeft);

            Assert.assertEquals(exp, res);
        }
    }

    @Test
    public void testContains() throws Exception {
        Interval[] is = intervals();
        for(Interval i : is)for(int j=-1;j< TO +1;j++) {
            boolean exp = i.left <= j && j < i.right;
            boolean res = i.contains(j);
            Assert.assertEquals(exp, res);
        }
    }
}
