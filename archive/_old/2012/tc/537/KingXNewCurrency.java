package tmp;

// Paste me into the FileEdit configuration dialog

public class KingXNewCurrency {
    public int howMany(int A, int B, int X) {
        if (A % X == 0 && B % X == 0) return -1;
        int res = 0;
        for (int Y = 1; Y <= 200; Y++) {
            boolean ok = can(A, X, Y) && can(B, X, Y);
            if (ok) res++;
        }
        return res;
    }

    private boolean can(int A, int X, int Y) {
        for (int k = 0; k * X <= A; k++) {
            if ((A - k * X) % Y == 0) return true;
        }
        return false;
    }


}

