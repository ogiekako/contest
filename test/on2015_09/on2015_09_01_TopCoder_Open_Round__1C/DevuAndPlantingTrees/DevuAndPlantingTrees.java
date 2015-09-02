package on2015_09.on2015_09_01_TopCoder_Open_Round__1C.DevuAndPlantingTrees;



import java.util.Arrays;

public class DevuAndPlantingTrees {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maximumTreesDevuCanGrow(String[] garden) {
        char[] cs = new char[garden[0].length()];
        Arrays.fill(cs, '.');
        int res = 0;
        for (int i = 0; i < garden[0].length(); i++) {
            if(garden[0].charAt(i) == '*' || garden[1].charAt(i) == '*'){
                res++;
                cs[Math.max(0,i-1)] = cs[i] = cs[Math.min(garden[0].length() - 1, i+1)] = '*';
            }
        }
        String[]ss = new String(cs).split("\\*");
        for(String t:ss){
            res += (t.length()+1) / 2;
        }
        return res;
    }
}
