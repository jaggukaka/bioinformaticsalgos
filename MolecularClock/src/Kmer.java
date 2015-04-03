
public final class Kmer implements Comparable<Kmer> {

    String kmer;

    int count;

    public Kmer(String kmer, int count) {
        super();
        this.kmer = kmer;
        this.count = count;
    }

    @Override
    public int compareTo(Kmer that) {
        // TODO Auto-generated method stub
        if (that.count > this.count) {
            return -1;
        } else if (that.count == this.count) {
            return 0;
        } else {
            return +1;
        }
    }

}