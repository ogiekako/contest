package on2014_10.on2014_10_24_Single_Round_Match_637.GreaterGame;


import java.util.HashSet;
import java.util.Set;

public class GreaterGame {
    public double calc(int[] hand, int[] sothe) {
        Set<Integer> rest = new HashSet<Integer>();
        int n = hand.length;
        for (int i = 1; i <= n * 2; i++) rest.add(i);
        for (int h : hand) rest.remove(h);
        for (int s : sothe) if (s > 0) rest.remove(s);
        double res = 0;
        for (int j = 0; j < sothe.length; j++)
            if (sothe[j] >= 0) {
                int p = -1;
                for (int i = 0; i < hand.length; i++)
                    if (hand[i] > 0) {
                        if (hand[i] > sothe[j] && (p == -1 || hand[p] > hand[i])) {
                            p = i;
                        }
                    }
                if (p >= 0) {
                    hand[p] = -1;
                    sothe[j] = -2;
                    res++;
                } else {
                    for (int i = 0; i < hand.length; i++)
                        if (hand[i] > 0) {
                            if (p == -1 || hand[p] > hand[i]) {
                                p = i;
                            }
                        }
                    hand[p] = -1;
                    sothe[j] = -2;
                }
            }
        for (int s : rest) {
            int c = 0;
            for (int h : hand) if (h > 0) if (h > s) c++;
            res += (double) c / rest.size();
        }
        return res;
    }
}
