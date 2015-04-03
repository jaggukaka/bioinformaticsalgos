import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class RandomizedMotifSearch {

    private RandomizedMotifSearch() {

    }

    private static final class RandomizedMotifSearchHolder {
        private static final RandomizedMotifSearch RANDOMIZED_MOTIF_SEARCH = new RandomizedMotifSearch();
    }

    public static RandomizedMotifSearch getInstance() {
        return RandomizedMotifSearchHolder.RANDOMIZED_MOTIF_SEARCH;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        In in = new In(args[0]);

        int k = in.readInt();

        int t = in.readInt();

        String[] dnalist = in.readAllStrings();

        StringBuffer buffer = new StringBuffer();
        String[] mp = RandomizedMotifSearch.getInstance().randomizedMotifSearch(dnalist, k, t, true);
        for (String st : mp) {
            buffer.append(st);
            buffer.append('\n');
        }

        System.out.println(mp.length);

        System.out.println(buffer.toString());

    }

    public String[] randomizedMotifSearch(String[] dnalist, int k, int t, boolean isPseudo) {
        String[] bestMotifs = new String[t];
        int score = 0;
        for (int r = 0; r < 1000; r++) {

            String[] randomMotifs = new String[t];
            for (int i = 0; i < t; i++) {
                String dna = dnalist[i];
                int randomI = StdRandom.uniform(dna.length() - k);
                randomMotifs[i] = dna.substring(randomI, randomI + k);
            }

            int localscore = GreedyMotifSearch.getInstance().score(randomMotifs, k);
            if (score == 0) {
                score = localscore;
            }
            while (true) {

                List<String> motifs = new ArrayList<String>();
                double[][] profile = GreedyMotifSearch.getInstance().profile(randomMotifs, k, isPseudo);
                for (int j = 0; j < t; j++) {
                    String motif = ProfileMostProbableKmer.getInstance().profileProbableKmer(profile, dnalist[j],
                        GreedyMotifSearch.getInstance().getColumns());
                    motifs.add(motif);
                }

                String[] calmotifs = new String[motifs.size()];
                motifs.toArray(calmotifs);

                int nscore = GreedyMotifSearch.getInstance().score(calmotifs, k);
                if (localscore > nscore) {
                    localscore = nscore;
                    randomMotifs = calmotifs;
                } else {
                    if (score > nscore) {
                        score = nscore;
                        bestMotifs = calmotifs;
                    }
                    break;
                }
            }
        }
        System.out.println(score);
        return bestMotifs;

    }

}
