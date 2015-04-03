import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class LongestPathDG {

    private LongestPathDG() {

    }

    private static final class LongestPathDGHolder {
        private static final LongestPathDG COUNTING_PEPTIDE = new LongestPathDG();

    }

    public static LongestPathDG getInstance() {
        return LongestPathDGHolder.COUNTING_PEPTIDE;
    }

    public Path longestPath(int sr, int si, EdgeWeightedDigraph dg) {

        AcyclicLPNew lp = new AcyclicLPNew(dg, sr);
        return new Path(lp.distTo(si), lp.pathTo(si));
    }
    
    private static class Path {
        double d;
        
        Iterable<DirectedEdge> path;

        public Path(double d, Iterable<DirectedEdge> path) {
            super();
            this.d = d;
            this.path = path;
        }
        
        
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        // Solve overlap
        // String[] dnalist = in.readAllStrings();

        // solve debrujin

        int src = Integer.parseInt(in.readLine().trim());

        int sink = Integer.parseInt(in.readLine().trim());

        EdgeWeightedDigraph dg = new EdgeWeightedDigraph(sink + 1);
        while (!in.isEmpty()) {

            String str = in.readLine();
            String[] st = str.split("->");
            
            int v = Integer.parseInt(st[0]);
            String[] edge = st[1].split(":");
            
            

            dg.addEdge(new DirectedEdge(v, Integer.parseInt(edge[0]), Integer.parseInt(edge[1])));
        }

        Stopwatch sw = new Stopwatch();
        // String[] mp = Overlap.getInstance().overlap2(dnalist);
        Path mp =  LongestPathDG.getInstance().longestPath(src, sink, dg);
        
        List<String> ls = new LinkedList<String>();
        ls.add(String.valueOf( (int)mp.d));
        StringBuffer buffer = new StringBuffer();
        int j = ((Stack<DirectedEdge>) mp.path).size();
        int k = 1;
        for (DirectedEdge de : mp.path) {
            
            buffer.append(de.from());
            buffer.append("->");
            if (k == j) {
                buffer.append(de.to());
            }
            k++;
        }
        ls.add(buffer.toString());
        
        // for (String st : mp) {
        // buffer.append(st);
        // buffer.append('\n');
        // }
        //
        // System.out.println(buffer.toString());

        Composition.getInstance().copyFile(args[0] + "_sol.txt", ls);
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

}
