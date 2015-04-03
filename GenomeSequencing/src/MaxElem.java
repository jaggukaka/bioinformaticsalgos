import java.util.List;

public final class MaxElem {

    private int max;

    public int getMax() {
        return max;
    }

    public List<int[]> getNodes() {
        return nodes;
    }

    private List<int[]> nodes;

    public MaxElem(int max, List<int[]> nodes) {
        super();
        this.max = max;
        this.nodes = nodes;
    }

}