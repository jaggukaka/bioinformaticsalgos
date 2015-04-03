/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class MinimumSkew {

    private static final char SPACE = ' ';

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        In in = new In(args[0]);

        String data = in.readString();
        MinimumSkew minimumSkew = new MinimumSkew();
        System.out.println(minimumSkew.findMinSkew(data));
    }

    public String findMinSkew(String data) {
        int min = data.length();
        int count = 0;
        int lastI = 0;

        MinPQ<MinimumSkew.MinIndex> minPQ = new MinPQ<MinimumSkew.MinIndex>();

        for (int i = 0; i < data.length(); i++) {
            int dataChar = data.charAt(i);
            if (dataChar == 'G') {
                if (min >= count) {
                    min = count;
                    minPQ.insert(new MinIndex(min, lastI, i));
                }
                count++;
            } else if (dataChar == 'C') {
                count--;
                lastI = i;
            }

        }

        if (min >= count) {
            min = count;
            minPQ.insert(new MinIndex(min, lastI, data.length() - 1));
        }
        StringBuffer buffer = new StringBuffer();
        while (!minPQ.isEmpty()) {
            MinIndex minIndex = minPQ.delMin();
            if (minIndex.value > min) {
                break;
            } else {
                for (int j = minIndex.index + 1; j <= minIndex.lastShiftIndex; j++) {
                    buffer.append(j);
                    buffer.append(SPACE);
                }
            }
        }

        return buffer.toString();
    }

    private final class MinIndex implements Comparable<MinIndex> {
        private int value;

        private int index;

        private int lastShiftIndex;

        public MinIndex(int value, int index, int lastShiftIndex) {
            super();
            this.value = value;
            this.index = index;
            this.lastShiftIndex = lastShiftIndex;
        }

        @Override
        public int compareTo(MinIndex o) {
            if (this.value < o.value) {
                return -1;
            } else if (this.value == o.value) {
                return 0;
            } else {
                return +1;
            }
        }

    }

}
