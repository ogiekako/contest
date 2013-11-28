package tmp;

import net.ogiekako.algorithm.dataStructure.interval.Interval;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.IntegerUtils;

import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

public class TaskD {
    class Segment implements Comparable<Segment> {
        final int height;
        final int left, right;
        int flow;

        public Segment(int height, int left, int right) {
            this.height = height;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Segment o) {
            int tmp = IntegerUtils.compare(height, o.height);
            return tmp == 0 ? IntegerUtils.compare(left, o.left) : tmp;
        }

        @Override
        public String toString() {
            return "Segment{" +
                    "flow=" + flow +
                    ", height=" + height +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    class Part extends Interval {

        private final Segment segment;

        /**
         * if left > right, right is set as left.
         *
         * @param left  - left (inclusive)
         * @param right - right (exclusive)
         */
        public Part(long left, long right, Segment segment) {
            super(left, right);
            this.segment = segment;
        }

        public int nextFlow(Part other) {
            int overlap = (int) intersection(other).length();
            return Math.min(overlap, segment.flow);
        }
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), t = in.nextInt();
        int[] heights = new int[n], lefts = new int[n], rights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = in.nextInt(); lefts[i] = in.nextInt(); rights[i] = in.nextInt();
        }
        long result = solve(heights, lefts, rights);
        out.println(result);
    }

    private long solve(int[] heights, int[] lefts, int[] rights) {
        int n = heights.length;
        Segment[] segments = new Segment[n + 2];
        for (int i = 0; i < n + 2; i++) {
            segments[i] = i == 0 ? new Segment(0, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1) : i == n + 1 ? new Segment(Integer.MAX_VALUE, Integer.MIN_VALUE + 2, Integer.MAX_VALUE - 2) : new Segment(heights[i - 1], lefts[i - 1], rights[i - 1]);
        }
        Arrays.sort(segments);
        segments[0].flow = Integer.MAX_VALUE;
        TreeSet<Part> set = new TreeSet<Part>();
        set.add(new Part(segments[0].left, segments[0].right, segments[0]));
        for (int i = 1; i < segments.length; i++) {
//            debug(set);
            Segment curSegment = segments[i];
            Part curPart = new Part(curSegment.left, curSegment.right, curSegment);
            Part leftPart = set.lower(new Part(curSegment.left, Integer.MAX_VALUE, curSegment));
            Part rightPart = set.lower(new Part(curSegment.right, curSegment.right, curSegment));
            if (leftPart == null) throw new AssertionError();
            if (rightPart == null) throw new AssertionError();
            NavigableSet<Part> midParts = set.subSet(leftPart, false, rightPart, false);
            curSegment.flow = 0;
            if (leftPart == rightPart) curSegment.flow = Math.max(curSegment.flow, leftPart.nextFlow(curPart));
            if (leftPart.right == leftPart.segment.right)
                curSegment.flow = Math.max(curSegment.flow, leftPart.nextFlow(curPart));
            if (rightPart.left == rightPart.segment.left)
                curSegment.flow = Math.max(curSegment.flow, rightPart.nextFlow(curPart));
            for (Part part : midParts) {
                if (part.left == part.segment.left && part.right == part.segment.right) {
                    long overlap = part.intersection(curPart).length();
                    curSegment.flow = (int) Math.max(curSegment.flow, Math.min(overlap, part.segment.flow));
                }
            }
            set.remove(leftPart);
            set.remove(rightPart);
            set.removeAll(new TreeSet<Part>(midParts));
            Part nLeftPart = new Part(leftPart.left, curPart.left, leftPart.segment);
            Part nRightPart = new Part(curPart.right, rightPart.right, rightPart.segment);
            set.add(curPart);
            if (nLeftPart.length() > 0) set.add(nLeftPart);
            if (nRightPart.length() > 0) set.add(nRightPart);
        }
//        debug(set);
        return segments[n + 1].flow;
    }

//    public static void main(String[] args) {
//        Random rnd = new Random(124089124L);
//        int n = 3;
//        int t = 1000;
//        for (int i = 0; i < 1000000; i++){
//            int[] heights = new int[n];
//            int[] lefts = new int[n], rights = new int[n];
//            for (int j = 0; j < n; j++){
//                heights[j] = rnd.nextInt(t-1) + 1;
//                lefts[j] = rnd.nextInt(10);
//                rights[j] = rnd.nextInt(10);
//                if(lefts[j]==rights[j]){
//                    j--;continue;
//                }
//                if(lefts[j] > rights[j]){int tmp = lefts[j]; lefts[j] = rights[j]; rights[j] = tmp;}
//            }
//            long res = new TaskD().solve(heights,lefts, rights);
//            long exp = solveStupid(heights, lefts, rights);
//            if(res!=exp){
//                debug(heights);
//                debug(lefts);
//                debug(rights);
//                debug(exp, res);
//                throw new AssertionError();
//            }
//        }
//    }
//
//    private static long solveStupid(int[] _heights, int[] _lefts, int[] _rights) {
//        int _n = _heights.length;
//        int n = _n + 2;
//        long[] heights = new long[n], lefts = new long[n], rights = new long[n];
//        for (int i = 0; i < n; i++){
//            heights[i] = i==0 ? 0 : i==n-1 ? Integer.MAX_VALUE : _heights[i-1];
//            lefts[i] = i==0 || i==n-1 ? Integer.MIN_VALUE : _lefts[i-1];
//            rights[i] = i==0 || i==n-1 ? Integer.MAX_VALUE : _rights[i-1];
//        }
//        boolean[][] valid = new boolean[n][n];
//        for (int i = 0; i < n; i++)for (int j = 0; j < n; j++)valid[i][j] = heights[i] < heights[j] && Math.max(lefts[i], lefts[j]) < Math.min(rights[i], rights[j]);
//        long[][] capacity = new long[n][n];// i -> j
//        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++)if(valid[i][j]){
//            capacity[i][j] = Math.min(rights[i], rights[j]) - Math.max(lefts[i], lefts[j]);
//            for (int k = 0; k < n; k++)if(valid[i][k] && valid[k][j])capacity[i][j] = 0;
//        }
//        long[] dp = new long[n];
//        dp[0] = Integer.MAX_VALUE;
//        boolean[] visited = new boolean[n];
//        visited[0] = true;
//        for (int i = 0; i < n-1; i++){
//            int p = -1;
//            for (int j = 0; j < n; j++)if(!visited[j] &&(p==-1 || heights[p] > heights[j]))p = j;
//            visited[p] = true;
//            for (int j = 0; j < n; j++)if(visited[j])dp[p] = Math.max(dp[p], Math.min(dp[j], capacity[j][p]));
//        }
//        return dp[n-1];
//    }
//
//    static void debug(Object...os){
//        System.err.println(Arrays.deepToString(os));
//    }
}
