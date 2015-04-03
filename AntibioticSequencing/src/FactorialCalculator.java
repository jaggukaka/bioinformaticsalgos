import java.math.BigInteger;

/**
 * 
 */

/**
 * @author jpyla
 * 
 */
public class FactorialCalculator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        while (!StdIn.isEmpty()) {
            String integer = StdIn.readString();
            BigInteger bigint = new BigInteger(integer);
            System.out.println(factorial(bigint));
        }

    }

    private static BigInteger factorial(BigInteger bigint) {
        BigInteger factorial = bigint;
        BigInteger terminal = bigint;
        BigInteger one = new BigInteger("1");
        while (true) {
            terminal = terminal.subtract(one);
            if (terminal.equals(one)) {
                break;
            }
            factorial = factorial.multiply(terminal);
            
        }

        return factorial;
    }
    
    public static long factorial(int bigint) {
        long factorial = factorial(new BigInteger(String.valueOf(bigint))).longValue();
        return factorial;
    }

}
