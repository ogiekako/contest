package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EllysJuice {
    public String[] getWinners(String[] players) {
        if (players.length == 1) return players;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String s : players) {
            if (map.containsKey(s)) map.put(s, map.get(s) + 1);
            else map.put(s, 1);
        }
        ArrayList<String> res = new ArrayList<String>();
        for (String s : map.keySet()) {
            if (map.get(s) >= 2) res.add(s);
        }
        Collections.sort(res);
        return res.toArray(new String[0]);
    }
}
