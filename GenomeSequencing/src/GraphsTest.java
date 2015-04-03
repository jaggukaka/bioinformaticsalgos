import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class GraphsTest {

    public static void main(String[] args) throws IOException {

        In in = new In(args[0]);
        List<int[]> nodes = new ArrayList<int[]>();
        int max = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] edge = line.split("->");

            int i = Integer.parseInt(edge[0].trim());
            String[] edgeTo = edge[1].trim().split(",");

            int[] nodeval = new int[edgeTo.length + 1];
            nodeval[0] = i;
            max = Math.max(max, i);
            for (int m = 0; m < edgeTo.length; m++) {
                int k = Integer.parseInt(edgeTo[m]);
                nodeval[m + 1] = k;
                max = Math.max(max, k);
            }

            nodes.add(nodeval);
        }

        // Eulerian cycle
        // Iterable<Integer> euler = EulerianCycle.getInstance().getEulerCycleDG(max, nodes);

        // Eulerian path

        Iterable<Integer> euler = EulerianPath.getInstance().getEulerPathDG(max, nodes);

        if (euler == null) {
            StdOut.println("Not eulerian");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        for (int v : euler) {
            buffer.append(v).append("->");
        }
        Stopwatch sw = new Stopwatch();

        Composition.getInstance().copyFile(args[0] + "_sol.txt", buffer.toString());
        System.out.println("took " + sw.elapsedTime() + " seconds");

    }

}
