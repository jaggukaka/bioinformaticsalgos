import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class EulerianPath {

    private EulerianPath() {

    }

    private static final class EulerianPathHolder {
        private static final EulerianPath EulerianPath = new EulerianPath();
    }

    public static EulerianPath getInstance() {
        return EulerianPathHolder.EulerianPath;
    }
    
    public Iterable<Integer> getEulerPathDG(int max, List<int[]> nodes) {
        Digraph G = new Digraph(max + 1);

        Map<Integer, Integer> mp = new HashMap<Integer, Integer>();
        int sentinalO = 0;
        int sentinalT = 0;
        for (int[] edges : nodes) {
            int root = edges[0];

            for (int i = edges.length - 1; i > 0; i--) {
                int edge = edges[i];
                G.addEdge(root, edge);

                if (mp.containsKey(edge)) {
                    mp.put(edge, mp.get(edge) + 1);
                } else {
                    mp.put(edge, 1);
                }
            }
        }

        for (int v = 0; v < G.V(); v++) {
            int indegree = mp.get(v) == null ? 0 : mp.get(v);
            int outdegree = ((Bag<Integer>) G.adj(v)).size();
            if (indegree > outdegree) {
                sentinalT = v;
            } else if (outdegree > indegree) {
                sentinalO = v;
            }
        }

        G.addEdge(sentinalT, sentinalO);

        DirectedEulerian euler = new DirectedEulerian(G);
        
        Queue<Integer> q1 =  new Queue<Integer>();
        Queue<Integer> q2 =  new Queue<Integer>();
        
         if (euler.isEulerian()) {
            boolean match = false;
            boolean isq1 = true;
            for (int v : euler.cycle()) {
                if (match && v== sentinalO) {
                    isq1 = false;
                } else {
                    match = false;
                }
                if (v == sentinalT) {
                    match = true;
                }
                if (isq1) {
                    q1.enqueue(v);
                } else {
                    q2.enqueue(v);
                }
            }
        }
        
         q1.dequeue();
        for (int v : q1) {
            q2.enqueue(v);
        }
        
        return q2;
    }

}
