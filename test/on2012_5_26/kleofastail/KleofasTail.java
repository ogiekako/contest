package on2012_5_26.kleofastail;



// Paste me into the FileEdit configuration dialog

public class KleofasTail {
   public long countGoodSequences(long K, long A, long B) {
       if(K==0)return B-A+1;
		long L = K | 1L;
       long res = 0;
        while(L>=0){
            long from = Math.max(A,K);
            long to = Math.min(B,L);
            res += Math.max(0,to-from+1);
           K<<=1;
            L=(L<<1)|1;
        }
       return res;
   }


}

