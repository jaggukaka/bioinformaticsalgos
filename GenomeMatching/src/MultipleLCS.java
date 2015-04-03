import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class MultipleLCS {

    private MultipleLCS() {

    }

    private static final class MultipleLCSHolder {
        private static final MultipleLCS COUNTING_PEPTIDE = new MultipleLCS();

    }

    public static MultipleLCS getInstance() {
        return MultipleLCSHolder.COUNTING_PEPTIDE;
    }

    private AlignmentUtil.Solution multiplelcs(String a, String b, String c) {
        int[][][] sol = new int[a.length() + 1][b.length() + 1][c.length() + 1];
        int[][][] bol = new int[a.length() + 1][b.length() + 1][c.length() + 1];

        for (int i = 0; i < a.length(); i++) {
            bol[i][0][0] = -3;
            if (i > 0) {
                for (int j = 1; j < b.length(); j++) {
                    bol[i][j][0] = -6;
                }
            }
        }
        
        for (int i = 1; i < a.length(); i++) {
            if (i > 0) {
                for (int k = 1; k < c.length(); k++) {
                    bol[i][0][k] = -8;
                }
            }
        }
        
        for (int j = 0; j < b.length(); j++) {
            bol[0][j][0] = -4;
                for (int k = 1; k < c.length(); k++) {
                    if (j > 0)
                    bol[0][j][k] = -7;
                    else 
                    bol[0][0][k] = -5;
                }
        }

        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        int val = lcsI(a, b, c, sol, bol);
        StringBuffer b1 = new StringBuffer();
        StringBuffer b2 = new StringBuffer();
        StringBuffer b3 = new StringBuffer();

        printsolAlignment(bol, a, b, c, a.length(), b.length(), c.length(), b1, b2, b3);

        AlignmentUtil.Solution solution = new AlignmentUtil.Solution(val, b1.toString(), b2.toString(),
            b3.toString());
        return solution;
    }

    private void printsolAlignment(int[][][] bol, String a, String b, String c, int i, int j, int k,
        StringBuffer b1, StringBuffer b2, StringBuffer b3) {
        // TODO Auto-generated method stub

        if ((bol[i][j][k] == -1 || bol[i][j][k] == -2)) {
            if (i > 0 && j > 0 && k - 1 > 0) {
                printsolAlignment(bol, a, b, c, i - 1, j - 1, k - 1, b1, b2, b3);
            }
            if (i > 0)
                b1.append(a.charAt(i - 1));
            if (j > 0)
                b2.append(b.charAt(j - 1));
            if (k > 0)
                b3.append(c.charAt(k - 1));
        } else if (bol[i][j][k] == -3 && i > 0) {

            printsolAlignment(bol, a, b, c, i - 1, j, k, b1, b2, b3);
            b1.append(a.charAt(i - 1));
            b2.append(DataCache.HYPEN);
            b3.append(DataCache.HYPEN);
        } else if (bol[i][j][k] == -4 && j > 0) {

            printsolAlignment(bol, a, b, c, i, j - 1, k, b1, b2, b3);
            b1.append(DataCache.HYPEN);
            b2.append(b.charAt(j - 1));
            b3.append(DataCache.HYPEN);
        } else if (bol[i][j][k] == -5 && k > 0) {

            printsolAlignment(bol, a, b, c, i, j, k - 1, b1, b2, b3);
            b1.append(DataCache.HYPEN);
            b2.append(DataCache.HYPEN);
            b3.append(c.charAt(k - 1));
        } else if (bol[i][j][k] == -6 && i > 0 && j > 0) {

            printsolAlignment(bol, a, b, c, i - 1, j - 1, k, b1, b2, b3);
            b1.append(a.charAt(i - 1));
            b2.append(b.charAt(j - 1));
            b3.append(DataCache.HYPEN);
        } else if (bol[i][j][k] == -7 && j > 0 && k > 0) {

            printsolAlignment(bol, a, b, c, i, j - 1, k - 1, b1, b2, b3);
            b1.append(DataCache.HYPEN);
            b2.append(b.charAt(j - 1));
            b3.append(c.charAt(k - 1));
        } else if (bol[i][j][k] == -8 && i > 0 && k > 0) {

            printsolAlignment(bol, a, b, c, i - 1, j, k - 1, b1, b2, b3);
            b1.append(a.charAt(i - 1));
            b2.append(DataCache.HYPEN);
            b3.append(c.charAt(k - 1));
        }
    }

    private int lcsI(String a, String b, String c, int[][][] sol, int[][][] bol) {

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                for (int k = 1; k <= c.length(); k++) {

                    char first = a.charAt(i - 1);
                    char second = b.charAt(j - 1);
                    char third = c.charAt(k - 1);

                    int p = sol[i - 1][j][k];
                    int q = sol[i][j - 1][k];
                    int r = sol[i][j][k - 1];
                    int s = sol[i - 1][j - 1][k];
                    int t = sol[i][j - 1][k - 1];
                    int u = sol[i - 1][j][k - 1];
                    int v = sol[i - 1][j - 1][k - 1];

                    int index = AlignmentUtil.getInstance().max(p, q, r, s, t, u, v);

                    if (index == 0) {
                        sol[i][j][k] = p;
                        bol[i][j][k] = -3;
                    } else if (index == 1) {
                        sol[i][j][k] = q;
                        bol[i][j][k] = -4;
                    } else if (index == 2) {
                        sol[i][j][k] = r;
                        bol[i][j][k] = -5;
                    } else if (index == 3) {
                        sol[i][j][k] = s;
                        bol[i][j][k] = -6;
                    } else if (index == 4) {
                        sol[i][j][k] = t;
                        bol[i][j][k] = -7;
                    } else if (index == 5) {
                        sol[i][j][k] = u;
                        bol[i][j][k] = -8;
                    } else if (index == 6) {
                        if (first == second && first == third) {
                            sol[i][j][k] = v + 1;
                            bol[i][j][k] = -1;
                        } else {
                            sol[i][j][k] = v;
                            bol[i][j][k] = -2;
                        }

                    }

                }
            }
        }

        return sol[a.length()][b.length()][c.length()];

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

        // Solution sol = LongestCommonSubsequence.getInstance().lcsLongest(strs[0], strs[1], scoringmatrix, 5);
        AlignmentUtil.Solution sol = MultipleLCS.getInstance().multiplelcs(strs[0], strs[1], strs[2]);

        Composition.getInstance().copyFile(args[0] + "_sol.txt", sol.getReadableSolution());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

}
