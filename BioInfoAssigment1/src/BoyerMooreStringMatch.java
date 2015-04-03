import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author jpyla
 *
 */
public class BoyerMooreStringMatch {
    

private short[] charlastOccurence;
    
    /**
     * constructor
     */
    public BoyerMooreStringMatch() {
        charlastOccurence = new short[256];

        for ( short i = 0; i < charlastOccurence.length; i++ ) {
            charlastOccurence[i] = -1;
        }
    }

  
    public List<StringMatch> search( String text, int startIndex, int endIndex, String pattern ) {
        assert text != null && text.length() > 0 : "text cannot be null";
        assert startIndex >= 0 : "index should be a positive whole number";
        assert endIndex > 0 : "index should be a positive integer";

        
        for ( short j = 0; j < pattern.length(); j++ ) {
            charlastOccurence[pattern.charAt( j )] = j;
        }
        
        List<StringMatch> matches = new ArrayList<StringMatch>();

        while ( startIndex <= endIndex - pattern.length() ) {

            int c = 0;
            int i = pattern.length() - 1;
            int patternchar = pattern.charAt( i );
            c = text.charAt( startIndex + i ) ;
            //System.out.println("patternchar=" + patternchar + " C=" + c);
            while ( i >= 0 &&  patternchar == c ) {
                //System.out.println("inside loop when macthing patternchar=" + patternchar + " C=" + c);
                if ( i == 0 ) {
                    //System.out.println("also printing match index inside " + (startIndex + pattern.length() -1));
                    matches.add(new StringMatch( pattern, startIndex, startIndex + pattern.length() -1 ));
                    break;
                }
                --i;
                patternchar = pattern.charAt( i );
                c = text.charAt( startIndex + i ) ;
            }
            //System.out.println("inside loop after macthing patternchar=" + patternchar + " C=" + c);
//            if (c < 0 || c > 255) {
//                startIndex++;
//                continue;
//            }

            startIndex = startIndex + Math.max( i - charlastOccurence[c], 1 );

        }
        return matches;
    }
    
    
    
    public static void main(String args[]) {
        In in = new In(args[0]);

        String pattern = in.readString();
        String text = in.readString();
        
        BoyerMooreStringMatch boyerMooreStringMatch = new BoyerMooreStringMatch();
        List<StringMatch> matches = boyerMooreStringMatch.search(text, 0, text.length() -1, pattern);
        StringBuffer buffer = new StringBuffer();
        for (StringMatch match : matches) {
             buffer.append(match.getStartIndex() + " ");
        }
        System.out.println(buffer.toString());
    }
    


}
