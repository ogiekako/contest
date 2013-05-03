package net.ogiekako.algorithm.utils.tests;

import junit.framework.Assert;
import net.ogiekako.algorithm.utils.IntegerUtils;
import net.ogiekako.algorithm.utils.StringUtils;
import org.junit.Test;

import java.util.Random;

public class IntegerUtilsTest {
    @Test
    public void testReverse() throws Exception {
        Random rnd = new Random(1208402840L);
        for (int i = 0; i < 10000; i++) {
            int k = rnd.nextInt();
            int lower = rnd.nextInt(33);
            int r = IntegerUtils.reverse(k, lower);
            String expStr = Integer.toBinaryString(k);
            while(expStr.length() < 32) expStr = "0" + expStr;
            expStr = expStr.substring(0, 32 - lower) + StringUtils.reverse(expStr.substring(32 - lower));
            int exp = (int)(long)Long.valueOf(expStr, 2);
            Assert.assertEquals(Integer.toBinaryString(exp) + " " + Integer.toBinaryString(r),exp, r);
        }
    }
}
