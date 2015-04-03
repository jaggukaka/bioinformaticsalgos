import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 *
 */
public class LongestRepeatedSubstring {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub


        // LRP
        In in = new In(args[0]);

        String s = in.readString();

        SuffixTree st = new SuffixTree(s);

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", st.longestRepeatProblem());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    

    }

}
