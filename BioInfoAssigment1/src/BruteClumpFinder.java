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
public class BruteClumpFinder {

    private static final char SPACE = ' ';

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String data = in.readString();
        int k = in.readInt();
        int l = in.readInt();
        int t = in.readInt();
        StringBuffer buffer = new StringBuffer();
        if (data != null) {
            Map<String, Integer> kmerMatchMap = new HashMap<String, Integer>();
            KmerMatch kmerMatch = new KmerMatch(kmerMatchMap, 1);
            for (int i = 0; i < data.length() - l + 1; i++) {
                String text = data.substring(i, i + l);
                BruteMostFrequentKMers.getInstance().findKmers(text, k, t, kmerMatch);
            }
            
            for (Entry<String, Integer> entry : kmerMatch.getMatch().entrySet()) {
                buffer.append(entry.getKey() + SPACE);
            }
        }
        System.out.println(buffer.toString());
    }

}
