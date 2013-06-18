package net.ogiekako.algorithm.string;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class PalindromeTest {

    @Test
    public final void testPalindromeRadius() {
        int n = 5000;
        String s = "";
        Random r = new Random(1209812098L);
        for (int i = 0; i < n; i++) s += (char) (r.nextInt(2) + 'a');
        int[] res = Palindrome.palindromeRadius(s);
        int[] ans = palindromeRadiusStupid(s);
        assertArrayEquals(res, ans);
    }

    @Test
    public final void testPalindromeRadiusSpeed() {
        int n = 10000000;
        StringBuilder b = new StringBuilder();
        Random r = new Random(1209812098L);
        for (int i = 0; i < n; i++) b.append((char) (r.nextInt(2) + 'a'));
        long start = System.currentTimeMillis();
        Palindrome.palindromeRadius(b.toString());
        long time = System.currentTimeMillis() - start;
        assertTrue(time < 1000);
    }

    @Test
    public final void testPalindromeRadius3() {
        int n = 5000000;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++) b.append('a');
        long start = System.currentTimeMillis();
        int[] res = Palindrome.palindromeRadius(b.toString());
        long time = System.currentTimeMillis() - start;
        assertTrue(time < 1000);
        for (int i = 0; i < n; i++) assertEquals(res[i], Math.min(i, n - i - 1));
    }

    private int[] palindromeRadiusStupid(String s) {
        int n = s.length();
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int j = 0;
            while (i - j >= 0 && i + j < n && s.charAt(i - j) == s.charAt(i + j)) j++;
            res[i] = j - 1;
        }
        return res;
    }
}
