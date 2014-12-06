package on2014_08.on2014_08_31_Single_Round_Match_631.TaroJiroGrid;


public class TaroJiroGrid {
    int w;

    public int getNumber(String[] grid) {
        w = grid[0].length();
        if (ok(grid)) return 0;
        for (int i = 0; i < grid.length; i++) {
            String[] test = grid.clone();
            test[i] = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
            if (ok(test)) return 1;
            test[i] = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
            if (ok(test)) return 1;
        }
        return 2;
    }

    private boolean ok(String[] test) {
        String bad = "";
        String bad2 = "";
        while (bad.length() * 2 <= w) {
            bad += "W";
            bad2 += "B";
        }
        for (int i = 0; i < w; i++) {
            String s = "";
            for (int j = 0; j < test.length; j++) s += test[j].charAt(i);
            if (s.contains(bad)) return false;
            if (s.contains(bad2)) return false;
        }
        return true;
    }
}
