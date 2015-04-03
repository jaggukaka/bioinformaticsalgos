public class StringMatch {
    private final CharSequence pattern;
    private final int startIndex;
    private final int endIndex;

    /**
     * start index of the match found
     * @return - {@link Integer}
     */
    public int getStartIndex() {
        return startIndex;
    }
    
    /**
     * end index of the match found
     * @return - {@link Integer}
     */
    public int getEndIndex() {
        return endIndex;
    }
    
    /**
     * Pattern being searched.
     * @return - {@link CharSequence}
     */
    public CharSequence getPattern() {
        return pattern;
    }
    
    /**
     * constructor
     * @param mypattern - {@link CharSequence}
     * @param start - {@link Integer}
     * @param end - {@link Integer}
     */
    public StringMatch(CharSequence mypattern, int start, int end) {
        super();
        pattern = mypattern;
        startIndex = start;
        endIndex = end;
    }
    
}
