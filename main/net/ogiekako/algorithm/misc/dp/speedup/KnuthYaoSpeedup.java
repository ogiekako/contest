package net.ogiekako.algorithm.misc.dp.speedup;
/**
 * 問題例:
 * 刺身: http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=2415 (AC)
 * An old stone game: http://poj.org/problem?id=1738 (さらに高速化しないと通らない Hu-Tucker Algorithm)
 * Tree Construction: http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=2488 (AC)
 *
 * 参考サイト:
 * http://topcoder.g.hatena.ne.jp/spaghetti_source/20120915/1347668163
 * http://www.kupc.jp/2012/editorial/J.pdf (刺身)
 * http://topcoder.g.hatena.ne.jp/iwiwi/20120701/1341149838
 * http://codeforces.com/blog/entry/8219
 */

/**
 * 例: 最適平衡二分探索木問題をO(n^2)で解く．
 * Monge性を利用するDP．
 * fがMonge :⇔，i≦j≦k≦lに対し，f(i,l)+f(j,k)≦f(i,k)+f(j,l).
 * (min-max束に対し劣モジュラ f(i,l)+f(j,k) ≧ f(min(i,j),min(k,l)) + f(max(i,j),max(k,l)) = f(i,k)+f(j,l) (i≦j≦k≦l). )
 * <p/>
 * Lemma 1. (f*g)(i,j) := min(f(i,s)+g(s+1,j)).とし，K(i,j)をminを達成する最大のsとする．このとき，
 * 1. f*gはMonge.
 * 2. Kは単調増加. K(i,j)≦K(i+1,j)≦K(i+1,j+1).
 * <p/>
 * proof:
 * 1はLemma 2の証明と同様．2を示す．
 * s = K(i,j), t = K(i+1,j)とすると，
 * f*g(i,j) + f*g(i+1,j) = f(i,s)+f(i+1,t) + g(s+1,j)+g(t+1,j)
 * Monge性より，
 * ≧ f(i,min(s,t))+f(i+1,max(s,t)) + g(min(s,t)+1,j)+g(max(s,t)+1,j).
 * これは f*g(i,j), f*g(i+1,j)で最小化される項の形をしている．左辺が最小であることより，右辺も最小を達成するはずで，
 * s,tは最小を達成する中で最大のものをとったので，
 * min(s,t)≦s (max(s,t)≦t).よって，s≦tである．
 * s = K(i,j), t=K(i,j+1)とした場合も同様で，一応示すと，
 * f*g(i,j) + f*g(i,j+1) = f(i,s)+f(i,t) + g(s+1,j)+g(t+1,j+1)
 * ≧ f(i,min(s,t))+f(i,max(s,t)) + g(min(s,t)+1,j)+g(max(s,t)+1,j+1).
 * これは f*g(i,j), f*g(i,j+1)で最小化される項の形であり，
 * min(s,t)≦s, max(s,t)≦t. よって，s≦t である．■
 * <p/>
 * Lemma 2. X(i,j) := min_{i≦s＜j}(X(i,s)+X(s+1,j)) + w(i,j)
 * は，wがMongeかつ単調 ( w(i,l) ≦ w(j,k) ([j,k]⊆[i,l]) )のときMonge.
 * <p/>
 * proof:
 * i≦j≦k≦l とし，X(i,l)+X(j,k)≧X(i,k)+X(j,l)を示したい．
 * |l-i|の大きさに関する帰納法で示す．|l-i|がそれより小さいところでは成り立っているとする．
 * X(i,l)=X(i,s)+X(s+1,l)+w(i,l)
 * X(j,k)=X(j,t)+X(t+1,k)+w(j,k) として，足し合わせ，列ごとに(Xとwの)Monge性を使うと(サイズが小さいので可)，
 * X(i,l)+X(j,k)≧X(i,min(s,t))+X(j,max(s,t))+X(min(s,t)+1,k)+X(max(s,t)+1,l)+w(i,k)+w(j,l)．
 * 右辺は，X(i,k), X(j,l)において最小化する項となっており，i≦min(s,t)＜k, j≦max(s,t)＜l は明らかに満たす．
 * よって，minが走ると更に小さくなり，
 * ≧X(i,k)+X(j,l).
 * よって，XがMongeであることが示された．■
 * <p/>
 * Lemma 1の1のproofも一応やっておくと，
 * i≦j≦k≦l とし，f*g(i,l)+f*g(j,k)≧f*g(i,k)+f*g(j,l)を示したい．
 * |l-i|の大きさに関する帰納法で示す．|l-i|がそれより小さいところでは成り立っているとする．
 * f*g(i,l)=f(i,s)+g(s+1,l)
 * f*g(j,k)=f(j,t)+g(t+1,k) として，足し合わせ，列ごとに(fとgの)Monge性を使うと，
 * f*g(i,l)+f*g(j,k)≧f(i,min(s,t))+f(j,max(s,t))+g(min(s,t)+1,k)+g(max(s,t)+1,l)．
 * 右辺は，f*g(i,k), f*g(j,l)において最小化する項となっており，よってminが走ると更に小さくなり
 * ≧ f*g(i,k)+f*g(j,l).
 * よってf*gがMongeであることが示された．■
 * <p/>
 * Lemma 3.
 * X(i,j)=min_{i≦s＜j}(X(i,s)+X(s+1,j))+w(i,j) のDPは，Lemma 2の条件 (wはMongeかつ単調)を満たす時，
 * O(n^2)で解ける．
 * <p/>
 * proof: Lemma 2より，XはMonge. Lemma 1より，X(i,j), X(i+1,j+1)が計算されている時，
 * X(i+1,j)を計算するには，K(i,j)≦K(i,j+1)≦K(i+1,j+1)より，K(i,j+1)の走る範囲を限定できる．
 * K(i,i)=iの対角線から始めて，右上方向に計算を進めていく．各対角線で合わせて n しか走る範囲がないので，O(n^2)の
 * アルゴリズムとなる．
 */
public class KnuthYaoSpeedup {
    // AOJ2415
    public static long optimalBinarySearchTree(long[] w) {
        int n = w.length;
        long[][] X = new long[n][n];
        int[][] K = new int[n][n];
        long[] sumW = new long[n + 1];
        for (int i = 0; i < n; i++) sumW[i + 1] = sumW[i] + w[i];
        for (int i = 0; i < n; i++) K[i][i] = i;
        for (int i = 0; i < n; i++) X[i][i] = 0;
        for (int d = 1; d < n; d++) {
            for (int i = 0, j; (j = i + d) < n; i++) {
                // K[i][j-1] ≦ K[i][j] ≦ K[i+1][j]
                X[i][j] = Long.MAX_VALUE;
                for (int s = K[i][j - 1]; s <= Math.min(j - 1, K[i + 1][j]); s++) {
                    long value = X[i][s] + X[s + 1][j] + (sumW[j + 1] - sumW[i]);
                    if (X[i][j] >= value) {
                        X[i][j] = value;
                        K[i][j] = s;
                    }
                }
            }
        }
        return X[0][n - 1];
    }
    /*
    連鎖集合積問題
    集合 S1, ..., Sn の直積 S1×...×Sn を計算する．
    S1×S2 を行うには |S1|×|S2| 時間かかる（全要素対の数），
    各集合のサイズが与えられたときの最適コストを求めよ

    X(i,j) = min(X(i,s)+X(s+1,j)+w(i,j))
    w(i,j) = Si*...*Sj.
    とする．wがMongeであることを示せば良い．
    Si≧1とすると，(1つでもゼロがあったら答えは明らかに0)．
    w(i,l)+w(j,k)≧w(i,k)+w(j,k) を示せば良い．
    A=w(i,j),B=w(j+1,k),C=w(k+1,l)として，
    ABC+B≧AB+BC を示せば良い．これは B(A-1)(C-1)≧0 と同値なので正しい．よってwはMongeであり，
    Knuth-Yao speedupが適用出来る．
     */
    // Not verified.
    public static long setChainMultiplication(long[] S) {
        for (long l : S) if (l == 0) return 0;
        int n = S.length;
        long[][] X = new long[n][n];
        int[][] K = new int[n][n];
        long[] prodS = new long[n + 1];
        prodS[0] = 1;
        for (int i = 0; i < n; i++) prodS[i + 1] = prodS[i] * S[i];
        for (int i = 0; i < n; i++) K[i][i] = i;
        for (int i = 0; i < n; i++) X[i][i] = 0;
        for (int d = 1; d < n; d++) {
            for (int i = 0, j; (j = i + d) < n; i++) {
                // K[i][j-1] ≦ K[i][j] ≦ K[i+1][j]
                X[i][j] = Long.MAX_VALUE;
                for (int s = K[i][j - 1]; s <= Math.min(j - 1, K[i + 1][j]); s++) {
                    long value = X[i][s] + X[s + 1][j] + (prodS[j + 1] / prodS[i]);
                    if (X[i][j] >= value) {
                        X[i][j] = value;
                        K[i][j] = s;
                    }
                }
            }
        }
        return X[0][n - 1];
    }
}
