package on2013_10.on2013_10_06_TopCoder_SRM__592.LittleElephantAndBooks;


import java.util.Arrays;
public class LittleElephantAndBooks {
    public int getNumber(int[] pages, int number) {
        Arrays.sort(pages);
        int res = 0;
        for (int i = 0; i < number + 1; i++) {
            res += pages[i];
        }
        res -= pages[number - 1];
        return res;

    }
}
