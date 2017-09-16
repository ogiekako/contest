package net.ogiekako.algorithm.io;

import net.egork.chelper.tester.StringInputStream;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyScannerTest {
    @Test
    public void nextInt() {
        MyScanner in = new MyScanner(new StringInputStream("" + Integer.MAX_VALUE));
        Assert.assertEquals(Integer.MAX_VALUE, in.nextInt());

        in = new MyScanner(new StringInputStream("" + Integer.MIN_VALUE));
        Assert.assertEquals(Integer.MIN_VALUE, in.nextInt());
    }

    @Test
    public void nextIntError() {
        MyScanner in = new MyScanner(new StringInputStream("" + ((long) Integer.MAX_VALUE + 1)));
        try {
            in.nextInt();
        } catch (IllegalArgumentException e) {
            // ok
            return;
        }
        Assert.fail();
    }

    @Test
    public void nextIntError2() {
        MyScanner in = new MyScanner(new StringInputStream("" + ((long) Integer.MIN_VALUE - 1)));
        try {
            in.nextInt();
        } catch (IllegalArgumentException e) {
            // ok
            return;
        }
        Assert.fail();
    }

}