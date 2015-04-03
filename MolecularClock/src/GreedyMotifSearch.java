import java.util.ArrayList;
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
public class GreedyMotifSearch {

    private Map<Character, Integer> columns = new HashMap<Character, Integer>();

    public Map<Character, Integer> getColumns() {
        return columns;
    }

    private GreedyMotifSearch() {
        modifyclms(columns);
    }

    private static final class GreedyMotifSearchHolder {

        private static final GreedyMotifSearch GREEDY_MOTIF_SEARCH = new GreedyMotifSearch();
    }

    public static GreedyMotifSearch getInstance() {
        return GreedyMotifSearchHolder.GREEDY_MOTIF_SEARCH;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

//        In in = new In(args[0]);
//
//        int k = in.readInt();
//
//        int t = in.readInt();
//
//        String[] dnalist = in.readAllStrings();
//
//        StringBuffer buffer = new StringBuffer();
//
//        String[] mp = GreedyMotifSearch.getInstance().greedyMotifSearch(dnalist, k, t, true);
//        for (String st : mp) {
//            buffer.append(st);
//            buffer.append('\n');
//        }
//
//        System.out.println(mp.length);
//
//        System.out.println(buffer.toString());

         In in = new In(args[0]);
         int k = in.readInt();
        
         String[] motifs = in.readAllStrings();
        
         System.out.println(GreedyMotifSearch.getInstance().score(motifs, k));

    }

    public String[] greedyMotifSearch(String[] dnaList, int k, int t, boolean isPseudo) {

        String[] bestMotifs = new String[t];
        for (int i = 0; i < t; i++) {
            bestMotifs[i] = dnaList[i].substring(0, k);
        }
        String firstDNA = dnaList[0];
        int score = score(bestMotifs, k);
        for (int j = 0; j < firstDNA.length() - k + 1; j++) {
            String kmer = firstDNA.substring(j, j + k);
            String[] calmotifs = performGreedySearch(kmer, dnaList, k, isPseudo);
            int nscore = score(calmotifs, k);
            if (score > nscore && calmotifs.length == t) {
                score = nscore;
                bestMotifs = calmotifs;
            }
        }

        return bestMotifs;
    }

    private String[] performGreedySearch(String kmer, String[] dnaList, int k, boolean isPseudo) {

        List<String> profileList = new ArrayList<String>();
        profileList.add(kmer);

        int sizechanged = 0;
        int previoussize = profileList.size();
        double[][] profile = null;
        for (int i = 1; i < dnaList.length; i++) {

            if (previoussize != sizechanged) {
                profile = profile(profileList, k, isPseudo);
            }
            String resultkmer = ProfileMostProbableKmer.getInstance().profileProbableKmer(profile, dnaList[i],
                columns);

            previoussize = profileList.size();
            if (resultkmer != null)
                profileList.add(resultkmer);

            sizechanged = profileList.size();
        }

        String[] profilearr = new String[profileList.size()];
        profileList.toArray(profilearr);

        return profilearr;
    }

    public int score(String[] motifs, int k) {

        int score = 0;
        for (int i = 0; i < k; i++) {
            score += getIndividualScore(motifs, i);
        }

        return score;
    }

    private int getIndividualScore(String[] motifs, int i) {
        Map<Character, Integer> mp = new HashMap<Character, Integer>();
        for (int j = 0; j < motifs.length; j++) {
            Character c = motifs[j].charAt(i);
            if (mp.containsKey(c)) {
                mp.put(c, mp.get(c) + 1);
            } else {
                mp.put(c, 1);
            }
        }
        int max = 0;
        for (Entry<Character, Integer> entry : mp.entrySet()) {
            int value = entry.getValue();
            if (value > max) {
                max = value;
            }
        }

        return motifs.length - max;
    }

    public double[][] profile(List<String> listmotifs, int k, boolean isPseudo) {

        String[] motifs = new String[listmotifs.size()];
        listmotifs.toArray(motifs);

        return profile(motifs, k, isPseudo);
    }

    public double[][] profile(String[] motifs, int k, boolean isPseudo) {

        double[][] profile = new double[k][4];

        for (int i = 0; i < k; i++) {
            profile[i] = getIndividualProfile(motifs, i, isPseudo);
        }

        return profile;
    }

    private double[] getIndividualProfile(String[] motifs, int i, boolean isPseudo) {
        Map<Character, Integer> mp = new HashMap<Character, Integer>();
        int lenMotif = motifs.length;
        for (int j = 0; j < motifs.length; j++) {
            if (motifs[j] == null) {
                lenMotif--;
                continue;
            }
            Character c = motifs[j].charAt(i);
            if (mp.containsKey(c)) {
                mp.put(c, mp.get(c) + 1);
            } else {
                mp.put(c, 1);
            }
        }

        boolean changed = false;
        if (mp.size() < 4 && isPseudo) {
            modifyMap(mp);
            changed = true;
        }

        double[] db = new double[4];
        for (Entry<Character, Integer> entry : mp.entrySet()) {
            int value = entry.getValue();
            int divisor = changed ? lenMotif + 4 : lenMotif;
            db[columns.get(entry.getKey())] = (double) value / divisor;
        }

        return db;
    }

    private void modifyMap(Map<Character, Integer> mp) {
        // TODO Auto-generated method stub

        String x = "ACGT";
        for (char c : x.toCharArray()) {
            if (!mp.containsKey(c)) {
                mp.put(c, 1);
            } else {
                mp.put(c, mp.get(c) + 1);
            }
        }

    }

    private void modifyclms(Map<Character, Integer> clms) {
        int size = clms.size();
        String x = "ACGT";
        for (char c : x.toCharArray()) {
            if (!clms.containsKey(c)) {
                clms.put(c, size++);
            }
        }

    }
}
