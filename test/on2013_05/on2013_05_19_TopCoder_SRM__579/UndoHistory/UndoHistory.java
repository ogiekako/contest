package on2013_05.on2013_05_19_TopCoder_SRM__579.UndoHistory;


import java.util.HashSet;
public class UndoHistory {
    public int minPresses(String[] lines) {
        HashSet<String> set = new HashSet<String>();
        int res = 0;
        res += lines.length;
        res += lines[0].length();
        for (int i = 0; i <= lines[0].length(); i++) set.add(lines[0].substring(0, i));
        for (int i = 0; i < lines.length - 1; i++) {
            int add1 = Integer.MAX_VALUE;
            if (lines[i + 1].startsWith(lines[i])) {
                add1 = lines[i + 1].length() - lines[i].length();
            }
            int add2 = 2;
            for (int j = lines[i + 1].length(); ; j--)
                if (set.contains(lines[i + 1].substring(0, j))) {
                    add2 += lines[i + 1].length() - j;
                    break;
                }
            res += Math.min(add1, add2);
            for (int j = 0; j <= lines[i + 1].length(); j++) set.add(lines[i + 1].substring(0, j));
        }
        return res;
    }
}
