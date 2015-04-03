import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class BreakPoint {

    private static final class BreakPointHolder {
        private static final BreakPoint BREAK_POINT = new BreakPoint();
    }

    private BreakPoint() {

    }

    public static BreakPoint getInstance() {
        return BreakPointHolder.BREAK_POINT;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String[] str = in.readLine().split(DataCache.SPACE);

        int[] arr = new int[str.length + 2];
        arr[0] = 0;
        arr[arr.length - 1] = arr.length - 1;

        for (int i = 1, j = 0; i < arr.length - 1; i++, j++) {
            String s = str[j];
            if (s.charAt(0) == '+') {
                arr[i] = Integer.parseInt(s.substring(1));
            } else {
                arr[i] = Integer.parseInt(s);
            }

        }

        Stopwatch sw = new Stopwatch();
        int sol = BreakPoint.getInstance().breakPoints(arr);

        Composition.getInstance().copyFile(args[0] + "_sol.txt", String.valueOf(sol));
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

    public int breakPoints(int[] arr) {
        int j = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] - 1 != 0) {
                j++;
            }
        }

        return j;
    }

}
