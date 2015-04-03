import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class GibbsSampling {

    private GibbsSampling() {

    }

    private static final class GibbsSamplingHolder {
        private static final GibbsSampling GIBBS_SAMPLING = new GibbsSampling();
    }

    public static GibbsSampling getInstance() {
        return GibbsSamplingHolder.GIBBS_SAMPLING;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        int k = in.readInt();

        int t = in.readInt();

        int N = in.readInt();

        String[] dnalist = in.readAllStrings();

        StringBuffer buffer = new StringBuffer();
        String[] mp = GibbsSampling.getInstance().gibbsMotifs(dnalist, k, t, N);
        for (String st : mp) {
            buffer.append(st);
            buffer.append('\n');
        }

        System.out.println(mp.length);

        System.out.println(buffer.toString());
    }

    public String[] gibbsMotifs(String[] dnalist, int k, int t, int N) {

        String[] bestMotifs = new String[t];
        int score = 0;

        for (int j = 0; j < 20; j++) {

            String[] randomMotifs = new String[t];

            for (int i = 0; i < t; i++) {
                String dna = dnalist[i];
                int randomI = StdRandom.uniform(dna.length() - k);
                randomMotifs[i] = dna.substring(randomI, randomI + k);
            }

            int localscore = GreedyMotifSearch.getInstance().score(randomMotifs, k);
            String[] localbest = new String[t];
            if (score == 0) {
                score = localscore;
            }

            for (int l = 0; l < N; l++) {
                int randi = StdRandom.uniform(t);
                randomMotifs[randi] = null;

                double[][] profile = GreedyMotifSearch.getInstance().profile(randomMotifs, k, true);
                String probablekmer = profileRandomProbableKmer(profile, dnalist[randi]);

                randomMotifs[randi] = probablekmer;

                int nscore = GreedyMotifSearch.getInstance().score(randomMotifs, k);
                if (localscore > nscore) {
                    localscore = nscore;
                    for (int i = 0; i < randomMotifs.length; i++) {
                        localbest[i] = randomMotifs[i];
                    }

                }
            }

            if (score > localscore) {
                score = localscore;
                bestMotifs = localbest;
            }
        }

        System.out.println(score);
        return bestMotifs;
    }

    public String profileRandomProbableKmer(double[][] pd, String dna) {

        Map<Character, Integer> columns = GreedyMotifSearch.getInstance().getColumns();

        int k = pd.length;
        String sol = dna.substring(0, k);
        int len = dna.length() - k + 1;
        double[] kmerpd = new double[len];
        double sumpd = 0;

        for (int i = 0; i < len; i++) {
            String knucleotide = dna.substring(i, i + k);
            double pb = 1;
            for (int j = 0; j < knucleotide.length(); j++) {
                char c = knucleotide.charAt(j);
                pb = (double) pb * pd[j][columns.get(c)];
            }
            sumpd += (double) pb;

            kmerpd[i] = pb;

        }

        for (int i = 0; i < kmerpd.length; i++) {
            double freq = (double) (kmerpd[i] / sumpd) * 100;
            kmerpd[i] = freq;
        }

        int i = random(kmerpd, len);

        sol = dna.substring(i, i + k);

        return sol;
    }

    private int random(double[] kmerpd, int len) {
        // TODO Auto-generated method stub
        int[] aux = new int[len];

        for (int i = 0; i < len; i++) {
            if (i == 0) {
                aux[i] = (int) Math.rint(kmerpd[i]);
            } else {
                aux[i] = aux[i - 1] + (int) Math.rint(kmerpd[i]);
            }
        }

        int randi = StdRandom.uniform(aux[len - 1] + 1);
        int l = 0;
        int h = len - 1;
        while (l < h) {
            int mid = (l + h) / 2;
            if (aux[mid] > randi) {
                h = mid;
            } else {
                l = mid + 1;
            }
        }
        int rand = aux[l] >= randi ? l : l - 1;
        return rand;
    }

}
