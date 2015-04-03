
public class SimpleLongestCommonExtension {
	
	public static int longestCommonExtension(String t1, int i1, String t2, int i2) {
	    int res = 0;
	    for (int i = i1; i < t1.length() && i2 < t2.length(); i++, i2++) {
	        if (t1.charAt(i) == t2.charAt(i2))
	            res++;
	        else
	            return res;
	    }
	    return res;
	}

	
	public static void main(String args[]) {
	    int res = longestCommonExtension("zsdabcdefghj", 3, "abcdezas", 0);
	    System.out.println(res);
	}
}
