import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class MiddleEdge {

    private static final class MiddleEdgeHolder {
        private static final MiddleEdge AFFINE_GAP_ALIGNMENT = new MiddleEdge();
    }

    private MiddleEdge() {

    }

    public static MiddleEdge getInstance() {
        return MiddleEdgeHolder.AFFINE_GAP_ALIGNMENT;
    }

    private LinkedList<int[]> middleEdge(String a, String b, int[] solarr, int[][] scoringMatrix,
        int indelpenalty, int limit) {

        int[] secondarr = solarr;
        LinkedList<int[]> ls = new LinkedList<int[]>();
        ls.addFirst(secondarr);

        for (int j = 1; j <= limit; j++) {

            int[] firstarr = new int[solarr.length];

            firstarr[0] = j * -indelpenalty;
            ls.addFirst(firstarr);

            for (int i = 1; i <= a.length(); i++) {

                char first = a.charAt(i - 1);
                char second = b.charAt(j - 1);

                int ifr = DataCache.getInstance().getScoringmatrixalphaindex().get(first);
                int ise = DataCache.getInstance().getScoringmatrixalphaindex().get(second);
                int scoreu = scoringMatrix[ifr][ise];

                int k = firstarr[i - 1];
                int l = ls.getLast()[i];
                int m = ls.getLast()[i - 1];

                int slk = k - indelpenalty;
                int sll = l - indelpenalty;
                int slmis = m + scoreu;

                if (slk > sll && slk >= slmis) {
                    firstarr[i] = slk;
                } else if (sll >= slk && sll >= slmis) {
                    firstarr[i] = sll;
                } else if (slmis > sll && slmis > slk) {
                    firstarr[i] = slmis;
                }

            }

            if (j != limit)
                ls.removeLast();

        }

        return ls;
    }

    public AlignmentUtil.Max[] middleEdge(String a, String b, int[][] scoringMatrix, int indelpenalty) {

        int[] solarr = new int[a.length() + 1];
        int k = -indelpenalty;
        for (int i = 0; i <= a.length(); i++) {
            solarr[i] = (i) * k;
        }

        // lcs(a, b, sol, bol, a.length() - 1, b.length() -1);
        
        LinkedList<int[]> ls = middleEdge(a, b, solarr, scoringMatrix, indelpenalty, b.length() / 2 + 1);
        LinkedList<int[]> ls2 = middleEdge(AlignmentUtil.getInstance().reverse(a), AlignmentUtil.getInstance()
            .reverse(b), solarr, scoringMatrix, indelpenalty, b.length() / 2  );
        
        
        int maxIndexright = getMaxIndex(ls.getFirst(), ls2.getLast(), 0);
        int maxIndexleft = getMaxIndex(ls.getLast(), ls2.getFirst(), maxIndexright);
        

        AlignmentUtil.Max max1 = new AlignmentUtil.Max(maxIndexleft, b.length()/2, 0);
        AlignmentUtil.Max max2 = new AlignmentUtil.Max(maxIndexright, b.length()/2 + 1, 0);

        return new AlignmentUtil.Max[] {max1, max2};
    }

    private int getMaxIndex(int[] first, int[] last, int j) {
        // TODO Auto-generated method stub
        int max = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < first.length; i++) {
            int val = first[i] + last[first.length - i - 1];
            if (max < val && ((j!=0 && (i+1 ==j || i == j)) || j==0) ) {
                System.out.println(i + ":" + val);
                max = val;
                index = i;
            }
        }
        return index;
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

        // Solution sol = LongestCommonSubsequence.getInstance().lcsLongest(strs[0], strs[1], scoringmatrix, 5);
        AlignmentUtil.Max[] sol = MiddleEdge.getInstance().middleEdge(strs[0], strs[1], scoringmatrix,
            5);
        StringBuffer buffer = new StringBuffer();
        buffer.append("(").append(sol[0].getI()).append(",").append(DataCache.SPACE).append(sol[0].getJ()).append(")");
        buffer.append(DataCache.SPACE);
        buffer.append("(").append(sol[1].getI()).append(",").append(DataCache.SPACE).append(sol[1].getJ()).append(")");

        Composition.getInstance().copyFile(args[1] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

}
