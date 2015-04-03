import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class CoinChange {

    private CoinChange() {

    }

    private static final class CoinChangeHolder {
        private static final CoinChange COUNTING_PEPTIDE = new CoinChange();

    }

    public static CoinChange getInstance() {
        return CoinChangeHolder.COUNTING_PEPTIDE;
    }

    public int minNumberOfCoins(int money, int[] denominations) {
        int[] moneychange = new int[money + 1];

        for (int m = 1; m <= money; m++) {
            moneychange[m] = Integer.MAX_VALUE;
            for (int d : denominations) {
                if (m >= d) {
                    int change = moneychange[m - d];
                    if (change + 1 < moneychange[m]) {
                        moneychange[m] = change + 1;
                    }
                }
            }
        }

        return moneychange[money];

    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

//        In in = new In(args[0]);
//
//        // Solve overlap
//        // String[] dnalist = in.readAllStrings();
//
//        // solve debrujin
//
//        int m = Integer.parseInt(in.readLine().trim());
//
//        String str = in.readLine();
//        
//        String[] st = str.split(",");
//
//        int[] arr = new int[st.length];
//
//        for (int i = 0; i < st.length; i++) {
//            String s = st[i].trim();
//            if (!s.isEmpty()) {
//                arr[i] = Integer.parseInt(s);
//            }
//        }

        Stopwatch sw = new Stopwatch();
        // String[] mp = Overlap.getInstance().overlap2(dnalist);
        int mp = CoinChange.getInstance().minNumberOfCoins(100, new int[]{2,5,1,3,6,8,4});
        // for (String st : mp) {
        // buffer.append(st);
        // buffer.append('\n');
        // }
        //
        // System.out.println(buffer.toString());

//        Composition.getInstance().copyFile(args[0] + "_sol.txt", String.valueOf(mp));
//        System.out.println("took " + sw.elapsedTime() + " seconds");
        System.out.println(mp);

    }

}
