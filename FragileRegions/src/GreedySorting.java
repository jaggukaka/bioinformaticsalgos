import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class GreedySorting {

    private static final class GreedySortingHolder {
        private static final GreedySorting GREEDY_SORTING = new GreedySorting();
    }

    private GreedySorting() {

    }

    public static GreedySorting getInstance() {
        return GreedySortingHolder.GREEDY_SORTING;
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        In in = new In(args[0]);

        String[] str = in.readLine().split(DataCache.SPACE);
        
        int[] arr =  new int[str.length];
        
        for (int i=0; i < arr.length; i++) {
            String s = str[i];
            if (s.charAt(0) == '+') {
                arr[i] = Integer.parseInt(s.substring(1));
            } else {
                arr[i] =  Integer.parseInt(s);
            }
            
        }
        
        
        

        Stopwatch sw = new Stopwatch();
        List<String> sol =  GreedySorting.getInstance().greedySorting(arr);

        Composition.getInstance().copyFile(args[0] + "_sol.txt", sol);
        System.out.println("took " + sw.elapsedTime() + " seconds");
    }

    public List<String> greedySorting(int[] arr) {

        int l = arr.length;
        Map<Integer, Integer> mp = new HashMap<Integer, Integer>();
        List<String> ls = new LinkedList<String>();

        for (int i = 0; i < l; i++) {
            mp.put(arr[i], i);
        }

        for (int i = 1; i <= l; i++) {
            Integer index = mp.get(i);
            if (index != null) {
                if (index - i + 1 == 0) {
                    continue;
                }
                reverse(arr, i - 1, index, mp);
                ls.add(printArr(arr));
            } else {
                index = mp.get(-i);
                if (index - i + 1 == 0) {
                    arr[index] = i;
                    mp.put(i, index);
                    ls.add(printArr(arr));
                    continue;
                }
                reverse(arr, i - 1, index, mp);
                ls.add(printArr(arr));
            }

            if (arr[i - 1] < 0) {
                arr[i - 1] = i;
                mp.put(i, i-1);
                ls.add(printArr(arr));

            }

        }

        return ls;
    }

    private String printArr(int[] arr) {
        // TODO Auto-generated method stub
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < arr.length; i++) {
            String symbol = arr[i] < 0 ? String.valueOf(arr[i]) : "+" + arr[i];
            if (i == arr.length - 1) {
                builder.append(symbol + ")");
            } else {
                builder.append(symbol + " ");
            }

        }
        return builder.toString();
    }

    public void reverse(int[] arr, int i, int j, Map<Integer, Integer> mp) {
        // TODO Auto-generated method stub
        int len = j - i + 1;
        int limit = len % 2 == 0 ? (len / 2 - 1) + i : len / 2 + i;

        for (int k = i, l = 0; k <= limit; k++, l++) {
            swap(arr, k, j - l, mp);
        }
    }

    private void swap(int[] arr, int i, int j, Map<Integer, Integer> mp) {
        // TODO Auto-generated method stub
        mp.remove(arr[i]);
        mp.remove(arr[j]);
        int l = -arr[i];
        arr[i] = -arr[j];
        mp.put(-arr[j], i);
        arr[j] = l;
        mp.put(l, j);
    }

}
