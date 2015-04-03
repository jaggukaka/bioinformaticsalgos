import java.util.ArrayList;
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
public class TheoriticalSpectrumProblem {

    private static final DataCache datacache = DataCache.getInstance();

    private TheoriticalSpectrumProblem() {

    }

    private static final class TheoriticalSpectrumProblemHolder {
        private static final TheoriticalSpectrumProblem TH_PROBLEM = new TheoriticalSpectrumProblem();

    }

    public static TheoriticalSpectrumProblem getInstance() {
        return TheoriticalSpectrumProblemHolder.TH_PROBLEM;
    }

    public List<Integer> getTheoreticalcyclospectrum(String peptide, String pattern) {
        peptide = processPeptidestr(peptide, pattern);
        List<String> combinations = new ArrayList<String>();

        String doublepep = peptide + peptide.substring(0, peptide.length() - 1);

        findTheoriticalSpectrumForcyclicPeptide(combinations, "", doublepep, peptide.length());
        combinations.add(peptide);
        return processCombinations(combinations);
    }

    public List<Integer> getTheoreticalcyclospectrumForAllAminoAcids(String peptide, String pattern) {
        List<String> combinations = new ArrayList<String>();

        String doublepep = peptide + peptide.substring(0, peptide.lastIndexOf(pattern));

        findThSpecCyclicPeptAllAminoacids(combinations, "", doublepep, peptide.length(), pattern);
        combinations.add(peptide);
        return processCombinationsAllAminoacids(combinations, pattern);
    }

    private List<Integer> processCombinationsAllAminoacids(List<String> combinations, String pattern) {
        List<Integer> spectrum = new ArrayList<Integer>(combinations.size());
        for (String subpeptide : combinations) {
            int totalmass = 0;
            String[] masses = subpeptide.split(pattern);
            for (String k : masses) {
                if (k != null && k.length() > 0) {
                    totalmass = totalmass + Integer.parseInt(k);
                }
            }
            spectrum.add(totalmass);
        }

        //System.out.println(combinations);
        return spectrum;
    }

    public List<Integer> getTheoreticalLinearspectrum(String peptide, String pattern) {
        peptide = processPeptidestr(peptide, pattern);
        List<String> combinations = new ArrayList<String>();

        findTheoriticalSpectrumForlinearPeptide(combinations, "", peptide);
        return processCombinations(combinations);
    }

    private List<Integer> processCombinations(List<String> combinations) {

        List<Integer> spectrum = new ArrayList<Integer>(combinations.size());
        for (String subpeptide : combinations) {
            int totalmass = 0;
            char[] subpeptidechars = subpeptide.toCharArray();
            for (int i = 0; i < subpeptidechars.length; i++) {
                totalmass = totalmass + datacache.getAminoacidMass().get(subpeptidechars[i]);
            }
            spectrum.add(totalmass);
        }

        // System.out.println(combinations);
        return spectrum;
    }

    public String processPeptidestr(String peptide, String pattern) {
        // TODO Auto-generated method stub
        if (peptide.contains(pattern)) {
            String[] masses = peptide.split(pattern);
            StringBuffer buffer = new StringBuffer();
            for (String k : masses) {
                if (k != null && k.length() > 0) {
                    buffer.append(datacache.getMassaminoacidmap().get(Integer.valueOf(k)));
                }
            }

            peptide = buffer.toString();
        }
        return peptide;
    }

    private void findTheoriticalSpectrumForcyclicPeptide(List<String> combinations, String head, String tail,
        int origlen) {
        if (head.length() >= origlen) {
            return;
        }
        combinations.add(head);
        // System.out.println(head);
        if (tail.isEmpty()) {
            return;
        }
        Map<Character, Boolean> used = new HashMap<Character, Boolean>();
        for (int i = 0; i < tail.length(); i++) {
            if (!head.isEmpty() && i > 0) {
                continue;
            }
            boolean lenpassed = i >= origlen;
            char item = tail.charAt(i);
            if (!used.containsKey(item) || !lenpassed) {
                used.put(item, true);
                findTheoriticalSpectrumForcyclicPeptide(combinations, head + item,
                    tail.substring(i + 1, tail.length()), origlen);
            }
        }

    }

    private void findThSpecCyclicPeptAllAminoacids(List<String> combinations, String head, String tail,
        int origlen, String pattern) {
        if (head.length() >= origlen) {
            return;
        }
        combinations.add(head);
        // System.out.println(head);
        if (tail.isEmpty()) {
            return;
        }
        Map<String, Boolean> used = new HashMap<String, Boolean>();
        for (int i = 0; i < tail.lastIndexOf(pattern) ; i =  tail.indexOf(pattern, i+1)) {
            if (!head.isEmpty() && i > 0) {
                continue;
            }
            boolean lenpassed = i >= origlen;
            int nextindex = tail.indexOf(pattern, i+1);
            if (nextindex == -1) {
                nextindex = tail.length();
            }
            String item = tail.substring(i, nextindex);
            if (!used.containsKey(item) || !lenpassed) {
                used.put(item, true);
                findThSpecCyclicPeptAllAminoacids(combinations, head + item,
                    tail.substring(nextindex, tail.length()), origlen, pattern);
            }
        }

    }

    private void findTheoriticalSpectrumForlinearPeptide(List<String> combinations, String head, String tail) {
        combinations.add(head);
        // System.out.println(head);
        if (tail.isEmpty()) {
            return;
        }
        for (int i = 0; i < tail.length(); i++) {
            if (!head.isEmpty() && i > 0) {
                continue;
            }
            char item = tail.charAt(i);
            findTheoriticalSpectrumForlinearPeptide(combinations, head + item,
                tail.substring(i + 1, tail.length()));
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        while (!StdIn.isEmpty()) {
            String[] peptides = StdIn.readLine().split(DataCache.SPACE);
            List<Integer> solution = TheoriticalSpectrumProblem.getInstance()
                .getTheoreticalcyclospectrumForAllAminoAcids(peptides[0], "-");
            List<Integer> solution2 =
            TheoriticalSpectrumProblem.getInstance().getTheoreticalcyclospectrum(peptides[1],"-");
            System.out.println(solution);
            System.out.println(solution2);
        }

    }
}
