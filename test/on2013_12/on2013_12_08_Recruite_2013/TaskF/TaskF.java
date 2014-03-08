package on2013_12.on2013_12_08_Recruite_2013.TaskF;



import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.List;

public class TaskF {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            UnionFind uf = new UnionFind(26);
            int n = in.nextInt();
            for (int i = 0; i < n; i++) {
                String s = in.next();
                for (char c : s.toCharArray())
                    for (char d : s.toCharArray()) {
                        uf.union(c - 'a', d - 'a');
                    }
            }
            List<String> is = new ArrayList<>();
            for(int i=0;i<26;i++)if(uf.root(i) == i){
                String s = "";
                for (int j = 0; j < 26; j++) {
                    if(uf.root(j) == i){
                        s += (char)('a' + j);
                    }
                }
                is.add(s);
            }
            String[][][][] can = new String[27][27][27][];
            can[0][0][0] = new String[]{"","",""};
            debug(is);
            for(String s:is){
                debug(s);
                String[][][][] ncan = new String[27][27][27][];
                for (int i = 0; i < 26; i++) {
                    for (int j = 0; j < 26; j++) {
                        for (int k = 0; k < 26; k++) {
                            if(can[i][j][k] != null){
                                debug(can[i][j][k]);
                                ncan[i+s.length()][j][k] = can[i][j][k].clone();
                                ncan[i+s.length()][j][k][0] += s;
                                ncan[i][j+s.length()][k] = can[i][j][k].clone();
                                ncan[i][j+s.length()][k][1] += s;
                                ncan[i][j][k+s.length()] = can[i][j][k].clone();
                                ncan[i][j][k+s.length()][2] += s;
                            }
                        }
                    }
                }
                can = ncan;
            }
            if(can[10][9][7] != null){
                int p=0;
                for(String s:can[10][9][7]){
                    p++;
                    out.printFormat("%s%c",s, p==3 ? '\n':' ');
                }
            }else
            out.println("IMPOSSIBLE");
        }
    }
    static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }
}
