import java.util.List;


/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class EulerianCycle {

    private EulerianCycle() {

    }

    private static final class EulerianCycleHolder {
        private static final EulerianCycle EulerianCycle = new EulerianCycle();
    }

    public static EulerianCycle getInstance() {
        return EulerianCycleHolder.EulerianCycle;
    }


    
    public Iterable<Integer> getEulerCycleDG(int max, List<int[]> nodes) {
        Digraph G = new Digraph(max + 1);

        for (int[] edges : nodes) {
            int root = edges[0];

            for (int i = edges.length - 1; i > 0; i--) {
                G.addEdge(root, edges[i]);
            }
        }

        DirectedEulerian euler = new DirectedEulerian(G);
        if (euler.isEulerian()) {
            return euler.cycle();
        }
        return null;
    }

}
