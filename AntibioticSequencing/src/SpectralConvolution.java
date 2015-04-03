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
public class SpectralConvolution {

    private SpectralConvolution() {

    }

    private static final class SpectralConvolutionHolder {
        private static final SpectralConvolution SPECTRAL_CONVOLUTION = new SpectralConvolution();

    }

    public static SpectralConvolution getInstance() {
        return SpectralConvolutionHolder.SPECTRAL_CONVOLUTION;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        while (!StdIn.isEmpty()) {
            String[] fields = StdIn.readLine().split(" ");
            int m = StdIn.readInt();
            int n = StdIn.readInt();

            int[] vals = new int[fields.length];
            for (int i = 0; i < fields.length; i++)
                vals[i] = Integer.parseInt(fields[i]);
            Arrays.sort(vals);

//            MinPQ<ConvolutionPeptide> pq = SpectralConvolution.getInstance().getAllPeptides(vals);
//
//            StringBuffer buffer = new StringBuffer();
//            while (!pq.isEmpty()) {
//                ConvolutionPeptide pep = pq.delMin();
//                int j = pep.repeat;
//                while (j-- > 0) {
//                    buffer.append(pep.peptide);
//                    buffer.append(" ");
//                }
//            }
//
//            System.out.println(buffer.toString());
            
            String sol = SpectralConvolution.getInstance().leaderboardwithconvolution(m, n, vals);
            System.out.println(sol);
        }

    }

    private MinPQ<ConvolutionPeptide> getAllPeptides(int[] vals) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = vals.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                int key = vals[i] - vals[j];
                if (key != 0) {
                    if (map.containsKey(key)) {
                        map.put(key, map.get(key) + 1);
                    } else {
                        map.put(key, 1);
                    }
                }
            }
        }

        MinPQ<ConvolutionPeptide> pq = new MinPQ<SpectralConvolution.ConvolutionPeptide>();
        for (Entry<Integer, Integer> entry : map.entrySet()) {

            ConvolutionPeptide peptide = new ConvolutionPeptide(entry.getKey(), entry.getValue());
            pq.insert(peptide);
        }

        return pq;
    }

    public String leaderboardwithconvolution(int m, int n, int[] spectrum) {
        List<Integer> allowedPeptides = getAllowedPeptides(m, spectrum);
        int len = spectrum.length;
        int max = spectrum[len - 1];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < len; i++) {
            int val = spectrum[i];
            if (map.containsKey(val)) {
                map.put(val, map.get(val) + 1);
            } else {
                map.put(val, 1);
            }
        }

        String sol = CyclopeptideSequencing.getInstance().leaderboardCyclopepseqConvolution(map, max, n, allowedPeptides);
        return sol;
    }

    private List<Integer> getAllowedPeptides(int m, int[] spectrum) {
        // TODO Auto-generated method stub
        MinPQ<ConvolutionPeptide> pq = getAllPeptides(spectrum);
        List<Integer> list = new ArrayList<Integer>();

        int repeats = 0;
        while (!pq.isEmpty() && m > 0) {
            ConvolutionPeptide pep = pq.delMin();
            int j = pep.repeat;
            int masspep = pep.peptide;
            if (masspep >= 57 && masspep < 200) {
                if (m == 1) {
                    if (j == repeats) {
                        list.add(masspep);
                        continue;
                    } else if (list.size() >= m) {
                        break;
                    }
                }
                list.add(masspep);
                repeats = j;
                m--;

            }

        }
        return list;
    }

    private static final class ConvolutionPeptide implements Comparable<ConvolutionPeptide> {
        int peptide;

        int repeat;

        public ConvolutionPeptide(int peptide, int repeat) {
            super();
            this.peptide = peptide;
            this.repeat = repeat;
        }

        @Override
        public int compareTo(ConvolutionPeptide that) {
            // TODO Auto-generated method stub
            if (that.repeat > this.repeat) {
                return 1;
            } else if (that.repeat == this.repeat) {
                if (that.peptide == this.peptide) {
                    return 0;
                } else if (that.peptide > this.peptide) {
                    return -1;
                } else {
                    return +1;
                }
            } else {
                return -1;
            }
        }

    }

}
