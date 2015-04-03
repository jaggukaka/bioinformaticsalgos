import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class BWTMatching {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readLine();
        String[] patterns = in.readLine().split(DataCache.SPACE);

        Stopwatch sw = new Stopwatch();
        List<Integer> matches = bwtMatching(s, patterns);

        StringBuffer buffer = new StringBuffer();
        for (int i =0 ; i < matches.size(); i++ ) {
            buffer.append(matches.get(i));
            if (i != matches.size()  -1 )
            buffer.append(DataCache.SPACE);
        }

        Composition.getInstance().copyFile(args[0] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    private static List<Integer> bwtMatching(String lastcolumn, String[] patterns) {

        String firstcolumn = new String(lastcolumn);
        SortedColumns fcsc = InverseBurrowsWheelerTransform.getInstance().getnamedcolumns(firstcolumn, true);
        SortedColumns lcsc = InverseBurrowsWheelerTransform.getInstance().getnamedcolumns(lastcolumn, false);

        Map<String, String> mp = new HashMap<String, String>();
        List<Integer> indicies = new LinkedList<Integer>();

        for (int i = 0; i < fcsc.getCs().length; i++) {
            mp.put(fcsc.getCs()[i], lcsc.getCs()[i]);
        }

        for (String pattern : patterns) {
            findoccurences(pattern, fcsc, lcsc, mp, indicies);
        }

        return indicies;

    }

    private static void findoccurences(String pattern, SortedColumns fcsc, SortedColumns lcsc,
        Map<String, String> mp, List<Integer> indicies) {
        // TODO Auto-generated method stub

        int index = pattern.length();

        int maxcharindex = lcsc.getCca()[pattern.charAt(index - 1)];
        int mincharIndex = 1;

        int topbottom = 0;

        boolean found = true;
        while (index-- > 1 && found) {
            found = false;
            int tempmin = 0;
            int tempmax = 0;
            char c = pattern.charAt(index);
            for (int k = mincharIndex; k <= maxcharindex; k++) {
                String key = String.valueOf(c) + String.valueOf(k);
                String symbol = mp.get(key);

                if (symbol.charAt(0) == pattern.charAt(index - 1)) {
                    int j = Integer.valueOf(symbol.substring(1));
                    if (tempmin == 0) {
                        tempmin = j;
                    }
                    if (tempmax < j) {
                        tempmax = j;
                    }
                    if (index - 1 == 0) {
                        topbottom = tempmax - tempmin + 1;
                    }

                    found = true;
                }
            }

            mincharIndex = tempmin;
            maxcharindex = tempmax;
        }

        indicies.add(topbottom);

    }

}
