import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class MedianString {

    private MedianString() {

    }

    private static final class MedianStringHolder {
        private static final MedianString MEDIAN_STRING = new MedianString();
    }

    public static MedianString getInstance() {
        return MedianStringHolder.MEDIAN_STRING;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        int k = in.readInt();

        String[] dnalist = in.readAllStrings();

        System.out.println(MedianString.getInstance().medianString(dnalist, k));

    }

    public String medianString(String[] dnaList, int k) {
        List<String> kmers = new ArrayList<String>();
        DataCache.getInstance().allsets("ACGT", k, "", kmers);

        MinPQ<Kmer> pq = new MinPQ<Kmer>();
        for (String kmer : kmers) {
            int count = 0;
            for (String dna : dnaList) {
                count += search(dna, kmer);
            }
            pq.insert(new Kmer(kmer, count));
        }

       

        return pq.delMin().kmer;
    }

    private int search(String dna, String kmer) {
        // TODO Auto-generated method stub
        int k = kmer.length();
        int min = k;
        for (int i = 0; i < dna.length() - k + 1; i++) {
            String knucleotide = dna.substring(i, i + k);
            int mis = 0;
            for (int j = 0; j < k; j++) {

                if (kmer.charAt(j) != knucleotide.charAt(j)) {
                    mis++;
                }
            }

            if (min > mis) {
                min = mis;
            }
        }
        return min;
    }

    

    

}
