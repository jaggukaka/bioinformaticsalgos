import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class ProfileMostProbableKmer {

    private ProfileMostProbableKmer() {

    }

    private static final class ProfileMostProbableKmerHolder {
        private static final ProfileMostProbableKmer PROBABLE_KMER = new ProfileMostProbableKmer();
    }

    public static ProfileMostProbableKmer getInstance() {
        return ProfileMostProbableKmerHolder.PROBABLE_KMER;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String dna = in.readLine();
        int k = Integer.valueOf(in.readLine());
        String[] cr = in.readLine().split(DataCache.SPACE);
        Map<Character, Integer> colums = new HashMap<Character, Integer>();

        for (int i = 0; i < cr.length; i++) {
            colums.put(cr[i].charAt(0), i);
        }

        double[][] pd = new double[k][4];

        for (int i = 0; i < k; i++) {
            String[] ss = in.readLine().split(DataCache.SPACE);
            for (int j = 0; j < ss.length; j++) {
                pd[i][j] = Double.valueOf(ss[j]);
            }
        }
        
        System.out.println(ProfileMostProbableKmer.getInstance().profileProbableKmer(pd, dna, colums));

    }

    public String profileProbableKmer(double[][] pd, String dna, Map<Character, Integer> columns) {

        int k = pd.length;
        double max = 0;
        String sol = dna.substring(0, k);
        for (int i = 0; i < dna.length() - k + 1; i++) {
            String knucleotide = dna.substring(i, i + k);
            double pb = 1;
            for (int j = 0; j < knucleotide.length(); j++) {
                char c = knucleotide.charAt(j);
                pb = pb * pd[j][columns.get(c)];
            }

            if (pb > max) {
                max = pb;
                sol = knucleotide;
            }
        }

        return sol;
    }

}
