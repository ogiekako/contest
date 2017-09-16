package on2017_09.on2017_09_09_TopCoder_Open_Round__1.RewardOnATree;



import java.util.Arrays;

public class RewardOnATree {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int collect(int[] parent, int[] reward) {
        int N = reward.length;
        int[] depth = new int[N];
        for (int i = 0; i < N - 1; i++) {
            depth[i + 1] = depth[parent[i]] + 1;
        }
        int[] best = new int[N];
        for (int i = 0; i < N; i++) {

        }
        return 0;
    }
}
