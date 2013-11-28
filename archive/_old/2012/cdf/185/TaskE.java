package tmp;

import net.ogiekako.algorithm.geometry.lattice.Point;
import net.ogiekako.algorithm.geometry.lattice.QuadTree;
import net.ogiekako.algorithm.geometry.lattice.Rectangle;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

// 通ってない MLE.
public class TaskE {
    int MX = 210000000;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        Point[] person = new Point[n];
        Point[] subway = new Point[k];
        for (int i = 0; i < n; i++) person[i] = new Point(in.nextInt(), in.nextInt());
        for (int i = 0; i < k; i++) subway[i] = new Point(in.nextInt(), in.nextInt());
        int res = solve(person, subway);
        out.println(res);
    }
    public int solve(Point[] _person, Point[] subway) {
        Person[] person = new Person[_person.length];
        for (int i = 0; i < person.length; i++) person[i] = new Person(convert(_person[i]));
        for (int i = 0; i < subway.length; i++) subway[i] = convert(subway[i]);
        QuadTree root = new QuadTree(new Point(-MX, -MX), new Point(MX, MX));
        for (Point p : subway) root.add(p);

        for (Person p : person) {
            p.minDist = QuadTree.calcDist(new Rectangle(p, p), root, MX);
        }
        Arrays.sort(person, new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                return -(o1.minDist - o2.minDist);
            }
        });

        int left = -1, right = person[0].minDist;
        do {
            int mid = (left + right) / 2;
            Point diff = new Point(mid, mid);
            Rectangle region = new Rectangle(new Point(-MX, -MX), new Point(MX, MX));
            for (Person p : person) {
                int distToNearest = QuadTree.calcDist(region, root, MX);
                if (p.minDist + distToNearest <= mid) {
                    break;
                }
                Rectangle myRegion = new Rectangle(p.minus(diff), p.plus(diff));
                region = region.intersection(myRegion);
                if (region == null) break;
            }
            if (region != null) {
                right = mid;
            } else {
                left = mid;
            }
        } while (right - left > 1);
        return right;
    }

    class Person extends Point {
        int minDist;

        Person(Point p) {
            super(p.x, p.y);
        }
    }

    private Point convert(Point p) {
        int nx = p.x - p.y;
        int ny = p.x + p.y;
        return new Point(nx, ny);
    }

}