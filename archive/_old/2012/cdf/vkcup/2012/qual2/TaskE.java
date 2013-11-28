package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.interfaces.Classifiable;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskE {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        Cube[] cubes = new Cube[n];
        for (int i = 0; i < n; i++) {
            cubes[i] = new Cube(in.nextInt(), in.nextInt(), i);
        }
        Cube[][] colorToCubes = ArrayUtils.classify(cubes);
        Entry[] highest = new Entry[n];
        Entry[] secondHighest = new Entry[n];
        Arrays.fill(highest, new Entry(-1, Long.MIN_VALUE / 10, 0));
        Arrays.fill(secondHighest, new Entry(-1, Long.MIN_VALUE / 10, 0));
        for (int i = 0; i < colorToCubes.length; i++) {
            Arrays.sort(colorToCubes[i], new Comparator<Cube>() {
                public int compare(Cube o1, Cube o2) {
                    return -(o1.height - o2.height);
                }
            });
            long height = 0;
            for (int j = 0; j < colorToCubes[i].length; j++) {
                height += colorToCubes[i][j].height;
                if (highest[j].height < height) {
                    secondHighest[j] = highest[j];
                    highest[j] = new Entry(i, height, j + 1);
                } else if (secondHighest[j].height < height) {
                    secondHighest[j] = new Entry(i, height, j + 1);
                }
            }
        }
        long maxHeight = 0;
        for (int i = 0; i < n; i++) {
            // i,i
            maxHeight = Math.max(maxHeight, highest[i].height + secondHighest[i].height);
            // i,i+1
            if (i < n - 1) {
                if (highest[i].colorIndex != highest[i + 1].colorIndex) {
                    maxHeight = Math.max(maxHeight, highest[i].height + highest[i + 1].height);
                }
                maxHeight = Math.max(maxHeight, highest[i].height + secondHighest[i + 1].height);
                maxHeight = Math.max(maxHeight, secondHighest[i].height + highest[i + 1].height);
            }
        }
        out.println(maxHeight);
        Entry bottom = null;
        Entry upper = null;
        for (int i = 0; i < n; i++) {
            // i,i
            if (maxHeight == highest[i].height + secondHighest[i].height) {
                bottom = highest[i];
                upper = secondHighest[i];
                break;
            }
            // i,i+1
            if (i < n - 1) {
                if (highest[i].colorIndex != highest[i + 1].colorIndex) {
                    if (maxHeight == highest[i].height + highest[i + 1].height) {
                        upper = highest[i];
                        bottom = highest[i + 1];
                        break;
                    }
                }
                if (maxHeight == highest[i].height + secondHighest[i + 1].height) {
                    upper = highest[i];
                    bottom = secondHighest[i + 1];
                    break;
                }
                if (maxHeight == secondHighest[i].height + highest[i + 1].height) {
                    upper = secondHighest[i];
                    bottom = highest[i + 1];
                    break;
                }
            }
        }
        out.println(bottom.cubeCount + upper.cubeCount);
        for (int i = 0; i < bottom.cubeCount + upper.cubeCount; i++) {
            if (i > 0) out.print(" ");
            out.print((i & 1) == 0 ? colorToCubes[bottom.colorIndex][i / 2].index + 1 :
                    colorToCubes[upper.colorIndex][i / 2].index + 1);
        }
        out.println();
    }

    class Entry {
        int colorIndex;
        long height;
        int cubeCount;

        Entry(int colorIndex, long height, int cubeCount) {
            this.colorIndex = colorIndex;
            this.height = height;
            this.cubeCount = cubeCount;
        }
    }

    class Cube implements Classifiable {
        int color;
        int height;
        int index;

        Cube(int color, int height, int index) {
            this.color = color;
            this.height = height;
            this.index = index;
        }

        public int getKey() {
            return color;
        }
    }
}
