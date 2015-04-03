import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class InverseBurrowsWheelerTransform {
    
    
    private InverseBurrowsWheelerTransform() {

    }

    private static final class InverseBurrowsWheelerTransformHolder {
        private static final InverseBurrowsWheelerTransform INVERSE_BURROWS_WHEELER_TRANSFORM = new InverseBurrowsWheelerTransform();

    }

    public static InverseBurrowsWheelerTransform getInstance() {
        return InverseBurrowsWheelerTransformHolder.INVERSE_BURROWS_WHEELER_TRANSFORM;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readString();

        String bwt = InverseBurrowsWheelerTransform.getInstance().inverseBurrowsWheelerTransform(s);

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", bwt);
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    private String inverseBurrowsWheelerTransform(String lastcolumn) {

        String firstcolumn = new String(lastcolumn);
        String[] fcs = getnamedcolumns(firstcolumn, true).getCs();
        String[] lcs = getnamedcolumns(lastcolumn, false).getCs();
        Map<String, String> mp = new HashMap<String, String>();

        for (int i = 0; i < lcs.length; i++) {
            mp.put(lcs[i], fcs[i]);
        }

        StringBuffer genomeBuffer = new StringBuffer();
        
        String runner = "$1";
        int k = fcs.length;
        
        while (k-- > 0) {
            String val = mp.get(runner);
            genomeBuffer.append(val.charAt(0));
            runner = val;
        }
        
        return genomeBuffer.toString();
    }

    public SortedColumns getnamedcolumns(String column, boolean isfirst) {
        char[] cca = new char[256];

        char[] ca = column.toCharArray();
        if (isfirst) {
            Arrays.sort(ca);
        }

        String[] cs = new String[column.length()];

        for (int i = 0; i < ca.length; i++) {
            char c = ca[i];
            int j = ++cca[c];
            String key = String.valueOf(c) + String.valueOf(j);
            cs[i] = key;

        }
        return new SortedColumns(cca, cs);
    }
    
    
    
}
