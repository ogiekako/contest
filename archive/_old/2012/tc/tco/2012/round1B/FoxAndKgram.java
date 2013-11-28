package tmp;

// Paste me into the FileEdit configuration dialog

public class FoxAndKgram {
    public int maxK(int[] len) {
        for (int k = 55; k >= 0; k--) {
            int[] cnt = new int[60];
            for (int l : len) cnt[l]++;
            boolean ok = true;
            for (int i = 0; i < k; i++) {
                if (cnt[k] > 0) cnt[k]--;
                else {
                    boolean ok2 = false;
                    for (int j = 0; j < k; j++) {
                        int nj = k - j - 1;
                        if (cnt[j] >= 2 && cnt[nj] >= 2 || j != nj && cnt[j] >= 1 && cnt[nj] >= 1) {
                            ok2 = true;
                            cnt[j]--; cnt[nj]--;
                            break;
                        }
                    }
                    if (!ok2) ok = false;
                }
            }
            if (ok) return k;
        }
        throw new RuntimeException();
    }


}

