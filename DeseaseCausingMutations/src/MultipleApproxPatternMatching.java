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
public class MultipleApproxPatternMatching {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readLine();
        String[] patterns = in.readLine().split(DataCache.SPACE);
        int d = Integer.parseInt(in.readLine());

        Stopwatch sw = new Stopwatch();

        List<Integer> matches = matching(s + "$", patterns, d);
        Integer[] ma = new Integer[matches.size()];
        matches.toArray(ma);
        Quick3way.sort(ma);

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < ma.length; i++) {
            buffer.append(ma[i]);
            if (i != ma.length - 1)
                buffer.append(DataCache.SPACE);
        }

        Composition.getInstance().copyFile(args[0] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    private static List<Integer> matching(String s, String[] patterns, int d) {
        // TODO Auto-generated method stub
        
        SuffixArrBWT suffixArrBWT = BurrowsWheelerTransform.getInstance().burrowsWheelerTransform(s);
        SuffixArrayX ps = suffixArrBWT.getSx();

        String firstcolumn = new String(suffixArrBWT.getBwt());
        SortedColumns fcsc = InverseBurrowsWheelerTransform.getInstance().getnamedcolumns(firstcolumn, true);
        SortedColumns lcsc = InverseBurrowsWheelerTransform.getInstance().getnamedcolumns(suffixArrBWT.getBwt(), false);

        Map<String, LastToFirst> mp = new HashMap<String, LastToFirst>();
        

        for (int i = 0; i < fcsc.getCs().length; i++) {
            mp.put(fcsc.getCs()[i], new LastToFirst(lcsc.getCs()[i], i));
        }

        for (String pattern : patterns) {
            int k = pattern.length()/(d + 1);
            List<Integer> indicies = new ArrayList<Integer>();
            for (int i =0, m =0; i < pattern.length();  m++) {
                String mpat = m < d ? pattern.substring(i, i+k) : pattern.substring(i, pattern.length());
                CheckPointMatching.findoccurences(mpat, fcsc, lcsc, mp, indicies, ps);
            }
            
            if ( indicies.size() > 0) {//check further
                IntervalST<Boolean> st = new IntervalST<Boolean>();
                for (int index : indicies) {
                    String mpat = s.substring(index, index + k);
                    
                }
                
            }
        }

        return indicies;
    }

}
