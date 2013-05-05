package on_2012.on2012_5_26.contestwinner;


// Paste me into the FileEdit configuration dialog

public class ContestWinner {
    public int getWinner(int[] events) {
        int[] cnt = new int[1000010];
        int res = 0;
        for (int i : events) {
            cnt[i]++;
            if (cnt[i] > cnt[res]) res = i;
        }
        return res;
    }


}

