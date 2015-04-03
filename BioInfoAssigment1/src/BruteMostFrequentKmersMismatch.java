import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
public class BruteMostFrequentKmersMismatch {

    private BruteMostFrequentKmersMismatch() {

    }

    private static final class BruteMostFrequentKmersMismatchHolder {
        private static final BruteMostFrequentKmersMismatch BRUTE_MOST_FREQUENT_KMERS_MISMATCH = new BruteMostFrequentKmersMismatch();
    }

    public static BruteMostFrequentKmersMismatch getInstance() {
        return BruteMostFrequentKmersMismatchHolder.BRUTE_MOST_FREQUENT_KMERS_MISMATCH;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String data = in.readString();
        int k = in.readInt();
        int tolerence = in.readInt();
        Map<String, Integer> kmerMatchMap = new HashMap<String, Integer>();
        KmerMatch kmerMatch = new KmerMatch(kmerMatchMap, 1);
        BruteMostFrequentKmersMismatch.getInstance().findKmers(data, k, 1, kmerMatch, tolerence);
        int max = kmerMatch.getMax();
        System.out.println("Max is " + max);
        for (Entry<String, Integer> entry : kmerMatch.getMatch().entrySet()) {
            if (entry.getValue().intValue() == max) {
                System.out.println(entry.getKey());
            }
            // System.out.println(entry.getKey() + "=" + entry.getValue());
        }

    }

    public void findKmers(String data, int k, int t, KmerMatch kmerMatch, int tolereance) {
        Map<String, Integer> match = new HashMap<String, Integer>();
        boolean isMethod1 = true;
        int max = 1;
        if (data != null) {
            for (int i = 0; i < data.length() - k + 1; i++) {
                String knucleotide = data.substring(i, i + k);
                Map<String, Integer> mutations = new HashMap<String, Integer>();

                // Method 1
                if (isMethod1) {
                    processesKmer(tolereance, knucleotide, mutations, knucleotide);
                    //processesKmer2(tolereance, knucleotide, mutations, data);
                    for (Entry<String, Integer> entry : mutations.entrySet()) {
                        max = increaseCount(entry.getKey(), kmerMatch, max, t, match);
                    }
                } else {

                    // Method 2
                    processesKmer(tolereance, knucleotide, mutations, knucleotide);
                    max = processesMutations(mutations, tolereance, data, match, max);
                }
            }

        }

        if (!isMethod1) {
            kmerMatch.setMatch(match);
        }
        // System.out.println(match.size());

        kmerMatch.setMax(max);
    }
    
    public void findKmersWithReverseComplements(String data, int k, int t, KmerMatch kmerMatch, int tolereance) {
        Map<String, Integer> match = new HashMap<String, Integer>();
        boolean isMethod1 = true;
        int max = 1;
        if (data != null) {
            for (int i = 0; i < data.length() - k + 1; i++) {
                String kmer = data.substring(i, i + k);
                String reverseKmer = ReverseComplement.getInstance().reverseComplement(kmer);
                Map<String, Integer> mutations = new HashMap<String, Integer>();

                // Method 1
                if (isMethod1) {
                    processesKmer(tolereance, reverseKmer, mutations, reverseKmer);
                    //processesKmer2(tolereance, reverseKmer, mutations, data);
                    for (Entry<String, Integer> entry : mutations.entrySet()) {
                        max = increaseCount(entry.getKey(), kmerMatch, max, t, match);
                    }
                } else {

                    // Method 2
                    processesKmer(tolereance, reverseKmer, mutations, reverseKmer);
                    max = processesMutations(mutations, tolereance, data, match, max);
                }
            }

        }

        if (!isMethod1) {
            kmerMatch.setMatch(match);
        }
        // System.out.println(match.size());

        kmerMatch.setMax(max);
    }

    private int processesMutations(Map<String, Integer> mutations, int tolereance, String data,
        Map<String, Integer> match, int max) {
        // TODO Auto-generated method stub

        for (Entry<String, Integer> entry : mutations.entrySet()) {
            String mutation = entry.getKey();
            if (!match.containsKey(mutation)) {
                int size = MatchingWithAllowedMismatch.getMatches(data, mutation, tolereance).size();
                if (size >= max) {
                    max = size;
                    match.put(mutation, size);
                }
            }
        }

        return max;
    }

    private void processesKmer2(int tolereance, String knucleotide, Map<String, Integer> mutations, String data) {
        Collection<String> results = MatchingWithAllowedMismatch.getMatches(data, knucleotide, tolereance);
        for (String result : results) {
            mutations.put(result, 1);
        }
    }

    public Map<String, Integer> processesKmer(int tolerence, String kmer, Map<String, Integer> mutations,
        String parent) {
        // TODO Auto-generated method stub

        mutations.put(kmer, 1);
        if (tolerence == 0) {
            return mutations;
        }

        List<String> result = getMutations(kmer);
        for (int j = 0; j < result.size(); j++) {
            String kmerResult = result.get(j);
            if (!kmerResult.equals(parent) ) {
                processesKmer(tolerence - 1, kmerResult, mutations, kmer);
            }
        }

        return mutations;
    }

    private List<String> getMutations(String kmer) {
        // TODO Auto-generated method stub
        List<String> arr = new ArrayList<String>();
        for (int i = 0; i < kmer.length(); i++) {
            arr.addAll(getMutations(kmer, kmer.charAt(i), i));
        }
        return arr;
    }

    private int increaseCount(String kmer, KmerMatch kmerMatch, int max, int times, Map<String, Integer> match) {
        // TODO Auto-generated method stub
        int increment = 1;
        if (match.containsKey(kmer)) {
            increment = match.get(kmer) + 1;
            if (increment > max) {
                max = increment;
            }
            match.put(kmer, increment);

        } else {
            match.put(kmer, new Integer(1));
        }

        if (times <= increment) {
            kmerMatch.getMatch().put(kmer, increment);
        }

        return max;
    }

    private List<String> getMutations(String kmer, int dataChar, int i) {
        // TODO Auto-generated method stub
        List<String> arr = new ArrayList<String>();
        char[] kmerChars = kmer.toCharArray();
        if (dataChar == 'G') {
            kmerChars[i] = 'C';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'T';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'A';
            arr.add(new String(kmerChars));
        } else if (dataChar == 'A') {
            kmerChars[i] = 'C';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'T';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'G';
            arr.add(new String(kmerChars));
        } else if (dataChar == 'T') {
            kmerChars[i] = 'C';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'G';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'A';
            arr.add(new String(kmerChars));
        } else if (dataChar == 'C') {
            kmerChars[i] = 'T';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'G';
            arr.add(new String(kmerChars));
            kmerChars[i] = 'A';
            arr.add(new String(kmerChars));
        }
        return arr;
    }

}
