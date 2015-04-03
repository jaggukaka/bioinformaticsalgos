import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * Fix this class some problem exists!!!!!
 */
//TODO
public class AffineGapAlignment {

    private static final class AffineGapAlignmentHolder {
        private static final AffineGapAlignment AFFINE_GAP_ALIGNMENT = new AffineGapAlignment();
    }

    private AffineGapAlignment() {

    }

    public static AffineGapAlignment getInstance() {
        return AffineGapAlignmentHolder.AFFINE_GAP_ALIGNMENT;
    }

    private int alignment(String a, String b, int[][] middle, int[][] upper, int[][] lower, int[][] bol,
        int[][] scoringMatrix, int openinggappenalty, int extensionpenalty) {

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                char first = a.charAt(i - 1);
                char second = b.charAt(j - 1);

                int ifr = DataCache.getInstance().getScoringmatrixalphaindex().get(first);
                int ise = DataCache.getInstance().getScoringmatrixalphaindex().get(second);
                int scoreu = scoringMatrix[ifr][ise];

                int k = lower[i - 1][j] - extensionpenalty;
                int l = middle[i - 1][j] - openinggappenalty;
                int lowerij = Math.max(k, l);
                lower[i][j] = lowerij;

                int m = upper[i][j - 1] - extensionpenalty;
                int n = middle[i][j - 1] - openinggappenalty;
                int upperij = Math.max(m, n);
                upper[i][j] = upperij;

                int slmis = middle[i - 1][j - 1] + scoreu;

                if (lowerij > upperij && lowerij >= slmis) {
                    middle[i][j] = lowerij;
                    bol[i][j] = -2;
                } else if (upperij >= lowerij && upperij >= slmis) {
                    middle[i][j] = upperij;
                    bol[i][j] = -3;
                } else if (slmis > upperij && slmis > lowerij) {
                    middle[i][j] = slmis;
                    if (first != second)
                        bol[i][j] = -4;
                    else
                        bol[i][j] = -1;
                }

            }
        }

        return middle[a.length()][b.length()];
    }

    public AlignmentUtil.Solution alignment(String a, String b, int[][] scoringMatrix,
        int openinggappenalty, int extensionpenalty) {
        int[][] middle = new int[a.length() + 1][b.length() + 1];
        int[][] upper = new int[a.length() + 1][b.length() + 1];
        int[][] lower = new int[a.length() + 1][b.length() + 1];
        int[][] bol = new int[a.length() + 1][b.length() + 1];

        for (int j = 0; j <= b.length(); j++) {
            for (int i = 0; i <= a.length(); i++) {
                lower[i][j] = i * -extensionpenalty;
                bol[i][j] = -2;
            }
        }

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                upper[i][j] = j * -extensionpenalty;
                bol[i][j] = -3;
            }
        }

        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        int max = alignment(a, b, middle, upper, lower, bol, scoringMatrix, openinggappenalty, extensionpenalty);
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        AlignmentUtil.getInstance().printsolAlignment(bol, b, a, a.length(), b.length(), buffer, buffer2);
        return new AlignmentUtil.Solution(max, buffer.toString(), buffer2.toString());
    }


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        In in = new In(args[0]);
        int[][] scoringmatrix = new int[20][20];
        // scoring matrix
        int i = 0;
        while (!in.isEmpty()) {

            String line = in.readLine();
            String[] scores = line.split(DataCache.SPACE);

            for (int j = 0, k = 0; j < scores.length; j++) {
                String s = scores[j].trim();
                if (!s.isEmpty()) {
                    scoringmatrix[i][k] = Integer.valueOf(s);
                    k++;
                }
            }
            i++;
        }

        in = new In(args[1]);

        String[] strs = in.readAllStrings();

        Stopwatch sw = new Stopwatch();
        AlignmentUtil.Solution sol = AffineGapAlignment.getInstance().alignment(strs[0], strs[1],
            scoringmatrix, 11, 1);
        

        Composition.getInstance().copyFile(args[1] + "_sol.txt", sol.getReadableSolution());
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

}
