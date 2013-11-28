package tmp;

import java.util.Arrays;

public class CucumberMarket {
    public String check(int[] price, int budget, int k) {
        Arrays.sort(price);
        int sum = 0;
        for (int i = 0; i < k; i++) sum += price[price.length - 1 - i];
        return sum <= budget ? "YES" : "NO";
    }
}
