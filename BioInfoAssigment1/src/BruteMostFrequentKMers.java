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
public class BruteMostFrequentKMers {

    private BruteMostFrequentKMers() {

    }

    private static final class BruteMostFrequentKMersHolder {
        private static final BruteMostFrequentKMers BRUTE_MOST_FREQUENT_K_MERS = new BruteMostFrequentKMers();
    }

    public static BruteMostFrequentKMers getInstance() {
        return BruteMostFrequentKMersHolder.BRUTE_MOST_FREQUENT_K_MERS;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String data = in.readString();
        int k = in.readInt();
        Map<String, Integer> kmerMatchMap = new HashMap<String, Integer>();
        KmerMatch kmerMatch = new KmerMatch(kmerMatchMap, 1);
        BruteMostFrequentKMers.getInstance().findKmers(data, k, 1, kmerMatch);
        int max = kmerMatch.getMax();
        System.out.println("Max is " + max);
        for (Entry<String, Integer> entry : kmerMatch.getMatch().entrySet()) {
            if (entry.getValue().intValue() == max) {
                System.out.println(entry.getKey());
            }
        }

    }

    public void findKmers(String data, int k, int t, KmerMatch kmerMatch) {
        Map<String, Integer> match = new HashMap<String, Integer>();
        
        int max = 1;
        if (data != null) {
            for (int i = 0; i < data.length() - k + 1; i++) {
                String knucleotide = data.substring(i, i + k);
                if (!match.containsKey(knucleotide)) {
                    match.put(knucleotide, new Integer(1));
                } else {
                    int increment = match.get(knucleotide) + 1;
                    if (max < increment) {
                        max = increment;
                    }
                    match.put(knucleotide, increment);
                    if (increment >= t) {
                        kmerMatch.getMatch().put(knucleotide, increment);
                    }
                }
            }
        }
        
        kmerMatch.setMax(max);
    }

}
