import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ContigGeneration {

    private ContigGeneration() {

    }

    private static final class ContigGenerationHolder {
        private static final ContigGeneration ContigGeneration = new ContigGeneration();
    }

    public static ContigGeneration getInstance() {
        return ContigGenerationHolder.ContigGeneration;
    }

    public String[] debruijin(String[] st, String allsets) {
        int len = 2 * st.length;
        String[] dnalist = new String[len];

        for (int i = 0, k = 0; i < st.length; i++, k++) {
            String key = st[i];
            String knucleotide = key.substring(0, key.length() - 1);
            String link = key.substring(1, key.length());
            dnalist[k] = knucleotide;
            dnalist[++k] = link;
        }

        return debruijin(dnalist, st, allsets);
    }

    public String[] debruijin(String[] st, String[] orig, String allsets) {

        Map<String, Boolean> map = new HashMap<String, Boolean>();

        for (String s : st) {
            map.put(s, false);
        }

        Map<String, Integer> map2 = new HashMap<String, Integer>();

        for (String s : orig) {
            map2.put(s, map2.containsKey(s) ? map2.get(s) + 1 : 1);
        }

        Quick3string.sort(st);

        String[] solution = new String[st.length];
        for (int i = 0; i < st.length; i++) {

            String s = st[i];
            if (map.get(s)) {
                continue;
            } else {
                map.put(s, true);
            }
            String suffix = s.substring(1);
            List<String> list = modifyclms(suffix, map, map2, s, allsets);

            int size = list.size();
            if (size > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(s).append(DataCache.SPACE).append("->").append(DataCache.SPACE);
                for (int j = 0; j < size; j++) {
                    sb.append(list.get(j));
                    if (j < size - 1) {
                        sb.append(',');
                    }
                }
                solution[i] = sb.toString();
            }
        }

        return solution;
    }

    private List<String> modifyclms(String suffix, Map<String, Boolean> clms, Map<String, Integer> orig,
        String str, String x) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < x.length(); i++) {
            String s = suffix + x.charAt(i);
            String st = str + x.charAt(i);
            if (clms.containsKey(s) && orig.containsKey(st)) {
                int times = orig.get(st);
                while (times-- > 0)
                    list.add(s);
            }
        }

        return list;

    }

    public List<String> contigs(List<String> edges) {
        Map<String, Integer> mp = new HashMap<String, Integer>();
        Map<Integer, String> mp2 = new HashMap<Integer, String>();
        MaxElem elem = GenomeSequence.getInstance().getNode(edges, mp, mp2);
        return generateContigs(elem.getMax(), elem.getNodes(), mp2);
    }

    public List<String> generateContigs(int max, List<int[]> nodes, Map<Integer, String> mp2) {
        Digraph G = new Digraph(max + 1);

        List<String> ls = new ArrayList<String>();
        Map<Integer, Integer> mp = new HashMap<Integer, Integer>();

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

        boolean[] marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            StringBuffer s = new StringBuffer();
            if (!marked[v]) {
                performDFSAction(s, ls, marked, mp, mp2, G, v);
            }
        }

        return ls;
    }

    private void dfs(StringBuffer s, List<String> ls, boolean[] marked, Map<Integer, Integer> mp,
        Map<Integer, String> mp2, Digraph G, int v) {

        marked[v] = true;
        for (int w : G.adj(v)) {
           
            if (!marked[w]) {
                performDFSAction(s, ls, marked, mp, mp2, G, w);
            } else {
                s.append(mp2.get(w));
                ls.add(s.toString());
                
                
            }
            s = new StringBuffer();
            s.append(mp2.get(v).charAt(0));

        }

    }

    private void performDFSAction(StringBuffer s, List<String> ls, boolean[] marked, Map<Integer, Integer> mp,
        Map<Integer, String> mp2, Digraph G, int v) {

        int indegree = mp.get(v) == null ? 0 : mp.get(v);
        int outdegree = ((Bag<Integer>) G.adj(v)).size();
        if (indegree != 1 || outdegree != 1) {

            if (s.length() != 0) {
                s.append(mp2.get(v));
                ls.add(s.toString());
            }

            s = new StringBuffer();
            s.append(mp2.get(v).charAt(0));
        } else {
            s.append(mp2.get(v).charAt(0));
        }
        dfs(s, ls, marked, mp, mp2, G, v);

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

        String[] st = in.readAllStrings();

        Stopwatch sw = new Stopwatch();
        // String[] mp = Overlap.getInstance().overlap2(dnalist);
        String[] mp = ContigGeneration.getInstance().debruijin(st, "ACGT");
        List<String> contigs = ContigGeneration.getInstance().contigs(Arrays.asList(mp));
        // for (String st : mp) {
        // buffer.append(st);
        // buffer.append('\n');
        // }
        //
        // System.out.println(buffer.toString());

        Composition.getInstance().copyFile(args[0] + "_sol.txt", contigs);
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

}
