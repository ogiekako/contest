package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;


public class BinaryTreeCopyAndPaste {

    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (; ; ) {
            String input;
            try {
                input = in.next();
            } catch (NoSuchElementException ignore) {
                return;
            }
            if (input.equals("#")) return;
            int result = new BinaryTreeCopyAndPaste().solve(input);
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
        debug("parsing...");
        Node root = parse();
        debug("parsed.");
        debug("#node = " + root.size);
        if (p != cs.length) throw new AssertionError();
//        if (!root.toString().equals(input)) throw new AssertionError();

        curRoot = deepest;
        ArrayList<Integer> able = new ArrayList<Integer>();
        debug("checking...");
        while (curRoot != null) {
            if (root.size % curRoot.size == 0) {
                debug("checking a subtree of size " + curRoot.size);
                if (valid_dfs(root, curRoot)) {
                    able.add(curRoot.size);
                }
            }
            curRoot = curRoot.parent;
        }
        debug("checked.");
        int n = able.size();
        debug("n = " + n);
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (able.get(j) % able.get(i) == 0) dist[i][j] = able.get(j) / able.get(i) - 1;
                else dist[i][j] = INF;
            }
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        return dist[0][n - 1];
    }

    private boolean valid(Node root, Node cur) {
        Stack<Node> params = new Stack<Node>();
        params.push(cur); params.push(root);
        while (!params.empty()) {
            root = params.pop();
            cur = params.pop();
            if (root.left == null) {
                if (cur.left != null) return false;
            } else {
                if (cur.left == null) {
                    params.push(curRoot); params.push(root.left);
                } else {
                    params.push(cur.left); params.push(root.left);
                }
            }

            if (root.right == null) {
                if (cur.right != null) return false;
            } else {
                if (cur.right == null) {
                    params.push(curRoot); params.push(root.right);
                } else {
                    params.push(cur.right); params.push(root.right);
                }
            }
        }
        return true;
    }
    private boolean valid_dfs(Node root, Node cur) {
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
        Stack<Action> actions = new Stack<Action>();
        Stack<Node> results = new Stack<Node>();
        actions.push(Action.ENTER);
        while (!actions.isEmpty()) switch (actions.pop()) {
            case ENTER:
                if (cs[p] != '(') throw new AssertionError();
                p++;
                if (cs[p] == ')') {
                    p++;
                    results.push(null);
                } else {
                    depth++;
                    actions.push(Action.EXIT);
                    actions.push(Action.ENTER);
                    actions.push(Action.ENTER);
                }
                break;
            case EXIT:
                Node right = results.pop();
                Node left = results.pop();

                if (cs[p] != ')') throw new AssertionError();
                p++;
                depth--;
                Node res = new Node(left, right);
                results.push(res);
                res.depth = depth;
                if (left != null) left.parent = res;
                if (right != null) right.parent = res;
                if (deepest == null || deepest.depth < depth) deepest = res;
                res.size = 1;
                if (left != null) res.size += left.size;
                if (right != null) res.size += right.size;
                break;
        }
        if (results.size() != 1) throw new AssertionError();
        return results.pop();
    }

    static enum Action {
        ENTER,
        EXIT
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
