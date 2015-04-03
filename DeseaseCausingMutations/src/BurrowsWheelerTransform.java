import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class BurrowsWheelerTransform {
    
    private BurrowsWheelerTransform() {

    }

    private static final class BurrowsWheelerTransformHolder {
        private static final BurrowsWheelerTransform BURROWS_WHEELER_TRANSFORM = new BurrowsWheelerTransform();

    }

    public static BurrowsWheelerTransform getInstance() {
        return BurrowsWheelerTransformHolder.BURROWS_WHEELER_TRANSFORM;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readString();
        
        Stopwatch sw = new Stopwatch();

        String bwt = BurrowsWheelerTransform.getInstance().burrowsWheelerTransform(s).getBwt();
        int runlen = BurrowsWheelerTransform.getInstance().runlength(10, bwt);
        
        StringBuffer buffer = new StringBuffer();
        buffer.append(bwt);
        buffer.append('\n');
        buffer.append(runlen);

        

        Composition.getInstance().copyFile(args[0] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    public  SuffixArrBWT burrowsWheelerTransform(String s) {
        SuffixArrayX sx = new SuffixArrayX(s);

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            int k = sx.index(i);
            if (k > 0)
                buffer.append(s.charAt(k - 1));
            else
                buffer.append(s.charAt(s.length() - 1));
        }
        

        return new SuffixArrBWT(buffer.toString(), sx);
    }
    
    public  int runlength(int runlen, String bwt) {
        char previous = bwt.charAt(0);
        int runlength = 1;
        int total = 0;
        for (int i =1; i < bwt.length(); i++) {
            if (previous == bwt.charAt(i)) {
                runlength++;
                if (runlength == 10) {
                    total++;
                }
            } else {
                previous = bwt.charAt(i);
                runlength = 1;
            }
        }
        
        return total;
    }

}
