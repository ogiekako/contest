package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class ARC005B {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int[][] A = new int[9][9];
        int y = in.nextInt() - 1, x = in.nextInt() - 1;
        String s = in.next();
        for (int i = 0; i < 9; i++) {
            String t = in.next();
            for (int j = 0; j < 9; j++) A[i][j] = t.charAt(j) - '0';
        }

        int dx = 0, dy = 0;
        if (s.contains("D")) dx = 1;
        if (s.contains("U")) dx = -1;
        if (s.contains("R")) dy = 1;
        if (s.contains("L")) dy = -1;
        String res = "";
        for (int i = 0; i < 4; i++) {
            int nx = x, ny = y;
            if (nx < 0) nx = -nx;
            if (ny < 0) ny = -ny;
            if (nx >= 9) nx = 16 - nx;
            if (ny >= 9) ny = 16 - ny;
            res += A[nx][ny];
            x += dx;
            y += dy;
        }
        out.println(res);
    }
}
