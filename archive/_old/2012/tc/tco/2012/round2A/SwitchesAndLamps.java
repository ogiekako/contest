package tmp;

// Paste me into the FileEdit configuration dialog

public class SwitchesAndLamps {
    public int theMin(String[] switches, String[] lamps) {
        int maxSame = 0;
        for (int i = 0; i < lamps[0].length(); i++) {
            String str = "";
            for (String lamp : lamps) str += lamp.charAt(i);
            int countLamp = 0;
            for (int j = 0; j < lamps[0].length(); j++) {
                String str2 = "";
                for (int k = 0; k < lamps.length; k++) str2 += lamps[k].charAt(j);
                if (str.equals(str2)) {
                    countLamp++;
                }
            }
            int countSwitch = 0;
            for (int j = 0; j < switches[0].length(); j++) {
                String str2 = "";
                for (int k = 0; k < switches.length; k++) str2 += switches[k].charAt(j);
                if (str.equals(str2)) {
                    countSwitch++;
                }
            }
            if (countLamp != countSwitch) return -1;
            maxSame = Math.max(maxSame, countLamp);
        }
        int res = 0;
        while (maxSame > 1 << res) res++;
        return res;
    }


}

