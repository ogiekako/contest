package on2017_08.on2017_08_06_2017_TopCoder_Open_Algorithm.CoprimeMatrix;



import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.misc.coordinateTransformation.P;

import java.util.Arrays;
import java.util.Random;

public class CoprimeMatrix {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    static String imp = "Impossible";
    static String pos = "Possible";

    public String isPossible(String[] coprime) {
        int n = coprime.length;
        if (coprime[0].charAt(0) == 'Y') {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    char exp = MathUtils.gcd(i + 1, j + 1) > 1 ? 'N' : 'Y';
                    if (coprime[i].charAt(j) != exp) return imp;
                }
            }
            return pos;
        }
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            graph[i][i] = true;
        }
        for (int p = 2; p < 50; p++) {
            if (!MathUtils.isPrime(p)) continue;
            int s = -1;
            for (int i = 0; i + p < n; i++) {
                if (coprime[i].charAt(i + p) == 'N') {
                    s = i;
                    break;
                }
            }
            if (s == -1 && 2 * p <= n) {
                return imp;
            }
            if (s == -1) continue;
            if (s - p >= 0) return imp;
            for (int i = s; i < n; i += p) {
                for (int j = s; j < n; j += p) {
                    graph[i][j] = true;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char exp = graph[i][j] ? 'N' : 'Y';
                if (coprime[i].charAt(j) != exp) return imp;
            }
        }
        return pos;
    }

    public String isPossibleWrong(String[] coprime) {
        int n = coprime.length;
        // If omitted, YY,YN
        if (coprime[0].charAt(0) == 'Y') {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    char exp = MathUtils.gcd(i + 1, j + 1) > 1 ? 'N' : 'Y';
                    if (coprime[i].charAt(j) != exp) return imp;
                }
            }
            return pos;
        }
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            graph[i][i] = true;
        }
        for (int p = 2; p < 50; p++) {
            if (!MathUtils.isPrime(p)) continue;
            int s = -1;
            for (int i = 0; i + p < n; i++) {
                if (coprime[i].charAt(i + p) == 'N') {
                    s = i;
                    break;
                }
            }
            // If omitted,  NYYYY,YNYYN,YYNYY,YYYNY,YNYYN
            if (s == -1 && 2 * p <= n) {
                return imp;
            }
            if (s == -1) continue;
            // If omitted, NYYNY,YNYYY,YYNYN,NYYNY,YYNYN
//            if (s - p >= 0) return imp;
            for (int i = s; i < n; i += p) {
                for (int j = s; j < n; j += p) {
                    graph[i][j] = true;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char exp = graph[i][j] ? 'N' : 'Y';
                if (coprime[i].charAt(j) != exp) return imp;
            }
        }
        return pos;
    }

    public static void main(String[] args) {
        Random rnd = new Random(12098108L);
        for (int iter = 0; iter < 10000; iter++) {
            int n = rnd.nextInt(50) + 1;
            int s = rnd.nextInt(40) + 1;
            String[] in = new String[n];
            for (int i = 0; i < n; i++) {
                in[i] = "";
                for (int j = 0; j < n; j++) {
                    if (MathUtils.gcd(s + i, s + j) == 1) {
                        in[i] += 'Y';
                    } else {
                        in[i] += 'N';
                    }
                }
            }
            String possible = new CoprimeMatrix().isPossible(in);
            if (!pos.equals(possible)) {
                debug(in);
                debug(s, n);
                throw new RuntimeException();
            }
        }
        debug("A");
        int a = 0, b = 0;
        for (int iter = 0; iter < 10000000; iter++) {
            int n = 5;
            String[] in = new String[n];
            char[][] cs = new char[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    cs[i][j] = cs[j][i] = rnd.nextBoolean() ? 'N' : 'Y';
                }
                cs[i][i] = 'N';
            }
            for (int i = 0; i < n; i++) {
                in[i] = new String(cs[i]);
            }
            String possible = new CoprimeMatrix().isPossible(in);
            String exp = solveStupid(in);
            debug(a, b);
            if (!possible.equals(exp)) {
                debug(in);
                throw new RuntimeException();
            }
            if (possible.equals(pos)) a++;
            else b++;
        }
        debug(a, b);
    }
    // NYNYN,YNNYN,NNNYY,YYYNN,NNYNN
    // NYNYN
    // YNNYN,
    // NNNYY,
    // YYYNN,
    // NNYNN

    // NNYYY,NNYNY,YYNNN,YNNNY,YYNYN

    private static String solveStupid(String[] in) {
//        int n = in.length;
//        for (int i = 0; i < n -1 ; i++) {
//            if (in[i].charAt(i+1) == 'N') return imp;
//        }
//        for (int p = 2; p < 50; p++) {
//            if (!MathUtils.isPrime(p)) continue;
//            boolean found = false;
//            for(int r=0;r<p;r++) {
//                boolean ok = true;
//                for(int i=(p-r)%p;i <n;i+=p){
//                    for(int j=(p-r)%p;j<n;j+=p){
//                        if(in[i].charAt(j)=='Y')ok=false;
//                    }
//                }
//                if(ok)found = true;
//            }
//            if(!found) return imp;
//        }
//        return pos;

        int n = in.length;
        char[][] myc = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(myc[i], 'Y');
            myc[i][i] = 'N';
        }
        boolean can = true;
        for (int p = 2; p < 50; p++) {
            if (!MathUtils.isPrime(p)) continue;
            int mod = p - 1;
            for (int j = 0; j + p < n; j++) {
                if (in[j].charAt(j + p) == 'N') {
                    mod = j % p;
                }
            }
            if (mod != p - 1) can = false;
            for (int j = mod; j < n; j += p) {
                for (int k = j + p; k < n; k += p) {
                    myc[j][k] = myc[k][j] = 'N';
                }
            }
        }
        boolean ok = true;
        for (int i = 0; i < n; i++) if (!new String(myc[i]).equals(in[i])) ok = false;
        if (ok) return pos;
        if (can) {
            myc[0][0] = 'Y';
            ok = true;
            for (int i = 0; i < n; i++) if (!new String(myc[i]).equals(in[i])) ok = false;
            if (ok) return pos;
        }
        return imp;
    }
}
