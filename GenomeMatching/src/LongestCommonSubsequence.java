import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class LongestCommonSubsequence {

    private LongestCommonSubsequence() {

    }

    private static final class LongestCommonSubsequenceHolder {
        private static final LongestCommonSubsequence COUNTING_PEPTIDE = new LongestCommonSubsequence();

    }

    public static LongestCommonSubsequence getInstance() {
        return LongestCommonSubsequenceHolder.COUNTING_PEPTIDE;
    }

    private void lcs(String a, String b) {
        int[][] sol = new int[a.length() + 1][b.length() + 1];
        int[][] bol = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            sol[i][0] = 0;
            bol[i][0] = 0;
        }
        for (int j = 0; j <= b.length(); j++) {
            bol[0][j] = 0;
        }

        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        System.out.println(lcsI(a, b, sol, bol));
        printsol(sol, bol, a, a.length(), b.length());
    }

    private void printsol(int[][] sol, int[][] bol, String a, int i, int j) {
        // TODO Auto-generated method stub
        if (i < 0 || j < 0) {
            return;
        }

        if (bol[i][j] == -1) {
            printsol(sol, bol, a, i - 1, j - 1);
            System.out.print(a.charAt(i));
        } else if (bol[i][j] == -2) {
            printsol(sol, bol, a, i - 1, j);
        } else if (bol[i][j] == -3) {
            printsol(sol, bol, a, i, j - 1);
        }

    }

    private int lcs(String a, String b, int[][] sol, int[][] bol, int i, int j) {
        // TODO Auto-generated method stub
        if (i == 0 || j == 0) {
            return 0;
        }
        if (a.charAt(i) == b.charAt(j)) {
            sol[i][j] = lcs(a, b, sol, bol, i - 1, j - 1) + 1;
            bol[i][j] = -1;
        } else {
            int k = lcs(a, b, sol, bol, i - 1, j);
            int l = lcs(a, b, sol, bol, i, j - 1);

            if (k > l) {
                sol[i][j] = k;
                bol[i][j] = -2;
            } else {
                sol[i][j] = l;
                bol[i][j] = -3;
            }
        }

        return sol[i][j];

    }

    private int lcsI(String a, String b, int[][] sol, int[][] bol) {

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                char first = a.charAt(i - 1);
                char second = b.charAt(j - 1);

                if (first == second) {
                    sol[i][j] = sol[i - 1][j - 1] + 1;
                    bol[i][j] = -1;
                } else {

                    int k = sol[i - 1][j];
                    int l = sol[i][j - 1];

                    if (k > l) {
                        sol[i][j] = k;
                        bol[i][j] = -2;
                    } else {
                        sol[i][j] = l;
                        bol[i][j] = -3;
                    }
                }
            }
        }

        return sol[a.length()][b.length()];
    }

    /*
     * ####################################3########################################################
     * 
     * LCS longest Alignment
     */

    /**************************************************************************************
     * 
     * @param a
     * @param b
     * @param sol
     * @param bol
     * @param scoringMatrix
     * @param indelpenalty
     * @return
     */

    private int lcsAlignment(String a, String b, int[][] sol, int[][] bol, int[][] scoringMatrix, int indelpenalty) {

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

    public AlignmentUtil.Solution lcsGlobalAlignment(String a, String b, int[][] scoringMatrix, int indelpenalty) {
        int[][] sol = new int[a.length() + 1][b.length() + 1];
        int[][] bol = new int[a.length() + 1][b.length() + 1];
        int k = -indelpenalty;
        for (int i = 0; i <= a.length(); i++) {
            sol[i][0] = (i) * k;
            bol[i][0] = -2;
        }
        for (int j = 0; j <= b.length(); j++) {
            bol[0][j] = -3;
            sol[0][j] = (j) * k;
        }

        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        int len = lcsAlignment(a, b, sol, bol, scoringMatrix, indelpenalty);
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        AlignmentUtil.getInstance().printsolAlignment(bol, b, a, a.length(), b.length(), buffer, buffer2);
        return new AlignmentUtil.Solution(len, buffer.toString(), buffer2.toString());
    }

    /*
     * ########################################################## Local Alignment
     * ##############################################
     */

    private AlignmentUtil.Max lcsAlignmentLocal(String a, String b, int[][] sol, int[][] bol,
        int[][] scoringMatrix, int indelpenalty) {

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

                if (slk > sll && slk >= slmis && slk > 0) {
                    sol[i][j] = slk;
                    bol[i][j] = -2;
                } else if (sll >= slk && sll >= slmis && sll > 0) {
                    sol[i][j] = sll;
                    bol[i][j] = -3;
                } else if (slmis > sll && slmis > slk && slmis > 0) {
                    sol[i][j] = slmis;
                    if (first != second)
                        bol[i][j] = -4;
                    else
                        bol[i][j] = -1;
                } else {
                    sol[i][j] = 0;
                    bol[i][j] = -5;
                }

                if (max.getVal() <= sol[i][j]) {
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

    public AlignmentUtil.Solution lcsLocalAlignment(String a, String b, int[][] scoringMatrix, int indelpenalty) {
        int[][] sol = new int[a.length() + 1][b.length() + 1];
        int[][] bol = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                sol[i][j] = 0;
                bol[i][j] = -5;
            }
        }

        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        AlignmentUtil.Max max = lcsAlignmentLocal(a, b, sol, bol, scoringMatrix, indelpenalty);
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

        // lcs("CAATAATCGTCCCATCTCAATGATCATGCTTTATCTTCATAAAAGTTTGCCAGTGCGGAGGATCTTCGGCGTAGTTTGATCTGACTAAATTAATTGAGTTGTGAAAGACGAGAGATAAAACGAAGCCCAATCGCTTATAGATACTACTGGGCTTCGGAACGTCGATACGACTGTATGGTAAAGTTGCATTAACATTGGAGCGAAACCACGAAGTCTCGGGCCTTCAATGTTCAGCGTCGTTAGACTGCGTATCTCCCTCCGATACACCGTACACAACGCGGGCCGGTCCGCATTCCCTCTGGAACCAGTCGGGTTACCCGAGTATTACGACCCCGTGAGATTAGATCTGCTGGGATGGAGCTTTTCAACACCGTCATGCCCAAGGAGATACCTTCGACTTCGTTCATGGTTAATATAGGGCAGGATACCGGTCTATGCCTAGGGCATACATCTTAGGGCAAGTATCTATCTTCCCGGGATTACCCGGATCCGACGTTATTTATTTTACTCAGCGCCCTGTACCTTCCCGACTCTTGCCAGATCACTCACACTACTGCCATGACCTAACTTTACACCCTAATGGTCAAGCAGCCCGCTGGGAGGTCTCACCCGGAATAGTATAAATACCCACTTCGAGAGCAACTTCTAACACCAAGCTATGCTGCGTATGAGGGGAAAAGAGGTTAGAGGGCCTCGTCAATTGATCGTCACTCATAATGCGTCATGCAGAAATGCCCCATAGGCAGTACGGCTGTCCCTATAACGCACTGGCATATTGGCGCTATACACGCCTGAAAGTACACATGCTGACGTAGTGCATGGCCTCCCATTCAGCTAGTTTGGTTTATGCGATTGTTACTGTTGCATTATAACCAACAATTCATCGTTAGCATATCTGACGTTGCCCTTTTCGGAGGGCAGGCACGTATCTGAGGAACTTACGCATCATCACGTGCCGGCAATGAGGAGAGGTTCGTAGCTA",
        // "GTGGTGCACGCGCAACCCTAACTAACCAAGCGAACCAAAGACCATGTGCGATAACCGTATCGACAATACCATTTAATTCCACCTTTACAAGGTCGTCCTGGAAGCTATTGTGTCCTCCTACTGTCTTCTGGCACGCCGCTAGACCACTGCGCCTCACAGCGCCGGTTCGCATGCGCACGTTAGGACCCGACCAGTTTGATCGTCCACAGGCAAGAGAATTCGTCGATGACTGCAAGTGCGGATCGGGTCGACTAATTAAGGTGTATGGGCGTGCCCTCACGTCTATTCCTGTGCCACTGCACGATCGATAGATCTAGCTGTACATGGACAGTCGACTACTGAGACAGGCACAGGTTCCTCATCACCTTTGTCTTTTACGGACGCCGATGCCTCGGTCTTGGGCAGCTGTTGCGCACTGAGGTAGGGCGGAAACGGTATGATCAATCATTAACAGGGCGAGTACTCCCGTCGCGGGGGGTTCGCTGAGGGGTACACAGAGTGGAAACGACTCGCCCGCAGGTGTCCGGAGAGAGGACCCGTTGCCCATAGCACGTGAATAAGAAACGGATTCGACAACGAGAAATAAAGTTATTATGCCGATGTGACTGATGATGCGGCTGCACAACCCTTAGAAGACTACGTATAGTACTGCCATGACGTATATAACTGACATTATATCCATGATGCGCCCCAAGCATGCGCTATATCTATTGTGACATACTCGCCGTGGAGCAGTGGGGTCAAGAATACACAGTGGTGGTTGATCGATTATTTCACAATTGAACCGACCCGGGAGACGACATTGGGACTTCAAGCGATTGGCACACATGAAATCAATTAGGCCGCTACGCCCCTGAAAGAAAGGCGAGGGACAGTTAACCACCCATTGCGGATTTCGCAAGGAGTTGTCTCGATCGAGCGGCATATCAGGTTCCCCTGCAAGGGGTGTA");

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

        // Solution sol = LongestCommonSubsequence.getInstance().lcsLongest(strs[0], strs[1], scoringmatrix, 5);
        AlignmentUtil.Solution sol = LongestCommonSubsequence.getInstance().lcsGlobalAlignment(strs[0], strs[1],
            scoringmatrix, 5);

        Composition.getInstance().copyFile(args[1] + "_sol.txt", sol.getReadableSolution());
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

}
