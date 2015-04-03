/**
 * 
 */

/**
 * @author jpyla
 *
 */
public final class LastToFirst {

         String symbol;

         int index;

        public LastToFirst(String symbol, int index) {
            super();
            this.symbol = symbol;
            this.index = index;
        }
        
        public String toString () {
            return symbol + ":" + index;
        }

    }
