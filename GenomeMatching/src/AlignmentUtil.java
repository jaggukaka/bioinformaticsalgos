import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class AlignmentUtil {

    private static final class AlignmentUtilHolder {
        private static final AlignmentUtil AFFINE_GAP_ALIGNMENT = new AlignmentUtil();
    }

    private AlignmentUtil() {

    }

    public static AlignmentUtil getInstance() {
        return AlignmentUtilHolder.AFFINE_GAP_ALIGNMENT;
    }

    public void printsolAlignment(int[][] bol, String b, String a, int i, int j, StringBuffer buffer,
        StringBuffer buffer2) {
        // TODO Auto-generated method stub
        if ((bol[i][j] == -1 || bol[i][j] == -4)) {
            if (i > 0 && j > 0) {
                printsolAlignment(bol, b, a, i - 1, j - 1, buffer, buffer2);
            }
            if (j > 0)
                buffer.append(b.charAt(j - 1));
            if (i > 0)
                buffer2.append(a.charAt(i - 1));
        } else if (bol[i][j] == -2 && i > 0) {

            printsolAlignment(bol, b, a, i - 1, j, buffer, buffer2);
            buffer.append("-");
            buffer2.append(a.charAt(i - 1));
        } else if (bol[i][j] == -3 && j > 0) {

            printsolAlignment(bol, b, a, i, j - 1, buffer, buffer2);
            buffer2.append("-");
            buffer.append(b.charAt(j - 1));
        }

    }

    public int max(int... arr) {
        // TODO Auto-generated method stub
        int max = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (max <= arr[i]) {
                max = arr[i];
                index = i;
            }
        }

        return index;

    }

    public static class Max {

        private int i;

        private int j;

        private int val;

        public void setI(int i) {
            this.i = i;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public int getVal() {
            return val;
        }

        public Max(int i, int j, int val) {
            super();
            this.i = i;
            this.j = j;
            this.val = val;
        }

        public String toString() {
            return i + " " + j + " " + val;
        }

    }

    public static class Solution {
        private int len;

        public int getLen() {
            return len;
        }

        public String[] getSolution() {
            return solution;
        }

        private String[] solution;

        public Solution(int len, String... solution) {
            super();
            this.len = len;
            this.solution = solution;
        }
        
        public List<String> getReadableSolution() {
            List<String> ls = new ArrayList<String>();
            ls.add(String.valueOf(len));
            ls.addAll(Arrays.asList(solution));
            
            return ls;
        }
        

    }

    public String reverse(String b) {
        // TODO Auto-generated method stub
        char[] arr = b.toCharArray();
        int limit = arr.length % 2 == 0 ? arr.length / 2 - 1 : arr.length / 2;

        for (int i = 0; i <= limit; i++) {
            swap(arr, i, arr.length - i - 1);
        }
        return new String(arr);
    }

    private void swap(char[] arr, int i, int j) {
        // TODO Auto-generated method stub
        char l = arr[i];
        arr[i] = arr[j];
        arr[j] = l;
    }

}
