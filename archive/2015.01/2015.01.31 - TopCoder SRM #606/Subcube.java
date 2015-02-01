package src;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Subcube {
    class P {
        double x, y, z;

        P(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double dist(P p) {
            return sub(p).norm();
        }

        private P sub(P p) {
            return new P(x - p.x, y - p.y, z - p.z);
        }


        double norm() {
            return Math.sqrt(x * x + y * y + z * z);
        }
    }

    P[] unitCube;

    public long getCount(int[] X, int[] Y, int[] Z) {
        unitCube = new P[8];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    unitCube[i*4+j*2+k] = new P(i,j,k);
                }
            }
        }
        int n = X.length;
        P[] ps = new P[n];
        for (int i = 0; i < n; i++) {
            ps[i] = new P(X[i], Y[i], Z[i]);
        }
        int[] cube = new int[8];
        Arrays.fill(cube, -1);recur(0, cube, 0, ps);
        return validHashes.size();
    }

    Set<Long> validHashes = new HashSet<>();
    private long recur(int i, int[] cube, int m, P[] ps) {
        if (i == ps.length) {
            return 0;
        }
        long res = 0;
        res += recur(i + 1, cube, m, ps);
        for (int j=0;j<cube.length;j++)if(cube[j]==-1) {
            cube[j] = i;
            if (valid(cube,ps)) {
                validHashes.add(hash(cube,m+1));
                res += recur(i + 1, cube, m+1, ps);
            }
            cube[j]=-1;
        }
        return res;
    }

    private Long hash(int[] cube, int m) {
        cube = cube.clone();
        Arrays.sort(cube);
        long hash = 0;
        for(int i=0;i<cube.length;i++){
            hash += cube[i] + 1;
            hash *= 60;
        }
        return hash;
    }

    private boolean valid(int[] cube, P[]ps) {
        double mul = 0;
        for(int i=0;i<8;i++)for(int j=0;j<i;j++)if(cube[i]>=0 && cube[j]>= 0){
            double dist = ps[cube[i]].dist(ps[cube[j]]);
            double dist2 = unitCube[i].dist(unitCube[j]);
            double nMul = dist / dist2;
            if (mul != 0 && Math.abs(mul - nMul) > 1e-9)return false;
            mul = dist / dist2;
        }
        return true;
    }
}
