package tmp;

import java.util.Arrays;

public class SwitchesAndLamps {
    public int theMin(String[] switches, String[] lamps) {
        switches = rev(switches);
        lamps = rev(lamps);
        Arrays.sort(switches);
        Arrays.sort(lamps);
        if (!Arrays.equals(switches, lamps)) return -1;
        int res = 0;
        for (int i = 0; i < switches.length; ) {
            int j = i;
            while (j < switches.length && switches[i].equals(switches[j])) j++;
            res = Math.max(32 - Integer.numberOfLeadingZeros(j - i - 1), res);
            i = j;
        }
        return res;
    }

    private String[] rev(String[] ss) {
        String[] res = new String[ss[0].length()];
        for (int i = 0; i < ss[0].length(); i++) {
            res[i] = "";
            for (int j = 0; j < ss.length; j++) res[i] += ss[j].charAt(i);
        }
        return res;
    }
}
