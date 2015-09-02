package on2015_09.on2015_09_01_TopCoder_Open_Round__DIV.SquareSeries;



import java.util.Arrays;

public class SquareSeries {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String completeIt(String pattern, int lastLength) {
        String[] ends = {"","B","W","BB","BW","WB","WW"};
        String WW="",BW="",WB="",BB="";
        String res = null;
        for (int i = 0; i < 150; i++) {
            for (String s:new String[]{WW,BW,WB,BB}) {
                for(String a:ends)for(String b:ends){
                    String t=a+s+b;
                    String pat = pattern.replaceAll("\\?", t);
                    if (valid(pat, lastLength)) {
                        if (res == null || res.length() > pat.length() || res.length() == pat.length() && res.compareTo(pat) > 0) {
                            res = pat;
                        }
                    }
                }
            }
            WW += "W";
            BB += "B";
            WB += i%2 == 0 ? "W" : "B";
            BW += i%2 == 0 ? "B" : "W";
        }
        return res == null ? "..." : res;
    }

    private boolean valid(String pat, int lastLength) {
        int w=0;
        for (int i = 0; i < pat.length(); i++) {
            w += i == 0 ? 1 : (pat.charAt(i) == pat.charAt(i-1) ? -1 : 1);
            if (w <= 0)return false;
        }
        return w == lastLength;
    }
}
