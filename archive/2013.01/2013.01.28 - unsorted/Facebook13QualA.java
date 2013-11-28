package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class Facebook13QualA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        String s = in.nextLine();
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            if ('a' <= c && c <= 'z') cnt[c - 'a']++;
            if ('A' <= c && c <= 'Z') cnt[c - 'A']++;
        }
        Arrays.sort(cnt);
        long res = 0;
        for (int i = 0; i < 26; i++) res += cnt[i] * (i + 1);
        out.println(res);
    }
}
