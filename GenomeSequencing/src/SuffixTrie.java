import java.util.Arrays;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class SuffixTrie {

    private Suffix[] suffixes;

    public SuffixTrie(String text) {
        int N = text.length();
        this.suffixes = new Suffix[N];
        for (int i = 0; i < N; i++)
            suffixes[i] = new Suffix(text, i);
        Arrays.sort(suffixes);
    }

    private static class Suffix implements Comparable<Suffix> {
        private final String text;

        private final int index;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }

        private int length() {
            return text.length() - index;
        }

        private char charAt(int i) {
            return text.charAt(index + i);
        }

        public int compareTo(Suffix that) {
            if (this == that)
                return 0; // optimization
            int N = Math.min(this.length(), that.length());
            for (int i = 0; i < N; i++) {
                if (this.charAt(i) < that.charAt(i))
                    return -1;
                if (this.charAt(i) > that.charAt(i))
                    return +1;
            }
            return this.length() - that.length();
        }

        public String toString() {
            return text.substring(index);
        }
    }

    public String lcsuffix(String s, int k) {
        int lo = 0, hi = suffixes.length - 1;
        String match = null;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            Suffix suffix = suffixes[mid];
            int cmp = compare(s, suffix);
            if (cmp == -10) {
                return null;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp == 0 && k <= suffix.text.length()) {
                return suffix.text.substring(0, k);
            }
        }
        return match;
    }

    // compare query string to suffix
    private static int compare(String query, Suffix suffix) {
        int q = query.length();
        int l = suffix.length();

        int N = Math.min(q, l);
        for (int i = 0; i < N; i++) {
            if (query.charAt(i) < suffix.charAt(i))
                return -1;
            if (query.charAt(i) > suffix.charAt(i))
                return +1;
        }

        return q > l ? -10 : 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}
