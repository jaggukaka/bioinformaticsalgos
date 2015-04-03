/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class ReverseComplement {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        In in = new In(args[0]);

        String data = in.readString();

        System.out.println(ReverseComplementHolder.BRUTE_MOST_FREQUENT_KMERS_MISMATCH.reverseComplement(data));
    }

    private ReverseComplement() {

    }

    private static final class ReverseComplementHolder {
        static char[] chars;

        static {
            // TODO Auto-generated constructor stub
            chars = new char[256];
            chars['T'] = 'A';
            chars['A'] = 'T';
            chars['G'] = 'C';
            chars['C'] = 'G';
        }

        private static final ReverseComplement BRUTE_MOST_FREQUENT_KMERS_MISMATCH = new ReverseComplement();
    }

    public static ReverseComplement getInstance() {
        return ReverseComplementHolder.BRUTE_MOST_FREQUENT_KMERS_MISMATCH;
    }

    public String reverseComplement(String data) {
        StringBuffer buffer = new StringBuffer(data.length());
        if (data != null) {
            for (int i = data.length() - 1; i >= 0; i--) {
                char nucleotide = data.charAt(i);
                buffer.append(ReverseComplementHolder.chars[nucleotide]);

            }
        }

        return buffer.toString();
    }

}
