import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class Trie {

    protected final Node root;

    protected int nodeCounter;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String[] str = in.readAllStrings();

        Trie trie = new Trie();
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            trie.add(s);
        }

        List<String> sol = trie.printTrie();

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", sol);
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    private List<String> printTrie() {
        // TODO Auto-generated method stub
        List<String> ls = new LinkedList<String>();
        printTrie(root, ls);

        return ls;
    }

    private void printTrie(Node runner, List<String> ls) {
        // TODO Auto-generated method stub

        for (int i = 0; i < runner.children.length; i++) {
            Node n = runner.children[i];
            if (n != null) {
                ls.add(runner.val + " " + n.val + " " + DataCache.getInstance().getGeneIndexNucleotide().get(i));
                printTrie(n, ls);
            }
        }

    }

    public List<Integer> trieMatching(String text) {

        List<Integer> ls = new LinkedList<Integer>();
        for (int i = 0; i < text.length(); i++) {
            trieMatching(text.substring(i), ls, i);
        }

        return ls;
    }

    private void trieMatching(String text, List<Integer> ls, int offset) {
        // TODO Auto-generated method stub
        Node runner = root;

        for (char c : text.toCharArray()) {
            int index = DataCache.getInstance().getGeneNucleotideIndex().get(c);
            Node temp = runner.children[index];

            if (temp == null) {
                return;
            }

            if (temp.leaf) {
                ls.add(offset);
            }

            runner = temp;
        }

    }

    public Trie() {
        root = new Node(++nodeCounter);
    }

    public Node add(String pattern) {

        Node runner = root;

        for (char c : pattern.toCharArray()) {
            int index = DataCache.getInstance().getGeneNucleotideIndex().get(c);
            Node temp = runner.children[index];
            if (temp == null) {
                temp = new Node(++nodeCounter);
                runner.children[index] = temp;
                runner.leaf = false;
                runner.limit++;
            }

            runner = temp;
        }
        
        return runner;
    }

    protected static class Node {

        protected boolean leaf;
        
        protected int limit;

        protected Node[] children = new Node[4];

        protected final int val;

        public Node(int val) {
            this.val = val;
            this.leaf = true;
            this.limit = 0;
        }

    }

}
