package net.ogiekako.algorithm.game;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/28
 * Time: 3:13
 * To change this template use File | Settings | File Templates.
 */
public class Stones {
    /**
     * a^k (k>=0) をとれる.
     *最後に石をとった人が負け.
     * a+1 で周期的になる.
     * a^i = a^(i%2) (mod a+1) が成立するので,
     * x - (a+1)j が負けなら,x も負け.
     * x - (a+1)j が勝ちなら,x も勝ち. というのも,x < a+1 のときを考えてやると,負け状態は交互に並んでいるので,x%(a+1)>0 なる負けでない状態からは,1を取ることによって,負け状態にいける.
     * x%(a+1)==0 の時は,a を取ることによって,負け状態にいける.
     * よって,a+1 で剰余をとり,偶数なら勝ち.
     * @param stoneCount
     * @param a
     * @return
     */
    public static boolean winPowerStoneGame(long stoneCount,long a){
        if(stoneCount<=a)return stoneCount%2==0;
        return stoneCount%(a+1)%2==0;
    }
}
