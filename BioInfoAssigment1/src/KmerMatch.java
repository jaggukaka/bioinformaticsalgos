import java.util.Map;

public final class KmerMatch {
    private Map<String, Integer> match;

    private int max;

    public void setMax(int max) {
        this.max = max;
    }

    public KmerMatch(Map<String, Integer> match, int max) {
        super();
        this.match = match;
        this.max = max;
    }

    public Map<String, Integer> getMatch() {
        return match;
    }

    public int getMax() {
        return max;
    }

    public void setMatch(Map<String, Integer> match) {
        this.match = match;
    }

   

}