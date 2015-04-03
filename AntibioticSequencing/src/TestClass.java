import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 */

/**
 * @author jpyla
 *
 */
public class TestClass {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        File is = new File("src/B_brevis2.txt");
        if (!is.exists()) {
            is.createNewFile();
        }
        In in = new In("B_brevis.txt");
        FileOutputStream os2 = new FileOutputStream(is);
        while (!in.isEmpty()) {
            os2.write(in.readLine().getBytes());
        }
        
        os2.write("\r\n".getBytes());
        os2.write("VKLFPWFNQY".getBytes());
        
        os2.flush();
        os2.close();
        System.out.println("done copying");
        
        
    }

}
