import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class CountingPeptide {

    private static final DataCache datacache = DataCache.getInstance();

    private CountingPeptide() {

    }

    private static final class CountingPeptideHolder {
        private static final CountingPeptide COUNTING_PEPTIDE = new CountingPeptide();

    }

    public static CountingPeptide getInstance() {
        return CountingPeptideHolder.COUNTING_PEPTIDE;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        while (!StdIn.isEmpty()) {
            int totalsum = StdIn.readInt();
            Integer[] arr = new Integer[]{1, 5, 10, 25, 50 ,100};
            System.out.println(CountingPeptide.getInstance().countPeptides2(Arrays.asList(arr), totalsum));
//            System.out.println("totalpeptides final - "
//                + CountingPeptide.getInstance().printAllSolutionsResult(totalsum));
        }
    }

    public long printAllSolutionsResult(int totalsum) {
        // TODO Auto-generated method stub
        List<String> solutions = countpeptides(totalsum, DataCache.SPACE, null, 0);
        System.out.println(solutions.size());
        long totalpeptides = 0L;

        Map<Integer, Long> factorialMap = new HashMap<Integer, Long>();
        for (String sol : solutions) {
            int dupindex = 0, previous = 0;
            String check = null;
            List<Integer> dupindexlen = new ArrayList<Integer>();
            String[] items = sol.trim().split(DataCache.SPACE);
            int length = items.length;
            for (int i = 0; i < length; i++) {
                String item = items[i];

                if (check != null && !check.equalsIgnoreCase(item)) {
                    dupindex = i - previous;
                    if (dupindex > 1) {
                        dupindexlen.add(dupindex);
                    }
                    previous = i;
                }
                check = item;
            }
            dupindex = length - previous;
            if (dupindex > 1) {
                dupindexlen.add(dupindex);
            }

            long totalpeptideincrement = 0;
            totalpeptideincrement = getValue(factorialMap, length);
            for (int i : dupindexlen) {
                totalpeptideincrement = totalpeptideincrement / getValue(factorialMap, i);
            }
            // if (repeats > 0) {
            // totalpeptideincrement = ((long) Math.pow(2, repeats)) * totalpeptideincrement;
            // }
            totalpeptides = totalpeptides + totalpeptideincrement;
            // System.out.println("totalpeptides - " + totalpeptides);
        }

        return totalpeptides;
    }

    private long getValue(Map<Integer, Long> factorialMap, int length) {
        if (!factorialMap.containsKey(length)) {
            long value = FactorialCalculator.factorial(length);
            factorialMap.put(length, value);
            return value;
        } else {
            return factorialMap.get(length);
        }
    }

    public long countPeptides(List<Integer> peptidemasses, int sumtotal) {

        long[] cumulativesum = new long[sumtotal + 1];
        cumulativesum[0] = 1L;
        for (int i = 1; i <= sumtotal; i++) {
            cumulativesum[i] = 0L;
        }

        for (int peptidemass : peptidemasses) {

            for (int i = 0; i <= sumtotal - peptidemass; i++) {
                cumulativesum[i + peptidemass] = cumulativesum[i] + cumulativesum[i + peptidemass];
            }
        }

        return cumulativesum[sumtotal];
    }

    public long countPeptides2(List<Integer> peptidemasses, int sumtotal) {

        long[] solutions = new long[sumtotal + 1];
        solutions[0] = 1L;

        for (int pep : peptidemasses) {

            for (int i = pep; i <= sumtotal; i++) {
                solutions[i] = solutions[i] + solutions[i - pep];
            }
        }

        return solutions[sumtotal];

    }

    public List<String> countpeptides(int sumtotal, String pattern, Map<Integer, Boolean> spectrum,
        int spectrumsize) {
        String tempSoln = new String();
        List<String> countingSolution = new ArrayList<String>();
        countPeptides(tempSoln, 0, sumtotal, countingSolution, pattern, spectrum, spectrumsize);
        return countingSolution;
    }

    private void countPeptides(String solution, int startIndex, int remainingtarget,
        List<String> countingSolution, String pattern, Map<Integer, Boolean> spectrum, int spectrumsize) {

        List<Integer> peptidemasses = datacache.getPeptidemasses();
        for (int i = startIndex; i < peptidemasses.size(); i++) {
            int val = peptidemasses.get(i);
            if (spectrum != null && !spectrum.containsKey(val)) {
                continue;
            }
            int temp = remainingtarget - val;
            String tempsol = solution + "" + val + pattern;

            if (temp < 0) {
                break;
            }
            if (temp == 0) {
                // reached the answer hence quit from the loop
                if (spectrum != null) {

                    int theoreticalspectrum = getTheoreticalSpectrumSize(tempsol, pattern);
                    if (spectrumsize != theoreticalspectrum) {
                        break;
                    }
                }
                countingSolution.add(tempsol);
                break;
            } else {
                // target not reached, try the solution recursively with the
                // current denomination as the start point.
                countPeptides(tempsol, i, temp, countingSolution, pattern, spectrum, spectrumsize);
            }
        }
    }

    private int getTheoreticalSpectrumSize(String tempsol, String pattern) {
        // TODO Auto-generated method stub

        String[] arr = tempsol.split(pattern);
        int arrsize = arr.length;
        return arrsize * (arrsize - 1) + 2;
    }

}
