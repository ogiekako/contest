package src;

public class OneDimensionalRobot {
    public long theSum(String[] commands1, String[] commands2, int minA, int maxA, int minB, int maxB) {
        StringBuilder sb = new StringBuilder();
        for (String s : commands1) sb.append(s);
        for (String s : commands2) sb.append(s);
        String C = sb.toString();
        int minX = 0, maxX = 0;
        int x = 0;
        for (char c : C.toCharArray()) {
            x += c == 'R' ? 1 : -1;
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
        }

        long res = 0;
        for (int w = 0; w <= 10100; w++) {
            x = 0;
            int a = Math.max(-maxA, -w);// b should not be negative.
            int b = a + w;
            for (char c : C.toCharArray()) {
                x += c == 'R' ? 1 : -1;
                if (x < a) x = a;
                if (b < x) x = b;
            }
            for (; b <= maxB && a <= -minA; a++, b++) {
                if (minB <= b) {
                    res += x;
                }
                if (minX <= a || b + 1 <= maxX) {
                    x++;
                }
            }
        }

        return res;
    }
}
