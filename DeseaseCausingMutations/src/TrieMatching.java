import java.io.IOException;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 *
 */
public class TrieMatching {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String[] str = in.readAllStrings();

        Trie trie = new Trie();
        for (int i = 1; i < str.length; i++) {
            String s = str[i];
            trie.add(s);
        }

        List<Integer> sol = trie.trieMatching(str[0]);

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", sol, DataCache.SPACE);
        System.out.println("took " + sw.elapsedTime() + " seconds");

    
    }

}
