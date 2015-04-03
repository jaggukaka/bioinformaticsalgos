import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Permute {

    public static void main(String args[]) {
        // permute("abab");
        // permutate("", "abab");
        // allsets("AGCT", 1, "");
        printBraces("", 3, 3);
        convertBase(11, 2);
        System.out.println(reverse("pleasure"));
    }

    public static void permute(String input) {
        int inputLength = input.length();
        boolean[] used = new boolean[inputLength];
        StringBuffer output = new StringBuffer();
        char[] in = input.toCharArray();

        permute(in, output, used, inputLength, 0);

    }

    private static void permute(char[] in, StringBuffer output, boolean[] used, int inputLength, int level) {
        // TODO Auto-generated method stub
        if (level == inputLength) {
            System.out.println(output.toString());
            return;
        }

        for (int i = 0; i < inputLength; i++) {

            if (used[i])
                continue;
            output.append(in[i]);
            used[i] = true;
            permute(in, output, used, inputLength, level + 1);
            used[i] = false;
            output.setLength(output.length() - 1);
        }
    }

    private static void permutate(String head, String tail) {
        if (tail.isEmpty()) {
            System.out.println(head);
            return;
        }
        Map<Character, Boolean> used = new HashMap<Character, Boolean>();
        for (int i = 0; i < tail.length(); i++) {
            char item = tail.charAt(i);
            if (!used.containsKey(item)) {
                used.put(item, true);
                permutate(head + item, tail.substring(0, i) + tail.substring(i + 1, tail.length()));
            }
        }
    }

    public static void allsets(String set, int left, String head) {

        if (left == 0) {
            System.out.println(head);
            return;
        }

        for (int i = 0; i < set.length() && left > 0; i++) {
            char c = set.charAt(i);
            String runner = head + c;
            allsets(set, left - 1, runner);
        }
    }

    private static void printBraces(String head, int left, int right) {

        if (left == 0 && right == 0) {
            System.out.println(head);
            return;
        }

        if (left > right) {
            return;
        }

        if (left > 0) {
            String lhead = head + "(";
            printBraces(lhead, left - 1, right);
        }
        if (right > 0) {
            String rhead = head + ")";
            printBraces(rhead, left, right - 1);
        }
    }

    private static void convertBase(int decimalVal, int base) {
        if (base > 0 && decimalVal < 0) {
            System.out.println("not possible");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (decimalVal != 0) {

            int result = decimalVal / base;
            int remainder = decimalVal % base;
            if (remainder < 0) {
                result++;
                remainder = decimalVal - base * result;
            }
            decimalVal = result;
            buffer.insert(0, remainder);
        }
        System.out.println();
        System.out.println(buffer);
    }

    private static LinkedList<int[]> middleEdge(int indelpenalty) {

        int[] secondarr = new int[] { 1, 6, 7, 9 };
        LinkedList<int[]> ls = new LinkedList<int[]>();
        ls.addFirst(secondarr);

        for (int j = 1; j <= 10; j++) {

            int[] firstarr = new int[secondarr.length];

            firstarr[0] = j * -indelpenalty;
            ls.addFirst(firstarr);
            for (int i=1 ; i < secondarr.length; i++) {
                firstarr[i] = i*j;
            }

            if (j != 10)
            ls.removeLast();

        }

        return ls;
    }
    
    public static String reverse(String b) {
        // TODO Auto-generated method stub
        char[] arr = b.toCharArray();
        int limit = arr.length %2 == 0 ? arr.length/2 -1 : arr.length/2;
        
        for (int i=0; i <= limit; i++) {
            swap(arr,i, arr.length -i -1);
        }
        return new String(arr);
    }

    private static void swap(char[] arr, int i, int j) {
        // TODO Auto-generated method stub
        char l = arr[i];
        arr[i] = arr[j];
        arr[j] = l;
    }
    
    private int ssub(String[] a, String[] b, int[][] sol, int[][] bt) {

        for (int i = 1; i <= a.length; i++) {

            for (int j = 1; j <= b.length; j++) {

                String first = a[i-1];
                String second = b[i-1];

                if (first == second) {
                    sol[i][j] = sol[i - 1][j - 1] + 1;
                    bt[i][j] = -1;
                } else {

                    int k = sol[i - 1][j];
                    int l = sol[i][j - 1];

                    if (k > l) {
                        sol[i][j] = k;
                        bt[i][j] = -2;
                    } else {
                        sol[i][j] = l;
                        bt[i][j] = -3;
                    }
                }
            }
        }

        return sol[a.length][b.length];
    }
    
    
    private void backtrack(int[][] sol, int[][] bt, String[] a, int i, int j) {
        // TODO Auto-generated method stub
        if (i < 0 || j < 0) {
            return;
        }

        if (bt[i][j] == -1) {
            backtrack(sol, bt, a, i - 1, j - 1);
            System.out.print(a[i]);
        } else if (bt[i][j] == -2) {
            backtrack(sol, bt, a, i - 1, j);
        } else if (bt[i][j] == -3) {
            backtrack(sol, bt, a, i, j - 1);
        }

    }

}
