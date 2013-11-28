package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class TurtleSpy {
    public double maxDistance(String[] commands) {
        boolean[][] can = new boolean[2][360];
        can[0][180] = true;
        int cur = 0, nxt = 1;
        int F = 0, B = 0;
        for (String s : commands) {
            String[] ss = s.split(" ");
            switch (ss[0].charAt(0)) {
                case 'f':
                    F += Integer.valueOf(ss[1]);
                    break;
                case 'b':
                    B += Integer.valueOf(ss[1]);
                    break;
                case 'r':
                    int d = Integer.valueOf(ss[1]);
                    next(can[cur], can[nxt], d);
                    int tmp = cur; cur = nxt; nxt = tmp;
                    break;
                case 'l':
                    d = 360 - Integer.valueOf(ss[1]);
                    next(can[cur], can[nxt], d);
                    tmp = cur; cur = nxt; nxt = tmp;
                    break;
            }
        }
        double res = 0;
        for (int i = 0; i < 360; i++)
            if (can[cur][i]) {
                double x = B * Math.sin(rad(i));
                double y = F + B * Math.cos(rad(i));
                res = Math.max(res, Math.hypot(x, y));
            }
        return res;
    }

    private double rad(int i) {
        return i * 2 * Math.PI / 360;
    }

    private void next(boolean[] cur, boolean[] nxt, int d) {
        Arrays.fill(nxt, false);
        for (int i = 0; i < 360; i++) {
            if (cur[i]) {
                nxt[i] = true;
                nxt[(i + d) % 360] = true;
            }
        }
    }


}

