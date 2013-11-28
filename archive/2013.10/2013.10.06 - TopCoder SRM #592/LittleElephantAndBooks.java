package src;

import java.util.Arrays;
public class LittleElephantAndBooks {
    public int getNumber(int[] pages, int number) {
        Arrays.sort(pages);
        int res = 0;
        for(int i=0;i<number+1;i++){
            res += pages[i];
        }
        res -= pages[number - 1];
        return res;

    }
}
