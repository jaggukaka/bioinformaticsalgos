import java.util.Iterator;

/**
 * @author jpyla
 * 
 */
public class DirectedEulerian {

    private Stack<Integer> cycle = new Stack<Integer>();

    private boolean isEulerian = true;

    // find Eulerian cycle
    public DirectedEulerian(Digraph G) {

        // create local view of adjacency lists
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // find vertex with nonzero degree as start of potential Eulerian cycle
        int s = 0;
        for (int v = 0; v < G.V(); v++) {
            if (adj[v].hasNext()) {
                s = v;
                break;
            }
        }

        // greedily add to cycle, depth-first search style
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            cycle.push(v);
            int w = v;
            while (adj[w].hasNext()) {
                stack.push(w);
                w = adj[w].next();
            }
            if (w != v)
                isEulerian = false;
        }

        // check if all edges have been used
        for (int v = 0; v < G.V(); v++)
            if (adj[v].hasNext())
                isEulerian = false;
    }

    // return Eulerian cycle, or null if no such tour
    public Iterable<Integer> cycle() {
        if (!isEulerian)
            return null;
        return cycle;
    }

    // does the digraph have an Eulerian tour?
    public boolean isEulerian() {
        return isEulerian;
    }

  

}
