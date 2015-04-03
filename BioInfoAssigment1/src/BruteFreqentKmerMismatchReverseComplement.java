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
public class BruteFreqentKmerMismatchReverseComplement {

    private static final char SPACE = ' ';

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String data = in.readString();
        int k = in.readInt();
        int tolerence = in.readInt();
        BruteFreqentKmerMismatchReverseComplement brt = new BruteFreqentKmerMismatchReverseComplement();
        String minPQ = brt.computeKmersAndReverseComplements2(data, k, tolerence);

        System.out.println(minPQ);
    }

    public String computeKmersAndReverseComplements(String data, int k, int tolerence) {
        Map<String, Integer> kmerMatchMap = new HashMap<String, Integer>();
        KmerMatch kmerMatch = new KmerMatch(kmerMatchMap, 1);
        BruteMostFrequentKmersMismatch.getInstance().findKmers(data, k, 0, kmerMatch, tolerence);
        int max = kmerMatch.getMax();
        System.out.println("Max of kmer is " + max);

        int combinedMax = max;
        MinPQ<ReverseComplementResult> pq = new MinPQ<BruteFreqentKmerMismatchReverseComplement.ReverseComplementResult>();
        for (Entry<String, Integer> entry : kmerMatch.getMatch().entrySet()) {
            String kmer = entry.getKey();
            // if ("ACAT".equals(kmer)) {
            // System.out.println("Come here");
            // }
            int size = entry.getValue();
            String reverseKmer = ReverseComplement.getInstance().reverseComplement(kmer);

            int currentMax = size + MatchingWithAllowedMismatch.getMatches(data, reverseKmer, tolerence).size();
            if (combinedMax <= currentMax) {
                combinedMax = currentMax;
                // System.out.println(kmer + " " + size);
                pq.insert(new ReverseComplementResult(kmer, currentMax));
            }
        }

        System.out.println("Combined max is " + combinedMax);
        StringBuffer buffer = new StringBuffer();
        while (!pq.isEmpty()) {
            ReverseComplementResult reverseComplementResult = pq.delMin();
            if (reverseComplementResult.maxCount < combinedMax) {
                break;
            } else {
                buffer.append(reverseComplementResult.kmer);
                buffer.append(SPACE);
            }
        }

        return buffer.toString();

    }

    public String computeKmersAndReverseComplements2(String data, int k, int tolerence) {
        Map<String, Integer> kmerMatchMap1 = new HashMap<String, Integer>();
        Map<String, Integer> kmerMatchMap2 = new HashMap<String, Integer>();
        KmerMatch kmerMatch1 = new KmerMatch(kmerMatchMap1, 1);
        BruteMostFrequentKmersMismatch.getInstance().findKmers(data, k, 0, kmerMatch1, tolerence);
        int max1 = kmerMatch1.getMax();
        System.out.println("Max of kmer is " + max1);

        KmerMatch kmerMatch2 = new KmerMatch(kmerMatchMap2, 1);
        BruteMostFrequentKmersMismatch.getInstance().findKmersWithReverseComplements(data, k, 0, kmerMatch2, tolerence);
        int max2 = kmerMatch2.getMax();
        System.out.println("Max of kmer reverse complements is " + max2);

        int combinedMax = max1;
        MinPQ<ReverseComplementResult> pq = new MinPQ<BruteFreqentKmerMismatchReverseComplement.ReverseComplementResult>();
        for (Entry<String, Integer> entry : kmerMatch1.getMatch().entrySet()) {
            String kmer = entry.getKey();
            
            int size1 = entry.getValue();
            int size2 = 0;
            if (kmerMatch2.getMatch().containsKey(kmer)) {
                size2 = kmerMatch2.getMatch().get(kmer);
            }

            int currentMax = size1 + size2;
            if (combinedMax <= currentMax) {
                combinedMax = currentMax;
                // System.out.println(kmer + " " + size);
                pq.insert(new ReverseComplementResult(kmer, currentMax));
            }
        }
        System.out.println("Combined max is " + combinedMax);
        StringBuffer buffer = new StringBuffer();
        while (!pq.isEmpty()) {
            ReverseComplementResult reverseComplementResult = pq.delMin();
            if (reverseComplementResult.maxCount < combinedMax) {
                break;
            } else {
                buffer.append(reverseComplementResult.kmer);
                buffer.append(SPACE);
            }
        }

        return buffer.toString();

    }
    
    public String computeKmersAndReverseComplements3(String data, int k, int tolerence) {
        Map<String, Integer> kmerMatchMap1 = new HashMap<String, Integer>();
        Map<String, Integer> kmerMatchMap2 = new HashMap<String, Integer>();
        KmerMatch kmerMatch1 = new KmerMatch(kmerMatchMap1, 1);
        BruteMostFrequentKmersMismatch.getInstance().findKmers(data, k, 0, kmerMatch1, tolerence);
        int max1 = kmerMatch1.getMax();
        System.out.println("Max of kmer is " + max1);

        KmerMatch kmerMatch2 = new KmerMatch(kmerMatchMap2, 1);
        BruteMostFrequentKmersMismatch.getInstance().findKmersWithReverseComplements(data, k, 0, kmerMatch2, tolerence);
        int max2 = kmerMatch2.getMax();
        System.out.println("Max of kmer reverse complements is " + max2);

        StringBuffer buffer = new StringBuffer();
        for (Entry<String, Integer> entry : kmerMatch1.getMatch().entrySet()) {
            if (entry.getValue().intValue() == max1) {
                buffer.append(entry.getKey());
                buffer.append(SPACE);
            }
            // System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        
        for (Entry<String, Integer> entry : kmerMatch2.getMatch().entrySet()) {
            if (entry.getValue().intValue() == max2) {
                buffer.append(entry.getKey());
                buffer.append(SPACE);
            }
            // System.out.println(entry.getKey() + "=" + entry.getValue());
        }

      return buffer.toString();
    }

    // private int calculateSize(String reverseKmer, int tolerence, Map<String, Integer> matches, String data) {
    // // TODO Auto-generated method stub
    // int count = 0;
    // Map<String, Integer> mutations = new HashMap<String, Integer>();
    // mutations = BruteMostFrequentKmersMismatch.getInstance().processesKmer(tolerence, reverseKmer, mutations,
    // reverseKmer);
    //
    // for (Entry<String, Integer> entry : mutations.entrySet()) {
    // String kmer = entry.getKey();
    // BoyerMooreStringMatch boyerMooreStringMatch = new BoyerMooreStringMatch();
    // List<StringMatch> match = boyerMooreStringMatch.search(data, 0, data.length(), kmer);
    // count = count + match.size();
    // }
    // return count;
    // }

    private final class ReverseComplementResult implements Comparable<ReverseComplementResult> {
        String kmer;

        int maxCount;

        public ReverseComplementResult(String kmer, int maxCount) {
            super();
            this.kmer = kmer;
            this.maxCount = maxCount;
        }

        @Override
        public int compareTo(ReverseComplementResult o) {
            if (this.maxCount > o.maxCount) {
                return -1;
            } else if (this.maxCount == o.maxCount) {
                return 0;
            } else {
                return +1;
            }
        }

    }

}
