package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TaskH {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        HashSet<Character> set = new HashSet<>();
        Set<Character> firsts = new HashSet<>();
        for(String s:S){
            for(char c:s.toCharArray())set.add(c);
        }
        char[] cs = toCs(set.toArray(new Character[0]));
        char three = '△';
        char two = '▽';
        int[] is = new int[10];
        for (int i = 0; i < is.length; i++) {
            is[i] = i;
        }
        do {
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < 10; i++) {
                map.put(cs[i], is[i]);
            }
            if (map.get(three) != 3 || map.get(two) != 2)continue;
            long[] ps = new long[S.length];
            boolean ok = true;
            for (int i = 0; i < S.length; i++) {
                if (S[i].length() > 1 && map.get(S[i].charAt(0)) == 0)ok = false;
                ps[i] = 0;
                for (int j = 0; j < S[i].length(); j++) {
                    ps[i] *= 10;
                    ps[i] += map.get(S[i].charAt(j));
                }
                if (ps[i] >= (1L<<32)) ok = false;
            }
            if (ok) {
                for(long p : ps){
                    System.out.println(p);
                }
            }

        } while(ArrayUtils.nextPermutation(is));

        System.out.println(set);
    }

    private char[] toCs(Character[] Cs) {
        char[] cs = new char[Cs.length];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = Cs[i];
        }
        return cs;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(new File("").getAbsolutePath());
        Scanner sc = new Scanner(new File("java/H1.in"));
        List<Byte> byteList = new ArrayList<>();
        while (sc.hasNextByte()) {
            byte b = sc.nextByte();
            byteList.add(b);
        }
        System.out.println(byteList.size());
    }


    String[] S = {
            "※ロ☆※〇☆▽※〒回",
            "△◇※▽〇※",
            "△ロ◇▽回▽〇▽△※",
            "△△〒△☆回§※☆〒",
            "☆▽〒〒回◇◇§※〇",
            "※▽◇※◇〇〒ロ※§",
            "回回◇◇▽〒☆◇",
            "☆▽◇※◇§◇△▽回",
            "回◇ロロ※◇回",
            "※▽§◇△〇☆〇〒◇",
            "▽ロ☆※",
            "☆▽§§〇〇〇ロ回回",
            "☆☆△§※§ロ☆〒〇",
            "☆▽〇◇〒▽☆△〒〒",
            "☆ロ◇回ロ回ロ◇※回",
            "△〇☆ロ▽回☆§ロ回",
            "※▽◇※◇§◇◇※◇",
            "△△回※☆",
            "※▽回回〇〒▽※〇回",
            "△〒ロ回◇▽☆※☆§",
            "▽☆〒※〒△▽ロ◇回",
            "※▽〇〇▽§回〇回☆",
            "ロ",
            "▽◇▽※〒§§〒☆〒",
            "※▽〒回〇§〇※※☆",
            "回ロ",
            "▽回§△※◇§ロ△回",
            "☆▽◇※◇〇〒ロ△◇",
            "▽〒〇回▽△※〇▽▽",
            "☆▽回〒〇§▽〒△回",
            "▽回〇",
            "回回◇ロ§☆※〇☆"};
}
