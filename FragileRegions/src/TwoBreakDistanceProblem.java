import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class TwoBreakDistanceProblem {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);
        
        List<int[]> p = new LinkedList<int[]>();
        List<int[]> q = new LinkedList<int[]>();

        int max = getArr(in, p);
        int maxq = getArr(in, q);
        
        if (max !=maxq) {
            throw new IllegalArgumentException("the genomes are of different length");
        }
        
        int breakdist = findBreakDistance(max, p, q);

    }

    private static int findBreakDistance(int max, List<int[]> p, List<int[]> q) {
        // TODO Auto-generated method stub
        Digraph di = new Digraph(max*2);
        
        
        for (int[] pd : p) {
            int first = pd[0];
            for (int pdi : pd) {
                
            }
        }
        
        return 0;
    }

    private static int getArr(In in, List<int[]> q) {
        // TODO Auto-generated method stub
        String[] str = in.readLine().split(")");
        int max = 0;
        for (String st : str) {
            String[] numbers = st.trim().split(DataCache.SPACE);
            int[] arr = new int[numbers.length];

            for (int j = 0; j < arr.length; j++) {
                String s = str[j];
                if (j == 0) {
                    s = s.substring(1);
                }

                if (s.charAt(0) == '+') {
                    arr[j] = Integer.parseInt(s.substring(1));
                } else {
                    arr[j] = Integer.parseInt(s);
                }

            }

            max += arr.length;
            q.add(arr);
        }
        
        return max;
    }

    

}
