package on2012_6_4.giftrift;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;

public class GiftRift {
    int h,w;
    int[][] is;
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        h = in.nextInt(); w = in.nextInt();
        is = new int[h][w];
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++)
            is[i][j] = in.nextInt();
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++){
            if(good(i,j)){
                out.println(is[i][j]);return;
            }
        }
        out.println("GUESS");
    }

    private boolean good(int i, int j) {
        for (int k = 0; k < h; k++){
            if(is[k][j] > is[i][j])return false;
        }
        for (int k = 0; k < w; k++){
            if(is[i][k] < is[i][j])return false;
        }
        return true;
    }
}
