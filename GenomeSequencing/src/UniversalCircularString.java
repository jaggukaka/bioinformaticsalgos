import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class UniversalCircularString {

    private UniversalCircularString() {

    }

    private static final class UniversalCircularStringHolder {
        private static final UniversalCircularString UniversalCircularString = new UniversalCircularString();
    }

    public static UniversalCircularString getInstance() {
        return UniversalCircularStringHolder.UniversalCircularString;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);
        int k = in.readInt();
        String allsets = "01";

        String universalString = universalString(k, allsets);

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", universalString);
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    private static String universalString(int k, String allsets) {
        List<String> kmers = new ArrayList<String>();

        DataCache.getInstance().allsets(allsets, k, "", kmers);
        String[] arr = new String[kmers.size()];
        kmers.toArray(arr);

        String[] debruijn = Overlap.getInstance().debruijin(arr, allsets);

        return GenomeSequence.getInstance().genomeCycle(Arrays.asList(debruijn));
    }

}
