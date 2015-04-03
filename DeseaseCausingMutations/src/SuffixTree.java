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
public class SuffixTree {

    protected final Node root;

    protected int nodeCounter;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readString();

        Stopwatch sw = new Stopwatch();
        SuffixTree st = new SuffixTree(s);

        Composition.getInstance().copyFile(args[0] + "_sol.txt", st.printTree());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    public SuffixTree(String s) {
        root = new Node(++nodeCounter, "");
        for (int i = 0; i < s.length(); i++) {
            add(s.substring(i));
        }
    }

    private String printTree() {
        // TODO Auto-generated method stub
        List<String> ls = new LinkedList<String>();
        printTree(root, ls);
        
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < ls.size(); i++) {
            buffer.append(ls.get(i));
            if (i != ls.size() - 1) {
                buffer.append('\n');
            }
        }

        return buffer.toString();
    }

    private void printTree(Node runner, List<String> ls) {
        // TODO Auto-generated method stub

        if (runner.ls == null) {
            return;
        }
        for (Node n : runner.ls) {
            if (n != null) {
                ls.add(n.data);
                printTree(n, ls);
            }
        }

    }

    public int match(String pattern) {

        Node runner = root;

        int index = pattern.length();

        while (index > 0 && runner.ls !=null) {
            boolean foundmatch = false;
            for (Node n : runner.ls) {
                int lcp = lcp(pattern, n.data);
                if (lcp > 0) {
                    foundmatch = true;
                    runner = n;
                    index = index - lcp;
                    pattern = pattern.substring(lcp);
                    if (lcp == n.data.length()) {
                        break;
                    } else {
                        return index;
                    }
                }
            }

            if (!foundmatch) {
                return index;
            }
        }

        return index;
    }

    public Node add(String pattern) {

        Node runner = root;

        int len = pattern.length() - 1;
        int index = len;

        while (index >= 0) {
            LinkedList<Node> ls = runner.ls;
            if (ls == null) {
                ls = new LinkedList<Node>();
                Node temp = new Node(++nodeCounter, pattern);
                ls.add(temp);
                runner.ls = ls;
                runner.leaf = false;
                return temp;
            } else {
                boolean foundmatch = false;
                for (Node n : ls) {
                    int lcp = lcp(pattern, n.data);
                    if (lcp > 0) {
                        foundmatch = true;
                        runner = n;
                        index = len - lcp;
                        pattern = pattern.substring(lcp);
                        if (lcp == n.data.length()) {
                            break;
                        } else {
                            String ntemp = n.data.substring(0, lcp);
                            Node split = new Node(++nodeCounter, n.data.substring(lcp));
                            n.data = ntemp;
                            LinkedList<Node> nls = new LinkedList<Node>();
                            Node temp = new Node(++nodeCounter, pattern);
                            nls.add(temp);
                            nls.add(split);
                            if (n.ls == null) {
                                n.ls = nls;
                                n.leaf = false;
                            } else {
                                split.ls = n.ls;
                                n.ls = nls;
                                split.leaf = false;
                            }
                            return temp;
                        }
                    }
                }

                if (!foundmatch) {
                    Node temp = new Node(++nodeCounter, pattern);
                    ls.add(temp);
                    return temp;
                }
            }
        }

        return runner;
    }

    // longest common prefix of s and t
    private static int lcp(String s, String t) {
        int N = Math.min(s.length(), t.length());
        for (int i = 0; i < N; i++) {
            if (s.charAt(i) != t.charAt(i))
                return i;
        }
        return N;
    }
    
    public String longestRepeatProblem() {

        LRPSolution lrp = new LRPSolution("", 0);
        lrp(root, lrp, "");

        return lrp.lrp;
    }

    private void lrp(Node node, LRPSolution sol,  String s) {
        // TODO Auto-generated method stub
        if (node == null || node.ls == null) {
            return;
        }
   
        for (Node n : node.ls) {

            String temp = s;
            if (n != null && n.ls != null) {
                String data = n.data;
                if ( n.ls.size() > 1 && s.length() + data.length() > sol.maxLevel) {
                    sol.maxLevel = s.length() + data.length();
                    sol.lrp = temp + n.data;
                }
                temp = temp + n.data;
                lrp(n, sol,  temp);
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

    protected static class Node {

        protected boolean leaf;

        protected LinkedList<Node> ls;

        protected final int val;

        protected String data;

        public Node(int val, String data) {
            this.val = val;
            this.leaf = true;
            this.data = data;
        }

    }

}
