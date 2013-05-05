package net.ogiekako.algorithm.utils;


import java.util.*;

public class TestUtils {
    private static Random random;

    public static void printRandomUndirectedGraph(int vertexCount, int edgeCount) {
        long maxEdgeCount = (long) vertexCount * (vertexCount - 1) / 2;
        edgeCount = (int) Math.min(edgeCount, maxEdgeCount);
        System.out.println(vertexCount + " " + edgeCount);
        Random rnd = new Random();
        if (edgeCount * 2 < maxEdgeCount) {
            HashSet<Pair<Integer, Integer>> set = new HashSet<Pair<Integer, Integer>>();
            while (edgeCount > 0) {
                int t = rnd.nextInt(vertexCount - 1) + 1;
                int s = rnd.nextInt(t) + 1 + 1;
                Pair<Integer, Integer> edge = new Pair<Integer, Integer>(s, t);
                if (set.contains(edge)) continue;
                set.add(edge);
                edgeCount--;
            }
            for (int i = 0; i < vertexCount; i++)
                for (int j = i + 1; j < vertexCount; j++) {
                    if (set.contains(new Pair<Integer, Integer>(i, j))) {
                        System.out.println(i + " " + j);
                    }
                }
        } else {
            HashSet<Pair<Integer, Integer>> set = new HashSet<Pair<Integer, Integer>>();
            int rest = (int) (maxEdgeCount - edgeCount);
            while (rest > 0) {
                int t = rnd.nextInt(vertexCount - 1) + 1;
                int s = rnd.nextInt(t) + 1 + 1;
                Pair<Integer, Integer> edge = new Pair<Integer, Integer>(s, t);
                if (set.contains(edge)) continue;
                set.add(edge);
                edgeCount--;
            }
            for (int i = 0; i < vertexCount; i++)
                for (int j = i + 1; j < vertexCount; j++) {
                    if (!set.contains(new Pair<Integer, Integer>(i, j))) {
                        System.out.println(i + " " + j);
                    }
                }
        }
    }

    public static void main(String[] args) {
        printRandomUndirectedGraph(14, 100);
    }

    public static String generateRandomLowerCaseString(int len) {
        return generateRandomString(StringUtils.lowercaseLetters, len);
    }

    public static String generateRandomString(String usedLetters, int len) {
        return generateRandomString(usedLetters, len, new Random());
    }

    public static String generateRandomString(String usedLetters, int len, Random rnd) {
        char[] cs = new char[len];
        for (int i = 0; i < len; i++) {
            cs[i] = usedLetters.charAt(rnd.nextInt(usedLetters.length()));
        }
        return new String(cs);
    }

    public static int[][] generateRandomIntArray(int height, int width, int upTo) {
        Random rnd = new Random();
        return generateRandomIntArray(height, width, upTo, rnd);
    }

    public static int[][] generateRandomIntArray(int height, int width, int upTo, Random rnd) {
        int[][] res = new int[height][];
        for (int i = 0; i < height; i++) res[i] = generateRandomIntArray(width, upTo, rnd);
        return res;
    }

    public static int[] generateRandomIntArray(int length, int upTo) {
        return generateRandomIntArray(length, upTo, new Random());
    }

    public static int[] generateRandomIntArray(int length, int upTo, Random rnd) {
        int[] res = new int[length];
        for (int i = 0; i < length; i++) res[i] = rnd.nextInt(upTo);
        return res;
    }

    public static void debug(Object... os) {
        String str = Arrays.deepToString(os);
        System.err.println(str);
    }

    public static long[] generateRandomLongArray(int length, long upTp) {
        return generateRandomLongArray(length, upTp, new Random());
    }

    public static long[] generateRandomLongArray(int length, long upTp, Random random) {
        long[] res = new long[length];
        for (int i = 0; i < length; i++) {
            res[i] = randomLong(upTp, random);
        }
        return res;
    }

    public static long randomLong(long upTo, Random random) {
        double res = random.nextDouble() * upTo;
        return (long) res;
    }

    public static int[] getRandomEmptyEdge(boolean[][] graph, int limitTry, Random rnd) {
        int n = graph.length;
        while (limitTry-- > 0) {
            int a = rnd.nextInt(n), b = rnd.nextInt(n);
            if (a != b && !graph[a][b]) return new int[]{a, b};
        }
        return null;
    }

    public static int[] getRandomExistingEdge(boolean[][] graph, int limitTry, Random rnd) {
        int n = graph.length;
        while (limitTry-- > 0) {
            int a = rnd.nextInt(n), b = rnd.nextInt(n);
            if (a != b && graph[a][b]) return new int[]{a, b};
        }
        return null;
    }

    public static int[] generateRandomDistinctIntArray(int n, int k) {
        return generateRandomDistinctIntArray(n, k, random);
    }

    public static int[] generateRandomDistinctIntArray(int n, int k, Random rnd) {
        List<Integer> list = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) list.add(i);
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            int p = rnd.nextInt(list.size());
            res[i] = list.remove(p);
        }
        return res;
    }

    public static void setRandom(Random random) {
        TestUtils.random = random;
    }

    public static void setRandom(long seed) {
        setRandom(new Random(seed));
    }
}
