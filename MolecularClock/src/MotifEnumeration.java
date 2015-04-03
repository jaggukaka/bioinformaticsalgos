import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class MotifEnumeration {

    private MotifEnumeration() {

    }

    private static final class MotifEnumerationHolder {
        private static final MotifEnumeration MOTIF_ENUMERATION = new MotifEnumeration();
    }

    public static MotifEnumeration getInstance() {
        return MotifEnumerationHolder.MOTIF_ENUMERATION;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        int k = in.readInt();
        int m = in.readInt();

        String[] dnalist = in.readAllStrings();
        
        StringBuffer buffer =  new StringBuffer();
        
        Map<String, Integer> mp = MotifEnumeration.getInstance().motifEnumeration(dnalist, m, k);
        for (String st : mp.keySet()) {
            buffer.append(st);
            buffer.append(DataCache.SPACE);
        }

        System.out.println(buffer.toString());
        System.out.println(mp.size());

    }

    public Map<String, Integer> motifEnumeration(String[] dnalist, int mismatch, int k) {
        Map<String, Integer> commonkmers = new HashMap<String, Integer>();

        for (String dna : dnalist) {
            commonKmers(dna, mismatch, k, commonkmers);
        }
        return commonkmers;
    }

    private void commonKmers(String dna, int mismatch, int k, Map<String, Integer> previous) {
        boolean put = previous.size() == 0;
        Map<String, Integer> kmers = new HashMap<String, Integer>();
        for (int i = 0; i < dna.length() - k + 1; i++) {
            String knucleotide = dna.substring(i, i + k);
            Map<String, Integer> mutations = new HashMap<String, Integer>();
            BruteMostFrequentKmersMismatch.getInstance().processesKmer(mismatch, knucleotide, mutations,
                knucleotide);

            for (Entry<String, Integer> entry : mutations.entrySet()) {
                String key = entry.getKey();
                if (put) {
                    previous.put(key, 1);
                } else if (previous.containsKey(key)) {
                    kmers.put(key, 1);
                }
            }
        }
        if (!put) {
            previous.clear();
            previous.putAll(kmers);
        }
    }

  

}
