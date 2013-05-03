package xorboarddivtwo;



// Paste me into the FileEdit configuration dialog

public class XorBoardDivTwo {
   public int theMax(String[] board) {
       int res = 0;
       int h = board.length, w = board[0].length();
       for (int i = 0; i < h; i++) for (int j = 0; j < w; j++){
           char[][] cs = new char[h][w];
           for (int k = 0; k < h; k++) for (int l = 0; l < w; l++)cs[k][l] = board[k].charAt(l);
           for (int k = 0; k < h; k++)cs[k][j] ^= 1;
           for (int k = 0; k < w; k++)cs[i][k] ^= 1;
           int cnt = 0;
           for (int k = 0; k < h; k++) for (int l = 0; l < w; l++)if(cs[k][l]=='1')cnt++;
           res = Math.max(res,cnt);
       }
       return res;
   }


}

