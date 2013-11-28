package tmp;

public class TheJediTest {
    public int minimumSupervisors(int[] students, int K) {
        int n = students.length;
        int[] moved = new int[n];
        int res = Integer.MAX_VALUE;
        for (int mask = 0; mask < 1 << n - 1; mask++) {
            System.arraycopy(students, 0, moved, 0, n);
            for (int i = 0; i < n - 1; i++) {
                if (mask << 31 - i < 0) {// ->
                    int remainder = moved[i] % K;
                    if (students[i] >= remainder) {
                        moved[i] -= remainder;
                        moved[i + 1] += remainder;
                    }
                } else {// <-
                    int accept = K - moved[i] % K;
                    if (accept == K) accept = 0;
                    accept = Math.min(accept, students[i + 1]);
                    moved[i + 1] -= accept;
                    moved[i] += accept;
                }
            }
            int sum = 0;
            for (int a : moved) sum += (a + K - 1) / K;
            res = Math.min(res, sum);
        }
        return res;
    }
}
