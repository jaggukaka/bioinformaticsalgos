import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class SequenceAlignment {

    private SequenceAlignment() {

    }

    private static final class SequenceAlignmentHolder {
        private static final SequenceAlignment SEQUENCE_ALIGNMENT = new SequenceAlignment();

    }

    public static SequenceAlignment getInstance() {
        return SequenceAlignmentHolder.SEQUENCE_ALIGNMENT;
    }

    private int lcsAlignment(String a, String b, int[][] sol, int[][] bol) {
        

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                char first = a.charAt(i - 1);
                char second = b.charAt(j - 1);

                int k = sol[i - 1][j] - 1;
                int l = sol[i][j - 1] - 1;
                int m = sol[i - 1][j - 1];

                int slk = k;
                int sll = l;
                int slmis = m;

                if (first != second) {
                    slmis = m -1;
                }

                if (slk > sll && slk >= slmis) {
                    sol[i][j] = slk;
                    bol[i][j] = -2;
                } else if (sll >= slk && sll >= slmis) {
                    sol[i][j] = sll;
                    bol[i][j] = -3;
                } else if (slmis > sll && slmis > slk) {
                    sol[i][j] = slmis;
                    if (first != second)
                        bol[i][j] = -4;
                    else
                        bol[i][j] = -1;
                }

            }
        }

        return sol[a.length()][b.length()];
    }

    private int lcsLongest(String a, String b) {
        int[][] sol = new int[a.length() + 1][b.length() + 1];
        int[][] bol = new int[a.length() + 1][b.length() + 1];
        
        for (int i = 0; i <= a.length(); i++) {
            sol[i][0] = i*-1;
            bol[i][0] = -2;
        }
        for (int j = 0; j <= b.length(); j++) {
            sol[0][j] = j*-1;
            bol[0][j] = -3;
        }
        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        lcsAlignment(a, b, sol, bol);
        int len = printsolAlignment(sol, bol, b, a, a.length(), b.length(), 0);
        return len;
    }
    
    private static int printsolAlignment(int[][] sol, int[][] bol, String b, String a, int i, int j, int changes) {
        // TODO Auto-generated method stub
        if ((bol[i][j] == -1 || bol[i][j] == -4)) {
            if (j > 0 && i > 0 && a.charAt(i-1) != b.charAt(j -1)) {
                ++changes;
            }
            if (i > 0 && j > 0) {
                changes  = printsolAlignment(sol, bol, b, a, i - 1, j - 1, changes);
            }
            
              
        } else if (bol[i][j] == -2 && i > 0) {

            changes = printsolAlignment(sol, bol, b, a, i - 1, j, ++changes);
 
        } else if (bol[i][j] == -3 && j > 0) {

            changes = printsolAlignment(sol, bol, b, a, i, j - 1, ++changes);

        }
        
        return changes;

    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        In in = new In(args[0]);
        String[] strs = in.readAllStrings();

        Stopwatch sw = new Stopwatch();

        String len = String.valueOf(SequenceAlignment.getInstance().lcsLongest(strs[0], strs[1]));

        Composition.getInstance().copyFile(args[0] + "_sol.txt", len);
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

}
