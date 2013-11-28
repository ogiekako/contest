package tmp;

import java.util.ArrayList;
import java.util.List;

public class FoxAndMp3 {
    public String[] playList(int n) {
        List<String> result = new ArrayList<String>();
        for (int i = 1; i <= 9; i++) {
            dfs(i, n, result);
        }
        return result.toArray(new String[result.size()]);
    }

    private void dfs(long cur, int n, List<String> result) {
        if (result.size() >= 50) return;
        if (cur > n) return;
        result.add(String.format("%d.mp3", cur));
        for (int i = 0; i < 10; i++) {
            dfs(cur * 10 + i, n, result);
        }
    }
}
