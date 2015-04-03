import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class Composition {

    private Composition() {

    }

    private static final class CompositionHolder {
        private static final Composition COMPOSITION = new Composition();
    }

    public static Composition getInstance() {
        return CompositionHolder.COMPOSITION;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        int k = in.readInt();

        String dnalist = in.readString();

        Stopwatch sw = new Stopwatch();
        // composition
        // String[] mp = Composition.getInstance().composition(dnalist, k);
        // for (String st : mp) {
        // buffer.append(st);
        // buffer.append('\n');
        // }
        //
        // System.out.println(buffer.toString());

        // debrujin
        String mp = Composition.getInstance().debrujin(dnalist, k - 1);

        Composition.getInstance().copyFile(args[0] + "_sol.txt", mp);
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

    public String[] composition(String dna, int k) {
        String[] kmers = new String[dna.length() - k + 1];
        for (int i = 0; i < dna.length() - k + 1; i++) {
            String knucleotide = dna.substring(i, i + k);
            kmers[i] = knucleotide;
        }

        LSD.sort(kmers, k);
        return kmers;
    }

    public String debrujin(String dna, int k) {
        TreeMap<String, String> mp = new TreeMap<String, String>();
        for (int i = 0; i < dna.length() - k; i++) {
            String knucleotide = dna.substring(i, i + k);
            String link = dna.substring(i + 1, i + 1 + k);

            if (mp.containsKey(knucleotide) && link != null) {
                mp.put(knucleotide, mp.get(knucleotide) + "," + link);
            } else {
                mp.put(knucleotide, link);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> er : mp.entrySet()) {
            sb.append(er.getKey()).append(DataCache.SPACE).append("->").append(DataCache.SPACE)
                .append(er.getValue());
            sb.append('\n');
        }

        return sb.toString();
    }

    public void copyFile(String filename, String[] st) throws IOException {
        File is = new File(filename);
        if (!is.exists()) {
            is.createNewFile();
        }
        FileOutputStream os2 = new FileOutputStream(is);
        for (String s : st) {
            if (s != null) {
                os2.write(s.getBytes());
                os2.write("\r\n".getBytes());
            }
        }

        os2.flush();
        os2.close();
        System.out.println("done copying");
    }

    public void copyFile(String filename, List<?> st) throws IOException {
        copyFile(filename, st, "\r\n");
    }

    public void copyFile(String filename, List<?> st, String delim) throws IOException {
        File is = new File(filename);
        if (!is.exists()) {
            is.createNewFile();
        }
        FileOutputStream os2 = new FileOutputStream(is);
        for (int i = 0; i < st.size(); i++) {
            String s = String.valueOf(st.get(i));
            if (s != null) {
                os2.write(s.getBytes());
                if (i != st.size() - 1) {
                    os2.write(delim.getBytes());
                }
            }
        }

        os2.flush();
        os2.close();
        System.out.println("done copying");
    }

    public void copyFile(String filename, String st) throws IOException {
        File is = new File(filename);
        if (!is.exists()) {
            is.createNewFile();
        }
        FileOutputStream os2 = new FileOutputStream(is);
        if (st != null) {
            os2.write(st.getBytes());
        }

        os2.flush();
        os2.close();
        System.out.println("done copying");
    }
}
