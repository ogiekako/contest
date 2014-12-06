package src;

public class AliceGame {
    public long findMinimumValue(long x, long y) {
        long sum = x + y;
        long n;
        for (n=0;;n++){
            if(n*n>=sum)break;
        }
        if(n*n!=sum)return -1;
        long res=0;
        for(;n>=1;n--){
            long t=n*2-1;
            if(t<=x && x-t!=2){
                x-=t;
                res++;
            }
        }
        return x==0?res:-1;
    }
}
