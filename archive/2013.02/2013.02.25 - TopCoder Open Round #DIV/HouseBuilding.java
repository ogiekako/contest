package tmp;

public class HouseBuilding {
    public int getMinimum(String[] area) {
        int res = Integer.MAX_VALUE;
        for (int min = 0; min < 10; min++) {
            int val = 0;
            for (String s : area)
                for (char c : s.toCharArray()) {
                    int d = c - '0';
                    if (d <= min) val += min - d;
                    else val += d - min - 1;
                }
            res = Math.min(res, val);
        }
        return res;
    }
}
