import java.io.IOException;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 *
 */
public class BetterBWTMatching {

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

    private static List<Integer> matching(String string, String[] patterns) {
        // TODO Auto-generated method stub
        return null;
    }

}
