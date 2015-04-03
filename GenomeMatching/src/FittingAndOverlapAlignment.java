import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class FittingAndOverlapAlignment {

    private static final class FittingAlignmentHolder {
        private static final FittingAndOverlapAlignment FITTING_AND_OVERLAP_ALIGNMENT = new FittingAndOverlapAlignment();
    }

    private FittingAndOverlapAlignment() {

    }

    public static FittingAndOverlapAlignment getInstance() {
        return FittingAlignmentHolder.FITTING_AND_OVERLAP_ALIGNMENT;
    }

    /*
     * ########################################################## Local Alignment
     * ##############################################
     */

    private AlignmentUtil.Max alignment(String a, String b, int[][] sol, int[][] bol, int[][] scoringMatrix,
        int indelpenalty, boolean isFitting) {

        AlignmentUtil.Max max = new AlignmentUtil.Max(0, 0, 0);

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                char first = a.charAt(i - 1);
                char second = b.charAt(j - 1);

                int ifr = DataCache.getInstance().getScoringmatrixalphaindex().get(first);
                int ise = DataCache.getInstance().getScoringmatrixalphaindex().get(second);
                int scoreu = scoringMatrix[ifr][ise];

                int k = sol[i - 1][j];
                int l = sol[i][j - 1];
                int m = sol[i - 1][j - 1];

                int slk = k - indelpenalty;
                int sll = l - indelpenalty;
                int slmis = m + scoreu;

                if (slk >= sll && slk >= slmis) {
                    sol[i][j] = slk;
                    bol[i][j] = -2;
                } else if (sll > slk && sll >= slmis) {
                    sol[i][j] = sll;
                    bol[i][j] = -3;
                } else if (slmis > sll && slmis > slk) {
                    sol[i][j] = slmis;
                    if (first != second)
                        bol[i][j] = -4;
                    else
                        bol[i][j] = -1;
                }

                boolean alignmentCondition = isFitting ? (i >= b.length() && j == b.length()) : i == a.length();

                if (max.getVal() <= sol[i][j] && alignmentCondition) {
                    max.setVal(sol[i][j]);
                    max.setI(i);
                    max.setJ(j);
                }

                if (i == a.length() && j == b.length()) {
                    if (sol[i][j] < max.getVal()) {
                        sol[i][j] = max.getVal();
                    }
                }

            }
        }

        return max;
    }

    public AlignmentUtil.Solution alignment(String a, String b, int[][] scoringMatrix, int indelpenalty,
        boolean isFitting) {
        int[][] sol = new int[a.length() + 1][b.length() + 1];
        int[][] bol = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            sol[i][0] = 0;
            bol[i][0] = -5;
        }

        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        AlignmentUtil.Max max = alignment(a, b, sol, bol, scoringMatrix, indelpenalty, isFitting);
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        AlignmentUtil.getInstance().printsolAlignment(bol, b, a, max.getI(), max.getJ(), buffer, buffer2);
        return new AlignmentUtil.Solution(max.getVal(), buffer.toString(), buffer2.toString());
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        int[][] scoringmatrix = new int[20][20];
        // scoring matrix

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (i != j) {
                    scoringmatrix[i][j] = -2; // for fitting put -1;
                } else {
                    scoringmatrix[i][j] = 1;
                }
            }
        }

        In in = new In(args[0]);

        String[] strs = in.readAllStrings();

        Stopwatch sw = new Stopwatch();

        // Solution sol = LongestCommonSubsequence.getInstance().lcsLongest(strs[0], strs[1], scoringmatrix, 5);
        // Fitting
        // AlignmentUtil.Solution sol = FittingAndOverlapAlignment.getInstance().alignment(strs[0], strs[1],
        // scoringmatrix, 1, true);
        // OVerlap
        AlignmentUtil.Solution sol = FittingAndOverlapAlignment.getInstance().alignment(strs[0], strs[1],
            scoringmatrix, 2, false);

        Composition.getInstance().copyFile(args[0] + "_sol.txt", sol.getReadableSolution());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

}
