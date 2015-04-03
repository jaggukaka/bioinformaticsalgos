import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class SuffixArrayIndicies {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readString();

        SuffixArray sa = new SuffixArray(s);

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            buffer.append(sa.index(i));
            if (i != s.length() - 1) {
                buffer.append(',' + DataCache.SPACE);
            }
        }

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

}
