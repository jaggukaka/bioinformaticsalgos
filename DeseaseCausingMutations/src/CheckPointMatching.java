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
public class CheckPointMatching {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readLine();
        String[] patterns = in.readAllStrings();

        Stopwatch sw = new Stopwatch();
        
        List<Integer> matches = matching(s + "$", patterns);
        Integer[] ma = new Integer[matches.size()];
        matches.toArray(ma);
        Quick3way.sort(ma);

        StringBuffer buffer = new StringBuffer();
        for (int i =0 ; i < ma.length; i++ ) {
            buffer.append(ma[i]);
            if (i != ma.length  -1 )
            buffer.append(DataCache.SPACE);
        }

        Composition.getInstance().copyFile(args[0] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    private static List<Integer> matching(String s, String[] patterns) {
        
        SuffixArrBWT suffixArrBWT = BurrowsWheelerTransform.getInstance().burrowsWheelerTransform(s);
        SuffixArrayX ps = suffixArrBWT.getSx();

        String firstcolumn = new String(suffixArrBWT.getBwt());
        SortedColumns fcsc = InverseBurrowsWheelerTransform.getInstance().getnamedcolumns(firstcolumn, true);
        SortedColumns lcsc = InverseBurrowsWheelerTransform.getInstance().getnamedcolumns(suffixArrBWT.getBwt(), false);

        Map<String, LastToFirst> mp = new HashMap<String, LastToFirst>();
        List<Integer> indicies = new ArrayList<Integer>();

        for (int i = 0; i < fcsc.getCs().length; i++) {
            mp.put(fcsc.getCs()[i], new LastToFirst(lcsc.getCs()[i], i));
        }

        for (String pattern : patterns) {
            findoccurences(pattern, fcsc, lcsc, mp, indicies, ps);
        }

        return indicies;

    }

    public static void findoccurences(String pattern, SortedColumns fcsc, SortedColumns lcsc,
        Map<String, LastToFirst> mp, List<Integer> indicies, SuffixArrayX ps) {
        // TODO Auto-generated method stub

        int index = pattern.length();

        int maxcharindex = lcsc.getCca()[pattern.charAt(index - 1)];
        int mincharIndex = 1;


        boolean found = true;
        while (index-- > 1 && found) {
            found = false;
            int tempmin = 0;
            int tempmax = 0;
            char c = pattern.charAt(index);
            for (int k = mincharIndex; k <= maxcharindex; k++) {
                String key = String.valueOf(c) + String.valueOf(k);
                LastToFirst symbol = mp.get(key);

                if (symbol.symbol.charAt(0) == pattern.charAt(index - 1)) {
                    int j = Integer.valueOf(symbol.symbol.substring(1));
                    if (tempmin == 0) {
                        tempmin = j;
                    }
                    if (tempmax < j) {
                        tempmax = j;
                    }
                    
                    if (index - 1 == 0) {
                        findandaddsymbol(ps, indicies, symbol, mp);
                    }

                    found = true;
                }
            }

            mincharIndex = tempmin;
            maxcharindex = tempmax;
        }


    }
    
    private static void findandaddsymbol(SuffixArrayX ps, List<Integer> indicies, LastToFirst symbol,
        Map<String, LastToFirst> mp) {
        // TODO Auto-generated method stub
        indicies.add(ps.index(mp.get(symbol.symbol).index));
    }

    private static void findandaddsymbol(PartialSuffixArray ps, List<Integer> indicies, LastToFirst symbol, Map<String, LastToFirst> firstlastmap) {
        // TODO Auto-generated method stub
        
        Map<Integer, Integer> mp = ps.getMp();
        int index = symbol.index;
        int count = 0;
        while (!mp.containsKey(index)) {
            symbol = firstlastmap.get(symbol.symbol);
            index = symbol.index;
            count++;
        }
        
        indicies.add(mp.get(index) + count - 1);
        
    }

    


}
