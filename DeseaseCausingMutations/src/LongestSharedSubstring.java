import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class LongestSharedSubstring {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String[] str = in.readAllStrings();
        String stree = str[0];
        String squery = str[1];
        if (str[1].length() > str[0].length()) {
            stree = str[1];
            squery = str[0];
        }

        SuffixTree trie = new SuffixTree(stree);

        int max = 0;
        String solution = "";
        for (int i = 0; i < squery.length(); i++) {
            String q = squery.substring(i);
            int index = trie.match(q);

            int done = q.length() - index;
            if (done >= max) {
                solution = q.substring(0, done);
                max = done;
            }
        }

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", solution);
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

}
