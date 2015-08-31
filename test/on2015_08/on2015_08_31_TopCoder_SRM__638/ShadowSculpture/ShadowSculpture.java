package on2015_08.on2015_08_31_TopCoder_SRM__638.ShadowSculpture;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShadowSculpture {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    /**
     * 絶対にあっては行けない部分が存在する。それを除いた部分 S には、あってもよい。
     * S は連結とは限らない。S のある連結成分には、すべてに置いても損はしない。
     */
    String possible = "Possible", impossible = "Impossible";

    public String possible(String[] XY, String[] YZ, String[] ZX) {
        int n = XY.length;
        boolean has = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (XY[i].charAt(j) == 'Y') has = true;
                if (YZ[i].charAt(j) == 'Y') has = true;
                if (ZX[i].charAt(j) == 'Y') has = true;
            }
        }
        if (!has) return possible;
        int[][][] cubes = new int[n][n][n];// x, y, z
        ArrayUtils.fill(cubes, -1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (XY[i].charAt(j) == 'N') cubes[i][j][k] = -2;
                    if (YZ[j].charAt(k) == 'N') cubes[i][j][k] = -2;
                    if (ZX[k].charAt(i) == 'N') cubes[i][j][k] = -2;
                }
            }
        }
        int p = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (cubes[i][j][k] != -1) continue;
                    Queue<Integer> que = new LinkedList<Integer>();
                    que.offer(i);
                    que.offer(j);
                    que.offer(k);
                    cubes[i][j][k] = p;
                    while (!que.isEmpty()) {
                        int x = que.poll(), y = que.poll(), z = que.poll();
                        for (int d = 0; d < 6; d++) {
                            int nx = x + dx[d];
                            int ny = y + dy[d];
                            int nz = z + dz[d];
                            if (0 <= nx && nx < n && 0 <= ny && ny < n && 0 <= nz && nz < n && cubes[nx][ny][nz] == -1) {
                                cubes[nx][ny][nz] = p;
                                que.offer(nx);
                                que.offer(ny);
                                que.offer(nz);
                            }
                        }
                    }
                    char[][] XY2 = new char[n][n];
                    char[][] YZ2 = new char[n][n];
                    char[][] ZX2 = new char[n][n];
                    ArrayUtils.fill(XY2, 'N');
                    ArrayUtils.fill(YZ2, 'N');
                    ArrayUtils.fill(ZX2, 'N');
                    for (int x = 0; x < n; x++) {
                        for (int y = 0; y < n; y++) {
                            for (int z = 0; z < n; z++) {
                                if (cubes[x][y][z] != p) continue;
                                XY2[x][y] = 'Y';
                                YZ2[y][z] = 'Y';
                                ZX2[z][x] = 'Y';
                            }
                        }
                    }
                    boolean ok = true;
                    for (int x = 0; x < n; x++) {
                        if (!new String(XY2[x]).equals(XY[x])) ok = false;
                        if (!new String(YZ2[x]).equals(YZ[x])) ok = false;
                        if (!new String(ZX2[x]).equals(ZX[x])) ok = false;
                    }
                    if (ok) return possible;

                    p++;
                }
            }
        }
        return impossible;
    }

    int[] dx = {1, 0, -1, 0, 0, 0};
    int[] dy = {0, 1, 0, -1, 0, 0};
    int[] dz = {0, 0, 0, 0, -1, 1};
}
