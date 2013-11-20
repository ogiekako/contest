package on2013_09.on2013_09_27_Single_Round_Match_592.LittleElephantAndBalls;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class LittleElephantAndBalls {
    static String C = "RGB";
    public int getNumber(String S) {
        HashMap<String, Integer> dp = new HashMap<String, Integer>();
        dp.put("", 0);
        for (char c : S.toCharArray()) {
            HashMap<String, Integer> nDp = new HashMap<String, Integer>();
            for (Map.Entry<String, Integer> e : dp.entrySet()) {
                String cur = e.getKey();
                int val = e.getValue();
                for (int i = 0; i < cur.length() + 1; i++) {
                    int nVal = val;
                    int mask = 0;
                    for (int j = 0; j < i; j++) mask |= 1 << C.indexOf(cur.charAt(j));
                    nVal += Integer.bitCount(mask);
                    mask = 0;
                    for (int j = i; j < cur.length(); j++) mask |= 1 << C.indexOf(cur.charAt(j));
                    nVal += Integer.bitCount(mask);

                    String nStr = cur.substring(0, i) + c + cur.substring(i);
                    boolean[] take = new boolean[nStr.length()];
                    for (char col : C.toCharArray()) {
                        for (int j = 0; j < nStr.length(); j++) {
                            if (nStr.charAt(j) == col) {
                                take[j] = true; break;
                            }
                        }
                        for (int j = nStr.length() - 1; j >= 0; j--) {
                            if (nStr.charAt(j) == col) {
                                take[j] = true; break;
                            }
                        }
                    }
                    String nStr2 = "";
                    for (int j = 0; j < nStr.length(); j++) if (take[j]) nStr2 += nStr.charAt(j);
                    if (!nDp.containsKey(nStr2)) nDp.put(nStr2, 0);
                    nDp.put(nStr2, Math.max(nVal, nDp.get(nStr2)));
                }
            }
            dp = nDp;
        }
        int res = 0;
        for (int val : dp.values()) res = Math.max(res, val);
        return res;
    }
    public static void main(String[] args) {
        String S = "";
        Random rnd = new Random(10412048L);
        for (int iter = 0; ; iter++) {
            if (iter % 10000 == 10000 - 1) System.err.println(iter);
            S = "";
            for (int i = 0; i < 6; i++) {
                S += C.charAt(rnd.nextInt(3));
            }
//            System.err.println(S);
            long time = System.currentTimeMillis();
            int res = new LittleElephantAndBalls().getNumber(S);
            int res2 = new LittleElephantAndBalls().getNumber2(S);
            if (res != res2) {
                System.err.println(S + " " + res + " " + res2);
            }
//        System.err.println(System.currentTimeMillis() - time);
        }
    }
    private int getNumber2(String S) {
        int res = 0;
        int n = S.length();
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (Map.Entry<Character, Integer> it : map.entrySet()) {
                sum += it.getValue();
            }
            res += sum;
            if (!map.containsKey(S.charAt(i))) map.put(S.charAt(i), 0);
            map.put(S.charAt(i), Math.min(2, map.get(S.charAt(i)) + 1));

        }
        return res;
    }
}
