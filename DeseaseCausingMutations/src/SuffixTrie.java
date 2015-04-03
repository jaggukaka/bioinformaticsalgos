import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class SuffixTrie extends Trie {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        // LRP
        In in = new In(args[0]);

        String s = in.readString();

        SuffixTrie st = new SuffixTrie(s);

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", st.longestRepeatProblem());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    public SuffixTrie(String text) {
        super();
        for (int i = 0; i < text.length(); i++) {
            Node runner = super.add(text.substring(i));
            runner.limit++;
        }
    }

    public String longestRepeatProblem() {

        LRPSolution lrp = new LRPSolution("", 0);
        lrp(root, lrp, 1, "");

        return lrp.lrp;
    }

    private void lrp(Node node, LRPSolution sol, int level, String s) {
        // TODO Auto-generated method stub
        if (node == null) {
            return;
        }
   
        for (int i = 0; i < node.children.length; i++) {

            String temp = s;
            Node n = node.children[i];
            if (n != null) {
                if (n.limit > 1 && level > sol.maxLevel) {
                    sol.maxLevel = level;
                    sol.lrp = temp + DataCache.getInstance().getGeneIndexNucleotide().get(i);
                }
                temp = temp + DataCache.getInstance().getGeneIndexNucleotide().get(i);
                lrp(n, sol, level + 1, temp);
            }

        }
    }

    private static final class LRPSolution {
        String lrp;

        int maxLevel;

        public LRPSolution(String lrp, int maxLevel) {
            super();
            this.lrp = lrp;
            this.maxLevel = maxLevel;
        }

    }

}
