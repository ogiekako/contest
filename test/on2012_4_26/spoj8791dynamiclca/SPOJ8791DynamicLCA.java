package on2012_4_26.spoj8791dynamiclca;



import net.ogiekako.algorithm.dataStructure.tree.LinkCutTree;
import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;

public class SPOJ8791DynamicLCA {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int M = in.nextInt();
        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) nodes[i] = new Node(i);
        for (int i = 0; i < M; i++){
            String s = in.next();
            if(s.charAt(1)=='u'){// cut
                int c = in.nextInt()-1;
                nodes[c].cut();
            }else if(s.charAt(1)=='i'){// link
                int c = in.nextInt()-1, p = in.nextInt()-1;
                nodes[c].link(nodes[p]);
            }else{// lowestCommonAncestor
                int a = in.nextInt()-1, b = in.nextInt()-1;
                Node lca = (Node) nodes[a].lowestCommonAncestor( nodes[b]);
                out.println(lca.id+1);
            }
        }
    }
    class Node extends LinkCutTree{

        private int id;

        public Node(int id) {
            super();
            this.id = id;
        }

        
    }
}
