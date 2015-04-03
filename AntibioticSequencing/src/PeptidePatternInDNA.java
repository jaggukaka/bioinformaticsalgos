import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class PeptidePatternInDNA {

    private static final class PeptidePatternDNAHolder {
        private static final PeptidePatternInDNA PATTERN_IN_DNA = new PeptidePatternInDNA();
    }

    public static PeptidePatternInDNA getInstance() {
        return PeptidePatternDNAHolder.PATTERN_IN_DNA;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String data = in.readString();
        String pattern = in.readString();

        List<String> dnafrags = PeptidePatternDNAHolder.PATTERN_IN_DNA
            .dnafragmentsforpeptidepattern(data, pattern);

        for (String dnafrag : dnafrags) {
            System.out.println(dnafrag);
        }
    }

    public List<String> dnafragmentsforpeptidepattern(String data, String pattern) {
        AntiBioticSequencing antiBioticSequencing = AntiBioticSequencing.getInstance();
        int k = pattern.length() * 3;
        List<String> dnafragments = new ArrayList<String>();
        if (data != null) {
            for (int i = 0; i < data.length() - k + 1; i++) {
                String knucleotide = data.substring(i, i + k);
                String reversecomp = ReverseComplement.getInstance().reverseComplement(knucleotide);

                boolean validNucleotide = addTodnaFrags(dnafragments, knucleotide, antiBioticSequencing, pattern)
                    || addTodnaFrags(dnafragments, reversecomp, antiBioticSequencing, pattern);
                if (validNucleotide) {
                    dnafragments.add(knucleotide);
                }

            }
        }
        return dnafragments;
    }

    private boolean addTodnaFrags(List<String> dnafragments, String knucleotide,
        AntiBioticSequencing antiBioticSequencing, String pattern) {
        // TODO Auto-generated method stub
        String peptide = antiBioticSequencing.antibioticsequenceDNA(knucleotide);
        return pattern.equalsIgnoreCase(peptide);
    }

}
