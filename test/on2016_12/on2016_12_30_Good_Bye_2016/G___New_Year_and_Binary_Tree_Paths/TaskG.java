package on2016_12.on2016_12_30_Good_Bye_2016.G___New_Year_and_Binary_Tree_Paths;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TaskG {

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    long INF= (long) 1e17;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long s = in.nextLong();
        out.println(solve(s));
    }
    public long solve(long s){
        long res = 0;
        for (int l = 0; l <= 55; l++)
            for (int r = 0; r <= 55; r++) {
                long L=0,R=INF;
                while(R-L>1){
//                    debug(L, R);
                    long min=0;
                    long max=0;
                    long m = (R+L)/2;

                    min += m;
                    long v=m*2;
                    for(int i=0;i<l;i++){
                        min = Math.min(min + v, INF);
                        v = Math.min(v * 2, INF);
                    }
                    v=m*2+1;
                    for(int i=0;i<r;i++){
                        min = Math.min(min + v, INF);
                        v = Math.min(v * 2, INF);
                    }

                    max += m;
                    v=m*2;
                    for(int i=0;i<l;i++){
                        max = Math.min(max + v, INF);
                        v = Math.min(v * 2 + 1, INF);
                    }
                    v=m*2+1;
                    for(int i=0;i<r;i++){
                        max = Math.min(max + v, INF);
                        v = Math.min(v * 2 + 1, INF);
                    }
                    if (s < min) {
                        R = m;
                    } else if (s > max) {
                        L = m;
                    } else {
                        res += solve(s - min,l,r);
                        break;
                    }
                }
            }
        return res;
    }

    Map<Long, Long> cur = new HashMap<>();
    Map<Long, Long> nxt = new HashMap<>();
    private long solve(long s, int l, int r) {
//        debug(l,r,s);
        l = Math.max(l-1, 0);
        r = Math.max(r-1, 0);
        long[] cand = new long[l + r];
        for(int i=0;i<l;i++)cand[i] = (1L << i+1) - 1;
        for(int i=0;i<r;i++)cand[l + i] = (1L << i+1) - 1;
        Arrays.sort(cand);

        long rest = 0;
        for(int i=0;i<cand.length;i++)rest += cand[i];
        cur.clear();
        cur.put(0L, 1L);
        for(int i=cand.length-1;i>=0;i--) {
            nxt.clear();
            long c = cand[i];
            rest -= c;
            for(Map.Entry<Long, Long> e : cur.entrySet()) {
                long val = e.getKey();
                if (val + c <= s) {
                    if(!nxt.containsKey(val+c)){
                        nxt.put(val+c, e.getValue());
                    } else{
                        nxt.put(val+c, nxt.get(val+c) + e.getValue());
                    }
                }
                if (val + rest >= s) {
                    if(!nxt.containsKey(val)){
                        nxt.put(val, e.getValue());
                    } else{
                        nxt.put(val, nxt.get(val) + e.getValue());
                    }
                }
            }
            Map<Long, Long> tmp=cur;cur=nxt;nxt=tmp;
        }
        return cur.containsKey(s) ? cur.get(s) : 0;
    }

    public static int weight(int i, int j) {
        int ii = i, jj = j;
        int v = 0;
        for (int k = 10; k >= 0; k--) {
            if (ii == jj) {
                v += ii;
                break;
            }
            if ((1 << k) <= ii) {
                v += ii;
                ii >>= 1;
            }
            if ((1 << k) <= jj) {
                v += jj;
                jj >>= 1;
            }
        }
        return v;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            debug(i, new TaskG().solve(i));
        }
//        System.exit(0);
        for (int l = 1; l <= 5; l++)
            for (int r = 1; r <= 5; r++) {
                int from=Integer.MAX_VALUE,to=0;
                int[] cnt = new int[10000];
                for(int i=1<<l;i<3*(1<<l-1);i++) {
                    for(int j=3*(1<<r-1);j<1<<r+1;j++){
                        int w=weight(i,j);
                        cnt[w]++;
                        from= Math.min(from,w);
                        to=Math.max(to,w);
                    }
                }
                debug(l,r);
                for(int i=from;i<=to;i++){
                    // debug(i +" -> " + cnt[i]);

                    int add = i - (1<<l+1) + 1 - 3 * ((1<<r) - 1);
                    debug(add +" -> " + cnt[i], Integer.toBinaryString(add));
                }
            }


        int[] res = new int[1000];
//
//        int[][][] cnt = new int[10][10][1000];
//
        for(int i=1;i<=1000;i++)for(int j=1;j<=i;j++){
            int ii=i,jj=j;
            int v=0;
            int l = 0, r = 0;
            for(int k=10;k>=0;k--) {
                if(ii==jj){
                    v+=ii;
                    int tmp = i;
                    while(tmp > ii) {
                        if(tmp%2 == 1 && tmp/2 == ii) {
                            int tmp2=l;l=r;r=tmp2;
                        }
                        tmp /= 2;
                    }
                    break;
                }
                if ((1<<k) <= ii) {
                    v+=ii;
                    ii >>= 1;
                    l++;
                }
                if ((1<<k) <= jj) {
                    v+=jj;
                    jj >>= 1;
                    r++;
                }
            }
            if(v<100) {
                res[v]++;
                if(v==17) debug(i,j);

//                if(ii==1)
//                    cnt[l][r][v]++;
            }
        }
        for (int i = 0; i < res.length; i++) {
            debug(i, Integer.toBinaryString(i), res[i], Integer.toBinaryString(res[i]));
        }
//        for(int l=2;l<8;l++)for(int r=2;r<8;r++){
//            int from = 0, to = 0;
//            for(int i=0;i<1000;i++){
//                if(from==0 && cnt[l][r][i]>0)from=i;
//                if(cnt[l][r][i]>0)to=i;
//            }
//            debug("l,r", l,r);
//            for(int i=from;i<=to;i++){
//                debug(i - (1<<l+1) - (1<<r+1) - 3, "->", cnt[l][r][i]);
//            }
//        }
    }
}
/*
/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/bin/java -Xmx1G -Didea.launcher.port=7537 "-Didea.launcher.bin.path=/Applications/IntelliJ IDEA 14 CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/lib/tools.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Users/oka/src/github.com/ogiekako/contest/out/production/contest:/Users/oka/Library/Application Support/IdeaIC14/chelper/lib/chelper.jar:/Users/oka/src/github.com/ogiekako/contest/deps/hamcrest-all-1.3.jar:/Users/oka/src/github.com/ogiekako/contest/deps/junit-4.12.jar:/Applications/IntelliJ IDEA 14 CE.app/Contents/lib/idea_rt.jar" com.intellij.rt.execution.application.AppMain src.TaskG
[1, 1]
[2, 1]
[3, 2]
[4, 2]
[5, 1]
[6, 3]
[7, 3]
[8, 2]
[9, 2]
[10, 4]
[11, 4]
[12, 3]
[13, 3]
[14, 2]
[15, 4]
[16, 5]
[17, 4]
[18, 6]
[19, 5]
[20, 2]
[21, 6]
[22, 6]
[23, 2]
[24, 5]
[25, 7]
[26, 4]
[27, 4]
[28, 8]
[29, 7]
[30, 7]
[31, 9]
[32, 5]
[33, 4]
[34, 5]
[35, 5]
[36, 6]
[37, 7]
[38, 8]
[39, 8]
[40, 9]
[41, 10]
[42, 9]
[43, 8]
[44, 7]
[45, 8]
[46, 9]
[47, 5]
[48, 8]
[49, 11]
[50, 7]
[51, 7]
[52, 10]
[53, 11]
[54, 6]
[55, 10]
[56, 16]
[57, 9]
[58, 6]
[59, 8]
[60, 10]
[61, 9]
[62, 7]
[63, 14]
[64, 13]
[65, 7]
[66, 14]
[67, 15]
[68, 10]
[69, 13]
[70, 17]
[71, 14]
[72, 10]
[73, 13]
[74, 11]
[75, 9]
[76, 10]
[77, 9]
[78, 11]
[79, 9]
[80, 10]
[81, 14]
[82, 12]
[83, 10]
[84, 15]
[85, 15]
[86, 11]
[87, 18]
[88, 21]
[89, 13]
[90, 14]
[91, 21]
[92, 15]
[93, 12]
[94, 18]
[95, 18]
[96, 14]
[97, 15]
[98, 16]
[99, 11]
[100, 15]
 */
