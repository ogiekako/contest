package src;

public class TrafficCongestion {
    int MOD = (int) (1e9+7);
    public int theMinCars(int treeHeight) {
        long a = 1;
        for(int i=1;i <= treeHeight;i++){
            if(i%2==1)a = (a*2 - 1 + MOD) % MOD;
            else a = (a*2 + 1) % MOD;
        }
        return (int)a;
    }
}
