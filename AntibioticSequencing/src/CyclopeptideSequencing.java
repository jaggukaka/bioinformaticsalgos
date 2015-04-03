import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class CyclopeptideSequencing {

    private static final DataCache datacache = DataCache.getInstance();

    private CyclopeptideSequencing() {

    }

    private static final class CyclopeptideSequencingHolder {
        private static final CyclopeptideSequencing CYCLOPEPTIDE_SEQUENCING = new CyclopeptideSequencing();

    }

    public static CyclopeptideSequencing getInstance() {
        return CyclopeptideSequencingHolder.CYCLOPEPTIDE_SEQUENCING;
    }

    public List<String> peptidsequences(Map<Integer, Integer> spectrum, int max, int spectrumsize) {

        Queue<Subpeptide> candidates = new Queue<Subpeptide>();
        List<Character> singlepeps = new ArrayList<Character>();

        for (Entry<Integer, Character> entry : datacache.getMassaminoacidmap().entrySet()) {
            Integer key = entry.getKey();
            if (spectrum.containsKey(entry.getKey())) {

                String strkey = String.valueOf(entry.getValue());
                candidates.enqueue(new Subpeptide(strkey, key, 0, 0));
                singlepeps.add(entry.getValue());
            }
        }

        List<String> candidatePeptides = new ArrayList<String>();
        while (!candidates.isEmpty()) {
            Subpeptide entry = candidates.dequeue();
            String key = entry.peptide;
            int value = entry.mass;
            if (value == max) {
                candidatePeptides.add(key);
                continue;
            }
            for (Character c : singlepeps) {
                String peptide = key + c;

                if (charExhausted(c, spectrum, key)) {
                    continue;
                }

                int mz = datacache.getAminoacidMass().get(c) + value;
                boolean invalid = false;
                if (mz == max) {
                    List<Integer> theoreticalspectrum = TheoriticalSpectrumProblem.getInstance()
                        .getTheoreticalcyclospectrum(peptide, DataCache.HYPEN);
                    Integer[] arr = new Integer[theoreticalspectrum.size()];
                    Arrays.sort(theoreticalspectrum.toArray(arr));
                    int j = 0;
                    int val = 0;
                    for (int mass : arr) {
                        if (!spectrum.containsKey(mass)) {
                            invalid = true;
                            break;
                        } else {
                            if (j == 0) {
                                int times = spectrum.get(mass);
                                j = times;
                                val = mass;
                                j--;
                                continue;
                            }
                            if (mass != val) {
                                invalid = true;
                                break;
                            } else {
                                j--;
                            }
                        }
                    }

                }

                if (invalid) {
                    continue;
                }

                if (spectrum.containsKey(mz)) {
                    candidates.enqueue(new Subpeptide(peptide, mz, 0, 0));
                }
            }

        }

        List<String> peptides = new ArrayList<String>();
        for (String mutation : candidatePeptides) {
            String pep = convert(mutation);
            peptides.add(pep);
        }

        System.out.println(peptides.size());
        return peptides;
    }

    private String convert(String mutation) {
        StringBuffer buffer = new StringBuffer();
        char[] chararr = mutation.toCharArray();
        for (int i = 0; i < chararr.length; i++) {
            buffer.append(datacache.getAminoacidMass().get(chararr[i]));
            if (i < chararr.length - 1) {
                buffer.append(DataCache.HYPEN);
            }
        }
        return buffer.toString();
    }

    private boolean charExhausted(Character c, Map<Integer, Integer> spectrum, String peptide) {
        // TODO Auto-generated method stub
        char[] pepchar = peptide.toCharArray();
        int max = 0;
        for (char ch : pepchar) {
            if (ch == c) {
                max++;
            }
            Object oj = spectrum.get(datacache.getAminoacidMass().get(c));
            if (oj != null && max >= (Integer) oj) {
                return true;
            }
        }
        return false;
    }

    private static final class Subpeptide implements Comparable<Subpeptide> {
        private String peptide;

        private int mass;

        private int score;

        private int seq;

        public Subpeptide(String peptide, int mass, int score, int seq) {
            super();
            this.peptide = peptide;
            this.mass = mass;
            this.score = score;
            this.seq = seq;
        }

        @Override
        public int compareTo(Subpeptide subpeptide) {
            // TODO Auto-generated method stub
            if (subpeptide.score == this.score) {
                if (subpeptide.seq < this.seq) {
                    return +1;
                } else if (subpeptide.seq == this.seq) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (subpeptide.score < this.score) {
                return -1;
            } else {
                return +1;
            }
        }

        public String toString() {
            return peptide + "," + mass + "," + score + "," + seq;
        }
    }

    private String formatPeptide(int val) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(DataCache.HYPEN);
        buffer.append(val);
        return buffer.toString();
    }

    public String leaderboardCyclopepseq(Map<Integer, Integer> spectrum, int max, int cutoff,
        Map<Integer, Character> allowedpeptides) {

        MinPQ<Subpeptide> minPq = new MinPQ<Subpeptide>();

        for (Entry<Integer, Character> entry : allowedpeptides.entrySet()) {
            int entr = entry.getKey();
            String pep = formatPeptide(entr);
            if (spectrum.containsKey(entr)) {
                minPq.insert(new Subpeptide(pep, entr, 1, 1));
            } else {
                minPq.insert(new Subpeptide(pep, entr, 0, 2));
            }
        }

        String solutionPep = null;
        int maxscore = 0;
        // Map<String, Integer> solutionList = new HashMap<String, Integer>();
        while (!minPq.isEmpty()) {

            List<Subpeptide> top = new ArrayList<Subpeptide>();
            getTop(minPq, spectrum, cutoff, top);
            minPq = null;
            minPq = new MinPQ<Subpeptide>();

            int seq = 0;
            for (Subpeptide peptide : top) {
                String pepstr = peptide.peptide;
                int mass = peptide.mass;

                for (Entry<Integer, Character> entry : allowedpeptides.entrySet()) {
                    int entr = entry.getKey();
                    String pep = pepstr + formatPeptide(entr);

                    int mz = mass + entr;

                    if (mz > max) {
                        continue;
                    } else if (mz == max) {
                        int currscore = getMaxScore(spectrum, pep);
                        if (currscore > maxscore) {
                            solutionPep = pep;
                            // solutionList.put(pep, currscore);
                            maxscore = currscore;
                        }
                        minPq.insert(new Subpeptide(pep, mz, currscore, seq++));
                        continue;
                    }

                    int sc = getMaxScore(spectrum, pep);
                    minPq.insert(new Subpeptide(pep, mz, sc, seq++));
                }
            }

        }

        // StringBuffer buffer = new StringBuffer();
        // int i =0;
        // for (Entry<String, Integer> mutation : solutionList.entrySet()) {
        // String key = mutation.getKey().substring(1);
        // if (maxscore == mutation.getValue()) {
        // i++;
        // buffer.append(key);
        // buffer.append(DataCache.SPACE);
        // }
        // }

        System.out.println(maxscore);
        // System.out.println(i);
        // System.out.println(buffer.toString());

        return solutionPep.substring(1);

    }

    // private int getScore(Map<Integer, Integer> spectrum, String pep) {
    // List<Integer> theoreticalspectrum = TheoriticalSpectrumProblem.getInstance().getTheoreticalLinearspectrum(
    // pep, DataCache.HYPEN);
    // return getsharedscore(spectrum, theoreticalspectrum);
    // }

    private int getMaxScore(Map<Integer, Integer> spectrum, String pepstr) {
        List<Integer> theoreticalspectrum = TheoriticalSpectrumProblem.getInstance()
            .getTheoreticalcyclospectrumForAllAminoAcids(pepstr, DataCache.HYPEN);
        return getsharedscore(spectrum, theoreticalspectrum);

    }

    private int getsharedscore(Map<Integer, Integer> spectrum, List<Integer> theoreticalspectrum) {
        int maxscore = 0;
        Map<Integer, Integer> sharedmap = new HashMap<Integer, Integer>();
        for (int mass : theoreticalspectrum) {
            if (spectrum.containsKey(mass)) {
                if (!sharedmap.containsKey(mass)) {
                    sharedmap.put(mass, 1);
                    maxscore++;
                } else {
                    int times = spectrum.get(mass);
                    int stimes = sharedmap.get(mass);
                    if (stimes < times) {
                        sharedmap.put(mass, stimes + 1);
                        maxscore++;
                    }
                }
            }
        }
        return maxscore;
    }

    private List<Subpeptide> getTop(MinPQ<Subpeptide> minPq, Map<Integer, Integer> spectrum, int cutoff,
        List<Subpeptide> top) {
        // TODO Auto-generated method stub
        int maxscore = 0;
        while (!minPq.isEmpty() && cutoff > 0) {
            Subpeptide pept = minPq.delMin();
            int score = pept.score;
            if (cutoff == 1 && score == maxscore) {
                top.add(pept);
                continue;
            }
            maxscore = score;
            top.add(pept);
            cutoff--;
        }

        return top;
    }

    public String leaderboardCyclopepseqConvolution(Map<Integer, Integer> spectrum, int max, int cutoff,
        List<Integer> allowedpeptides) {

        MinPQ<Subpeptide> minPq = new MinPQ<Subpeptide>();

        int seq1 = 0, seq2 = 0;
        for (int entry : allowedpeptides) {
            String pep = formatPeptide(entry);
            if (spectrum.containsKey(entry)) {
                minPq.insert(new Subpeptide(pep, entry, 1, seq1++));
            } else {
                minPq.insert(new Subpeptide(pep, entry, 0, seq2++));
            }
        }

        String solutionPep = null;
        int maxscore = 0;
        Map<String, Integer> solutionList = new HashMap<String, Integer>();
        while (!minPq.isEmpty()) {

            List<Subpeptide> top = new ArrayList<Subpeptide>();
            getTop(minPq, spectrum, cutoff, top);
            minPq = null;
            minPq = new MinPQ<Subpeptide>();

            int seq = 0;
            for (Subpeptide peptide : top) {
                String pepstr = peptide.peptide;
                int mass = peptide.mass;

                for (int entry : allowedpeptides) {
                    String pep = pepstr + formatPeptide(entry);
                    int mz = mass + entry;
                    if (mz > max) {
                        continue;
                    } else if (mz == max) {
                        int currscore = getMaxScore(spectrum, pep);
                        if (currscore >= maxscore) {
                            solutionPep = pep;
                            solutionList.put(pep, currscore);
                            maxscore = currscore;
                        }
                        minPq.insert(new Subpeptide(pep, mz, currscore, seq++));
                        continue;
                    }
                    int sc = getMaxScore(spectrum, pep);
                    minPq.insert(new Subpeptide(pep, mz, sc, seq++));
                }
            }
        }

        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (Entry<String, Integer> mutation : solutionList.entrySet()) {
            String key = mutation.getKey().substring(1);
            if (maxscore == mutation.getValue()) {
                i++;
                buffer.append(key);
                buffer.append(DataCache.SPACE);
            }
        }
        
        System.out.println(i);
        System.out.println(maxscore);
        System.out.println(buffer.toString());
        return solutionPep.substring(1);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        while (!StdIn.isEmpty()) {
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            String[] spectrum = StdIn.readLine().split(DataCache.SPACE);
            int cutoff = StdIn.readInt();
            int len = spectrum.length;
            System.out.println(len);
            int max = Integer.valueOf(spectrum[len - 1]);
            for (int i = 0; i < len; i++) {
                int val = Integer.valueOf(spectrum[i]);
                if (map.containsKey(val)) {
                    map.put(val, map.get(val) + 1);
                } else {
                    map.put(val, 1);
                }
            }
            // List<String> peptides = CyclopeptideSequencing.getInstance().peptidsequences(map, max, len);
            // StringBuffer buffer = new StringBuffer();
            //
            // for (String peptide : peptides) {
            // buffer.append(peptide);
            // buffer.append(DataCache.SPACE);
            // }
            //
            // System.out.println(buffer.toString());

            String leader = CyclopeptideSequencing.getInstance().leaderboardCyclopepseq(map, max, cutoff,
                datacache.getMassaminoacidmap());
            System.out.println(leader);
        }
    }

}
