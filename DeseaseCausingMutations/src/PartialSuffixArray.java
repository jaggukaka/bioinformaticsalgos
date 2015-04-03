import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class PartialSuffixArray {
    
    Map<Integer, Integer> mp ;
    char[] occurences = new char[84];
    String[] lastcolumn;
    Map<Character, Integer> firstoccurence = new HashMap<Character, Integer>(4, 0.9f);

    public Map<Integer, Integer> getMp() {
        return mp;
    }


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String s = in.readLine();
        int gap = Integer.valueOf(in.readLine());

        ReferenceQueue<SuffixArrayX> referenceQueue = new ReferenceQueue<SuffixArrayX>();
        WeakReference<SuffixArrayX> suReference = new WeakReference<SuffixArrayX>(new SuffixArrayX(s),
            referenceQueue);
        SuffixArrayX sx = suReference.get();
        //Map<Integer, Integer> mp = new HashMap<Integer, Integer>();

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < sx.getIndex().length; i++) {
            int val = sx.index(i);
            if (val % gap == 0) {
                //mp.put(i, val);
                buffer.append(i).append(',').append(val);
                buffer.append('\n');
            }
        }

        suReference.enqueue();

        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }
    
    
    public PartialSuffixArray (String s, int gap) {
        ReferenceQueue<SuffixArrayX> referenceQueue = new ReferenceQueue<SuffixArrayX>();
        WeakReference<SuffixArrayX> suReference = new WeakReference<SuffixArrayX>(new SuffixArrayX(s),
            referenceQueue);
        
        SuffixArrayX sx = suReference.get();
        

        mp = new HashMap<Integer, Integer>(16, 0.9f);
        for (int i = 0; i < sx.getIndex().length; i++) {
            int val = sx.index(i);
            if (val % gap == 0) {
                mp.put(i, val);
                
            }
        }

        suReference.enqueue();
    }

}
