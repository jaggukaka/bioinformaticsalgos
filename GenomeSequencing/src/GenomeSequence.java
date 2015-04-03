import java.io.IOException;
import java.util.ArrayList;
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
public class GenomeSequence {

    private GenomeSequence() {

    }

    private static final class GenomeSequenceHolder {
        private static final GenomeSequence GenomeSequence = new GenomeSequence();
    }

    public static GenomeSequence getInstance() {
        return GenomeSequenceHolder.GenomeSequence;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);
        List<String> ls = new ArrayList<String>();
        while (!in.isEmpty()) {
            ls.add(in.readLine());
        }

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", GenomeSequence.getInstance().genome(ls));
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

    public String genome(List<String> edges) {
        Map<String, Integer> mp = new HashMap<String, Integer>();
        Map<Integer, String> mp2 = new HashMap<Integer, String>();

        Iterable<Integer> euler = genomePath(edges, mp, mp2);
        if (euler == null) {

            return "Not eulerian";
        }

        StringBuffer buffer = new StringBuffer();
        int j = ((Queue<Integer>) euler).size();
        for (int v : euler) {
            if (j > 1) {
                buffer.append(mp2.get(v).charAt(0));
            } else {
                buffer.append(mp2.get(v));
            }
            j--;
        }

        return buffer.toString();
    }

    public String genomeCycle(List<String> edges) {

        Map<String, Integer> mp = new HashMap<String, Integer>();
        Map<Integer, String> mp2 = new HashMap<Integer, String>();

        Iterable<Integer> euler = genomecycle(edges, mp, mp2);
        if (euler == null) {

            return "Not eulerian";
        }

        StringBuffer buffer = new StringBuffer();
        int j = ((Stack<Integer>) euler).size();

        for (int v : euler) {
            if (j > 1)
            buffer.append(mp2.get(v).charAt(0));

            j--;
        }

        return buffer.toString();

    }

    public Iterable<Integer> genomePath(List<String> edges, Map<String, Integer> mp, Map<Integer, String> mp2) {

        MaxElem elem = getNode(edges, mp, mp2);
        Iterable<Integer> euler = EulerianPath.getInstance().getEulerPathDG(elem.getMax(), elem.getNodes());
        return euler;
    }

    private Iterable<Integer> genomecycle(List<String> edges, Map<String, Integer> mp, Map<Integer, String> mp2) {

        MaxElem elem = getNode(edges, mp, mp2);
        Iterable<Integer> euler = EulerianCycle.getInstance().getEulerCycleDG(elem.getMax(), elem.getNodes());
        return euler;
    }

    public MaxElem getNode(List<String> edges, Map<String, Integer> mp, Map<Integer, String> mp2) {
        List<int[]> nodes = new ArrayList<int[]>();
        int max = 0;

        int count = 0;
        for (String line : edges) {
            if (line == null)
                continue;
            String[] edge = line.split("->");
            
            if (edge.length < 2) {
                continue;
            }

            String is = edge[0].trim();

            int i = count;
            String[] edgeTo = edge[1].trim().split(",");

            if (mp.containsKey(is)) {
                i = mp.get(is);
            } else {
                mp.put(is, count);
                mp2.put(count++, is);
            }

            int[] nodeval = new int[edgeTo.length + 1];
            nodeval[0] = i;
            max = Math.max(max, i);
            for (int m = 0; m < edgeTo.length; m++) {
                String ks = edgeTo[m];
                int k = count;
                if (mp.containsKey(ks)) {
                    k = mp.get(ks);
                } else {
                    mp.put(ks, count);
                    mp2.put(count++, ks);
                }
                nodeval[m + 1] = k;
                max = Math.max(max, k);
            }

            nodes.add(nodeval);
        }

        return new MaxElem(max, nodes);
    }

    

}
