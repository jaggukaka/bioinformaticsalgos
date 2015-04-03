import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class ManhattanTourist {

    private ManhattanTourist() {

    }

    private static final class ManhattanTouristHolder {
        private static final ManhattanTourist COUNTING_PEPTIDE = new ManhattanTourist();

    }

    public static ManhattanTourist getInstance() {
        return ManhattanTouristHolder.COUNTING_PEPTIDE;
    }

    public int manhattanTourist(int n, int m, int[][] down, int[][] right) {
        int[][] s = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            s[i][0] = s[i - 1][0] + down[i -1][0];
        }

        for (int j = 1; j <= m; j++) {
            s[0][j] = s[0][j - 1] + right[0][j - 1];
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                s[i][j] = Math.max(s[i - 1][j] + down[i-1][j], s[i][j - 1] + right[i][j-1]);
            }
        }

        return s[n][m];

    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub

        In in = new In(args[0]);

        // Solve overlap
        // String[] dnalist = in.readAllStrings();

        // solve debrujin

        int n = Integer.parseInt(in.readLine().trim());
        int m = Integer.parseInt(in.readLine().trim());
        int[][] down = new int[n][m + 1];
        int[][] right = new int[n + 1][m];

        for (int l = 0; l < n; l++) {
            String str = in.readLine();
            String[] st = str.split(DataCache.SPACE);

            int[] arr = new int[m + 1];

            for (int i = 0; i < st.length; i++) {
                String s = st[i].trim();
                if (!s.isEmpty()) {
                    arr[i] = Integer.parseInt(s);
                }
            }
            down[l] = arr;
        }
        in.readLine();
        for (int l = 0; l < n + 1; l++) {
            String str = in.readLine();
            String[] st = str.split(DataCache.SPACE);

            int[] arr = new int[m];

            for (int i = 0; i < st.length; i++) {
                String s = st[i].trim();
                if (!s.isEmpty()) {
                    arr[i] = Integer.parseInt(s);
                }
            }
            right[l] = arr;
        }

        Stopwatch sw = new Stopwatch();
        // String[] mp = Overlap.getInstance().overlap2(dnalist);
        int mp = ManhattanTourist.getInstance().manhattanTourist(n, m, down, right);
        // for (String st : mp) {
        // buffer.append(st);
        // buffer.append('\n');
        // }
        //
        // System.out.println(buffer.toString());

        Composition.getInstance().copyFile(args[0] + "_sol.txt", String.valueOf(mp));
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

}
