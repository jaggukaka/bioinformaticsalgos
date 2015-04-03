import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class AntiBioticSequencing {

    private static final DataCache datacache = DataCache.getInstance();

    private AntiBioticSequencing() {

    }

    private static final class AntiBioticSequencingHolder {
        private static final AntiBioticSequencing ANTI_BIOTIC_SEQUENCING = new AntiBioticSequencing();

    }

    public static AntiBioticSequencing getInstance() {
        return AntiBioticSequencingHolder.ANTI_BIOTIC_SEQUENCING;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        AntiBioticSequencing antiBioticSequencing = AntiBioticSequencing.getInstance();

        String data = in.readString();
        System.out.println(antiBioticSequencing.antibioticsequenceRNA(data));
    }

    public String antibioticsequenceRNA(String rnaString) {
        return antibioticsequence(rnaString, datacache.getRnacodons());
    }

    public String antibioticsequenceDNA(String dnaString) {
        return antibioticsequence(dnaString, datacache.getDnacodons());
    }

    public String antibioticsequence(String dnarnaseqstring, Map<String, String> codonMap) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i + 3 <= dnarnaseqstring.length(); i = i + 3) {
            String three_mer = dnarnaseqstring.substring(i, i + 3);
            String singlecodon = codonMap.get(three_mer);
            if (singlecodon != null) {
                buffer.append(singlecodon);
            } else {
                break;
            }
        }

        return buffer.toString();
    }

}
