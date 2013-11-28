package tmp;

public class TheDevice {
    public int minimumAdditional(String[] plates) {
        int res = 0;
        for (int i = 0; i < plates[0].length(); i++) {
            boolean zero = false, one = false, one2 = false;
            for (int j = 0; j < plates.length; j++) {
                if (plates[j].charAt(i) == '0') zero = true;
                else {
                    if (!one) one = true;
                    else one2 = true;
                }
            }
            int cur = 0;
            if (!zero) cur++;
            if (!one) cur++;
            if (!one2) cur++;
            res = Math.max(res, cur);
        }
        return res;
    }
}
