import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class ReadPairs {

    private ReadPairs() {

    }

    private static final class ReadPairsHolder {
        private static final ReadPairs ReadPairs = new ReadPairs();
    }

    public static ReadPairs getInstance() {
        return ReadPairsHolder.ReadPairs;
    }

    public String readPair(String[] st, int d) {
        List<String> ls = ReadPairs.getInstance().debruijin(st);
        String path = genome(ls, d);
        return path;
    }

    public String genome(List<String> edges, int d) {
        Map<String, Integer> mp = new HashMap<String, Integer>();
        Map<Integer, String> mp2 = new HashMap<Integer, String>();

        Iterable<Integer> euler = GenomeSequence.getInstance().genomePath(edges, mp, mp2);
        if (euler == null) {

            return "Not eulerian";
        }

        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        int j = ((Queue<Integer>) euler).size();
        for (int v : euler) {
            String s = mp2.get(v);
            if (j > 1) {
                buffer.append(s.charAt(0));
            } else {
                buffer.append(s.split(":")[0]);
            }
            int k = j - d - 1;
            if (k <= 1 && j > 1) {
                buffer2.append(s.split(":")[1].charAt(0));
            } else if (j == 1) {
                buffer2.append(s.split(":")[1]);
            }

            j--;
        }

        buffer.append(buffer2);
        return buffer.toString();
    }

    public List<String> debruijin(String[] st) {
        int len = 2 * st.length;
        PairKmer[] readpair = new PairKmer[len];

        for (int i = 0, k = 0; i < st.length; i++, k++) {
            String[] key = st[i].split("\\|");

            String rkey = key[0];
            String lkey = key[1];

            String rpk1 = rkey.substring(0, rkey.length() - 1);
            String lpk1 = lkey.substring(0, lkey.length() - 1);

            PairKmer pk1 = new PairKmer(rpk1, lpk1);

            String rpk2 = rkey.substring(1);
            String lpk2 = lkey.substring(1);

            PairKmer pk2 = new PairKmer(rpk2, lpk2);
            pk1.pkmer = pk2;

            readpair[k] = pk1;
            readpair[++k] = pk2;
        }

        return debruijin(readpair);
    }

    public List<String> debruijin(PairKmer[] st) {

        Map<PairKmer, List<PairKmer>> map = new HashMap<PairKmer, List<PairKmer>>();

        List<String> solution = new LinkedList<String>();
        for (PairKmer s : st) {

            if (map.containsKey(s)) {
                List<PairKmer> ls = map.get(s);
                if (s.pkmer != null) {
                    ls.add(s.pkmer);
                }
            } else {
                List<PairKmer> ls = new LinkedList<PairKmer>();
                if (s.pkmer != null) {
                    ls.add(s.pkmer);
                }
                map.put(s, ls);
            }
        }

        for (Entry<PairKmer, List<PairKmer>> mp : map.entrySet()) {
            PairKmer s = mp.getKey();
            StringBuilder sb = new StringBuilder();

            LinkedList<PairKmer> ls = (LinkedList<PairKmer>) mp.getValue();
            for (int j = 0; j < ls.size(); j++) {
                sb.append(s);
                sb.append(DataCache.SPACE).append("->").append(DataCache.SPACE);
                sb.append(ls.poll());
                if (j < ls.size() - 1) {
                    sb.append(',');
                }
            }
            solution.add(sb.toString());
        }

        return solution;
    }

    private static class PairKmer {

        String upper;

        String lower;

        PairKmer pkmer;

        public PairKmer(String upper, String lower) {
            this.upper = upper;
            this.lower = lower;
        }

        public boolean equals(Object kmer) {
            if (this == kmer) {
                return true;
            }
            if (kmer == null) {
                return false;
            }
            if (kmer instanceof PairKmer) {
                return this.upper.equalsIgnoreCase(((PairKmer) kmer).upper)
                    && this.lower.equalsIgnoreCase(((PairKmer) kmer).lower);
            }

            return false;
        }

        public int hashCode() {
            int h = 1;
            h = 31 * h + upper.hashCode();
            h = 31 * h + lower.hashCode();

            return h;
        }

        public String toString() {
            return upper.toString() + ":" + lower.toString();
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

        int d = in.readInt();

        String[] st = in.readAllStrings();

        Stopwatch sw = new Stopwatch();
        // String[] mp = Overlap.getInstance().overlap2(dnalist);
        String mp = ReadPairs.getInstance().readPair(st, d);
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
