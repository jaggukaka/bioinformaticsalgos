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
public class Overlap {

    private Overlap() {

    }

    private static final class OverlapHolder {
        private static final Overlap Overlap = new Overlap();
    }

    public static Overlap getInstance() {
        return OverlapHolder.Overlap;
    }

    public String[] overlap(String[] st) {
        StringBuilder buffer = new StringBuilder();

        for (String s : st) {
            buffer.append(s);
        }

        String text = buffer.toString();
        SuffixTrie trie = new SuffixTrie(text);

        Quick3string.sort(st);

        String[] solution = new String[st.length];
        for (int i = 0; i < st.length; i++) {
            StringBuilder sb = new StringBuilder();
            String s = st[i];
            String suffix = s.substring(1);
            String edgeTo = trie.lcsuffix(suffix, s.length());
            sb.append(s).append(DataCache.SPACE).append("->").append(DataCache.SPACE).append(edgeTo);
            solution[i] = sb.toString();
        }

        return solution;
    }

    public String[] overlap2(String[] st, String allsets) {

        Map<String, Boolean> map = new HashMap<String, Boolean>();

        for (String s : st) {
            map.put(s, false);
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
            List<String> list = modifyclms(suffix, map, allsets);

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
    
    
    public String[] debruijin(String[] st, String allsets) {
        int len = 2 * st.length;
        String[] dnalist = new String[len];

        for (int i = 0, k=0; i < st.length; i++, k++) {
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
        
        Map<String, Boolean> map2 = new HashMap<String, Boolean>();

        for (String s : orig) {
            map2.put(s, false);
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


    private List<String> modifyclms(String suffix, Map<String, Boolean> clms, String x) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < x.length(); i++) {
            String s = suffix + x.charAt(i);
            if (clms.containsKey(s)) {
                list.add(s);
            }
        }

        return list;

    }
    
    
    private List<String> modifyclms(String suffix, Map<String, Boolean> clms, Map<String, Boolean> orig, String str,  String x) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < x.length(); i++) {
            String s = suffix + x.charAt(i);
            String st = str + x.charAt(i);
            if (clms.containsKey(s) && orig.containsKey(st)) {
                list.add(s);
            }
        }

        return list;

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
        //String[] mp = Overlap.getInstance().overlap2(dnalist);
        String[] mp = Overlap.getInstance().debruijin(st, "ACGT");
        // for (String st : mp) {
        // buffer.append(st);
        // buffer.append('\n');
        // }
        //
        // System.out.println(buffer.toString());

        Composition.getInstance().copyFile(args[0] + "_sol.txt", mp);
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

    

}
