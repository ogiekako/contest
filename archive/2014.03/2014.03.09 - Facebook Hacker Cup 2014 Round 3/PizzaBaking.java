package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class PizzaBaking {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int K = in.nextInt();
        int[] C = new int[K];
        for (int i = 0; i < K; i++) {
            C[i] = in.nextInt();
        }
        int N = in.nextInt();
        int[] S = new int[N], T = new int[N];
        for (int i = 0; i < N; i++) {
            S[i] = in.nextInt();
            T[i] = in.nextInt() + 1;
        }

        int M = 0;
        int[] sum = new int[K];
        for (int i = 0; i < K; i++) {
            sum[i] = 0;
            for (int j = 0; j < N; j++) {
                if (S[j] <= i && i < T[j]) {
                    sum[i]++;
                }
            }
            M = Math.max(M, (sum[i] + C[i] - 1) / C[i]);
        }

        int source = K + 1, sink = K + 2;
        int[] R = new int[N];
        Arrays.fill(R, -1);
        for (int oven = 0; oven < M; oven++) {
            int[][] cap = new int[K + 3][K + 3];
            for (int i = 0; i < K; i++) {
                cap[i][i + 1] += (M - oven) * C[i] - sum[i];
                if (cap[i][i + 1] < 0) throw new AssertionError();
            }
            for (int i = 0; i < N; i++) {
                if (R[i] == -1) {
                    cap[S[i]][T[i]] += 1;
                }
            }
            int flowValue = 0;
            for (int i = 0; i <= K; i++) {
                if (i == 0) {
                    cap[source][i] += C[i];
                } else if (i == K) {
                    cap[i][sink] += C[i - 1];
                } else {
                    if (C[i - 1] < C[i]) cap[source][i] += C[i] - C[i - 1];
                    else cap[i][sink] += C[i - 1] - C[i];
                }
                flowValue += cap[source][i];
            }
            for (int i = 0; i < flowValue; i++) {
                boolean[] visited = new boolean[K + 3];
                boolean success = dfs(cap, visited, source, sink);
                if (!success) throw new AssertionError();
            }
            for (int i = 0; i < N; i++) {
                if (R[i] == -1) {
                    if (cap[T[i]][S[i]] == 0) {
                        boolean success = dfs(cap, new boolean[K + 3], T[i], S[i]);
                        if (success) {
                            if (cap[S[i]][T[i]] <= 0) throw new AssertionError();
                            cap[S[i]][T[i]]--;
                            cap[T[i]][S[i]]++;
                        }
                    }
                    if (cap[T[i]][S[i]] > 0) {
                        cap[T[i]][S[i]]--;
                        cap[T[i]][source]++;
                        cap[sink][S[i]]++;
                        R[i] = oven;
                        for (int j = S[i]; j < T[i]; j++) {
                            sum[j]--;
                        }
                    }
                }
            }
        }
        out.printFormat("Case #%d: ", testNumber);
        for (int i = 0; i < N; i++) {
            out.printFormat("%d%c", R[i], i == N - 1 ? '\n' : ' ');
        }
    }
    private boolean dfs(int[][] cap, boolean[] visited, int source, int sink) {
        if (visited[source]) return false;
        visited[source] = true;
        if (source == sink) return true;
        for (int i = 0; i < cap.length; i++) {
            if (cap[source][i] > 0) {
                boolean success = dfs(cap, visited, i, sink);
                if (success) {
                    cap[source][i]--;
                    cap[i][source]++;
                    return true;
                }
            }
        }
        return false;
    }
}
