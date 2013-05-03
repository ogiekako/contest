package on2012_8_15.bintreestack_re;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
/*
((LL)((LL)L)) -> 2
(((LL)(LL))(LL)) -> 4
((L(LL))((L(LL))L)) -> 3
 */
public class BinTreeStack_RE {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (; ; ) {
            String input;
            try{
                input = in.next();
            }catch (NoSuchElementException ignore){
                return;
            }
            if (input.equals("#")) return;
            int result = new BinTreeStack_RE().solve(input);
            out.println(result);
        }
    }

    int p;
    char[] cs;
    int depth;
    Node deepest;
    Node curRoot;
    int INF = Integer.MAX_VALUE / 2;

    int solve(String input) {
        cs = input.toCharArray();
        Node root = parse();
        if (p != cs.length) throw new AssertionError();

        curRoot = deepest;
        ArrayList<Integer> able = new ArrayList<Integer>();
        while (curRoot != null) {
            if (root.size % curRoot.size == 0) {
                if (valid(root, curRoot)) {
                    able.add(curRoot.size);
                }
            }
            curRoot = curRoot.parent;
        }
        int n = able.size();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
            if (able.get(j) % able.get(i) == 0) dist[i][j] = able.get(j) / able.get(i) - 1;
            else dist[i][j] = INF;
        }
        for (int k = 0; k < n; k++) for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        return dist[0][n - 1];
    }

    private boolean valid(Node root, Node cur) {
        if (root.left == null) {
            if (cur.left != null) return false;
        } else {
            if (cur.left == null) {
                if (!valid(root.left, curRoot)) return false;
            } else {
                if (!valid(root.left, cur.left)) return false;
            }
        }

        if (root.right == null) {
            if (cur.right != null) return false;
        } else {
            if (cur.right == null) {
                if (!valid(root.right, curRoot)) return false;
            } else {
                if (!valid(root.right, cur.right)) return false;
            }
        }

        return true;
    }

    private Node parse() {
        if (cs[p] != '(') throw new AssertionError();
        p++;
        if (cs[p] == ')') {
            p++;
            return null;
        }
        depth++;
        Node left = parse(), right = parse();
        if (cs[p] != ')') throw new AssertionError();
        p++;
        depth--;

        Node res = new Node(left, right);
        res.depth = depth;
        if (left != null) left.parent = res;
        if (right != null) right.parent = res;
        if (deepest == null || deepest.depth < depth) deepest = res;
        res.size = 1;
        if (left != null) res.size += left.size;
        if (right != null) res.size += right.size;
        return res;
    }

    static class Node {
        Node left, right;
        Node parent;
        int size;
        int depth;

        public Node(Node left, Node right) {
            this.left = left; this.right = right;
        }

        @Override
        public String toString() {
            return String.format("(%s%s)", left == null ? "()" : left, right == null ? "()" : right);
        }
    }
}
