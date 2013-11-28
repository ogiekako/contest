package tmp;

import net.ogiekako.algorithm.geometry.lattice.Point;
import net.ogiekako.algorithm.geometry.lattice.Rectangle;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class SetnjaTask {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        Rectangle[] friends = new Rectangle[n];
        for (int i = 0; i < n; i++) {
            int x = in.nextInt(), y = in.nextInt();
            int p = in.nextInt();
            friends[i] = new Rectangle(
                    new Point(x - p, y - p),
                    new Point(x + p, y + p)
            );
        }
        Rectangle region = friends[0];
        res = 0;
        for (int i = 1; i < n; i++) {
            region = f(region, friends[i]);
        }
        out.println(res);
    }

    private Rectangle f(Rectangle region, Rectangle friend) {
        if (region.intersect(friend)) return region.intersection(friend);
        int lb = 0, ub = 500000;
        do {
            int md = (lb + ub) / 2;
            Rectangle expanded = region.expand(md);
            if (expanded.intersect(friend)) {
                ub = md;
            } else {
                lb = md;
            }
        } while (ub - lb > 1);
        int md = ub;
        Rectangle expanded = region.expand(md);
        res += md;
        return expanded.intersection(friend);
    }

    long res;

}