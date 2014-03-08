package src;

public class WinterAndPresents {
    public long getNumber(int[] apple, int[] orange) {
        long res = 0;
        for(int X=1;X<=2e6;X++){
            boolean ok = true;
            long minA = 0;
            long maxA = 0;
            for(int i=0;i<apple.length;i++){
                if(apple[i] + orange[i] < X)ok = false;
                minA += Math.max(0, X - orange[i]);
                maxA += Math.min(X, apple[i]);
            }
            if(ok) res += maxA - minA + 1;
        }
        return res;
    }
}
