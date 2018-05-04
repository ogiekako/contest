package on2018_05.on2018_05_03_2018_TopCoder_Open_Algorithm.LineOff;



import java.util.Arrays;

public class LineOff {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int movesToDo(String points) {
        for (int i = 0; i < points.length() - 1; i++) {
            if (points.charAt(i) == points.charAt(i + 1)) {
                return 1 + movesToDo(points.substring(0, i) + points.substring(i + 2));
            }
        }
        return 0;
    }
}
