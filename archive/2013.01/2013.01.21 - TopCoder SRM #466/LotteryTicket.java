package tmp;

public class LotteryTicket {
    public String buy(int price, int b1, int b2, int b3, int b4) {
        int[] b = {b1, b2, b3, b4};
        for (int i = 0; i < 1 << b.length; i++) {
            int p = 0;
            for (int j = 0; j < b.length; j++) if (i << 31 - j < 0) p += b[j];
            if (p == price) return "POSSIBLE";
        }
        return "IMPOSSIBLE";
    }
}
