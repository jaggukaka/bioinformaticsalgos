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
public class DataCache {

    private final Map<String, String> rnacodons = new HashMap<String, String>();

    private final Map<String, List<String>> rnadashcodons = new HashMap<String, List<String>>();

    private final Map<String, String> dnacodons = new HashMap<String, String>();

    private final Map<String, List<String>> dnadashcodons = new HashMap<String, List<String>>();

    private final Map<Integer, Character> massaminoacidmap = new HashMap<Integer, Character>();

    private final List<Integer> peptidemasses = new ArrayList<Integer>();

    private final List<Character> peptides = new ArrayList<Character>();

    private final Map<Character, Integer> aminoacidMass = new HashMap<Character, Integer>();

    private final Map<Character, Integer> scoringmatrixalphaindex = new HashMap<Character, Integer>();
    
    private final Map<Character, Integer> geneNucleotideIndex = new HashMap<Character, Integer>(4, 0.75f);
    
    private final Map<Integer, Character> geneIndexNucleotide = new HashMap<Integer, Character>(4, 0.75f);

    

   

    public static final String SPACE = " ";

    public static final String HYPEN = "-";

    private DataCache() {

    }

    private static final class DataCacheHolder {
        private static final DataCache DATA_CACHE = new DataCache();

        static {

            In in = new In("3MerNucleotidetoCodonmapping.txt");
            while (!in.isEmpty()) {
                String line = in.readLine();
                if (line != null) {
                    String[] items = line.split(SPACE);
                    String key = items[0];
                    String value = items.length > 1 ? items[1] : null;
                    DATA_CACHE.rnacodons.put(key, value);
                    List<String> threemers;
                    if (!DATA_CACHE.rnadashcodons.containsKey(value)) {
                        threemers = new ArrayList<String>();

                    } else {
                        threemers = DATA_CACHE.rnadashcodons.get(value);
                        threemers.add(key);
                    }
                    threemers.add(key);
                }
            }

            in = new In("3MerNucleotidetoCodonmappingdna.txt");
            while (!in.isEmpty()) {
                String line = in.readLine();
                if (line != null) {
                    String[] items = line.split(SPACE);
                    String key = items[0];
                    String value = items.length > 1 ? items[1] : null;
                    DATA_CACHE.dnacodons.put(key, value);
                    List<String> threemers;
                    if (!DATA_CACHE.dnadashcodons.containsKey(value)) {
                        threemers = new ArrayList<String>();

                    } else {
                        threemers = DATA_CACHE.dnadashcodons.get(value);
                        threemers.add(key);
                    }
                    threemers.add(key);
                }
            }

            in = new In("aminoacidmass.txt");
            while (!in.isEmpty()) {
                String line = in.readLine();
                if (line != null) {
                    String[] items = line.split(SPACE);
                    String value = items.length > 1 ? items[1] : null;
                    int val = Integer.valueOf(value);
                    String key = items[0];
                    DATA_CACHE.massaminoacidmap.put(val, new Character(key.charAt(0)));
                    if (!DATA_CACHE.peptidemasses.contains(val)) {
                        DATA_CACHE.peptidemasses.add(val);
                    }
                }
            }

            in = new In("aminoacidmass.txt");
            while (!in.isEmpty()) {
                String line = in.readLine();
                if (line != null) {
                    String[] items = line.split(SPACE);
                    String key = items[0];
                    String value = items.length > 1 ? items[1] : null;
                    int val = Integer.valueOf(value);
                    Character ch = new Character(key.charAt(0));
                    DATA_CACHE.aminoacidMass.put(ch, Integer.valueOf(value));
                    DATA_CACHE.massaminoacidmap.put(val, ch);
                    if (ch != 'K' && ch != 'I') {
                        DATA_CACHE.peptides.add(ch);
                    }
                }
            }

            in = new In("scoringmatrix.txt");
            String line = in.readLine();
            String[] alpha = line.split(SPACE);
            for (int i = 0; i < alpha.length; i++) {
                DATA_CACHE.scoringmatrixalphaindex.put(alpha[i].charAt(0), i);
            }
            
            DATA_CACHE.geneNucleotideIndex.put('A', 0);
            DATA_CACHE.geneNucleotideIndex.put('C', 1);
            DATA_CACHE.geneNucleotideIndex.put('G', 2);
            DATA_CACHE.geneNucleotideIndex.put('T', 3);
            
            DATA_CACHE.geneIndexNucleotide.put(0, 'A');
            DATA_CACHE.geneIndexNucleotide.put(1, 'C');
            DATA_CACHE.geneIndexNucleotide.put(2, 'G');
            DATA_CACHE.geneIndexNucleotide.put(3, 'T');

        }
    }

    public static DataCache getInstance() {
        return DataCacheHolder.DATA_CACHE;
    }

    public Map<String, String> getRnacodons() {
        return rnacodons;
    }

    public Map<String, List<String>> getRnadashcodons() {
        return rnadashcodons;
    }

    public Map<String, String> getDnacodons() {
        return dnacodons;
    }

    public Map<String, List<String>> getDnadashcodons() {
        return dnadashcodons;
    }

    public Map<Integer, Character> getMassaminoacidmap() {
        return massaminoacidmap;
    }

    public List<Integer> getPeptidemasses() {
        return peptidemasses;
    }

    public Map<Character, Integer> getAminoacidMass() {
        return aminoacidMass;
    }

    public List<Character> getPeptides() {
        return peptides;
    }

    public void allsets(String set, int left, String head, List<String> kmers) {

        if (left == 0) {
            kmers.add(head);
            return;
        }

        for (int i = 0; i < set.length() && left > 0; i++) {
            char c = set.charAt(i);
            String runner = head + c;
            allsets(set, left - 1, runner, kmers);
        }
    }

    public Map<Character, Integer> getScoringmatrixalphaindex() {
        return scoringmatrixalphaindex;
    }
    
    public Map<Character, Integer> getGeneNucleotideIndex() {
        return geneNucleotideIndex;
    }
    
    public Map<Integer, Character> getGeneIndexNucleotide() {
        return geneIndexNucleotide;
    }

}
