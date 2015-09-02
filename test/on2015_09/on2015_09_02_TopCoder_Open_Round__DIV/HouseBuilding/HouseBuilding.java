package on2015_09.on2015_09_02_TopCoder_Open_Round__DIV.HouseBuilding;



import java.util.Arrays;

public class HouseBuilding {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int getMinimum(String[] area) {
        int res = 1000000;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Math.abs(i - j) > 1) continue;
                int val = 0;
                for (String s : area)
                    for (char c : s.toCharArray()) {
                        val += Math.min(Math.abs(c - '0' - i), Math.abs(c - '0' - j));
                    }
                res = Math.min(res, val);
            }
        }
        return res;
    }
}
