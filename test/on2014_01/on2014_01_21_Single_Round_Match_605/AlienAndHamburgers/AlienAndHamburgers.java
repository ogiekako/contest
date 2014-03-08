package on2014_01.on2014_01_21_Single_Round_Match_605.AlienAndHamburgers;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class AlienAndHamburgers {
    public int getNumber(int[] type, int[] taste) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < type.length; i++) {
            if (!map.containsKey(type[i])) map.put(type[i], new ArrayList<Integer>());
            map.get(type[i]).add(taste[i]);
            Collections.sort(map.get(type[i]));
        }
        List<Integer> list = new ArrayList<>();
        for (List<Integer> l : map.values()) {
            if (l.get(l.size() - 1) < 0) list.add(l.get(l.size() - 1));
            else {
                int s = 0;
                for (int t : l) if (t > 0) s += t;
                list.add(s);
            }
        }
        Collections.sort(list);
        int res = 0;
        for (int m = 0; m < list.size() + 1; m++) {
            int s = 0;
            for (int i = 0; i < m; i++) s += list.get(list.size() - 1 - i);
            res = Math.max(res, s * m);
        }
        return res;
    }
}
