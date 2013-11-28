package tmp;

public class Chopsticks {
    public int getmax(int[] length) {
        int[] cnt = new int[110];
        for (int len : length) cnt[len]++;
        int res = 0;
        for (int c : cnt) res += c / 2;
        return res;
    }
}
