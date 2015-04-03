

import java.util.ArrayList;
import java.util.Collection;

public class MatchingWithAllowedMismatch {
	public static Collection<String> getMatches(String t, String p, int k) {
		Collection<String> result = new ArrayList<String>();
		for (int i = 0; i < t.length(); i++) {
			
			int j = 0;
			int h = i;
			int count = 0;
			int n = p.length();

			while (true) {
				
				int L = SimpleLongestCommonExtension.longestCommonExtension(p, j, t, h);
				
				if (j + 1 + L == n + 1 && i+n <= t.length()) {
				    result.add(t.substring(i, i + n));
				    break;
				}
				
				else if (count < k) {
				    count++;
				    j = j + L + 1;
				    h = h + L + 1;
				} else if (count == k) {
				    break;
				}
			}
		}
		return result;
	}
	
	public static void main(String args[]) {
		
		Collection<String> results = getMatches("AACAAGCTGATAAACATTTAAAGAG", "AAAAA", 1);
		System.out.println( results.size());
	}
}
