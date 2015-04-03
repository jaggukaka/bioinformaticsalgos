import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadPatternFromReaderSimple {

    private StringBuffer buffer;

    private BufferedReader br;

    private final int R;

    private int[][] dfa;

    private String pattern;

    private boolean first;

    private boolean nothingToRead;

    public ReadPatternFromReaderSimple(BufferedReader br, String pattern) {
        this.pattern = pattern;
        this.br = br;
        this.buffer = new StringBuffer();
        this.first = true;
        this.nothingToRead = false;

        this.R = 256;
        this.pattern = pattern;

        int M = pattern.length();
        dfa = new int[R][M];
        dfa[pattern.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];
            dfa[pattern.charAt(j)][j] = j + 1;
            X = dfa[pattern.charAt(j)][X];
        }
    }

    public String parseMatchingContent() throws IOException {

        if (this.nothingToRead) {
            return null;
        }
        if (first) {
            findMatch();
        }
        findMatch();
        String content = null;
        if (first) {
            content = buffer.substring(pattern.length());
            first = false;
        } else {
            content = buffer.toString();
        }

        int length = content.length();
        if (!this.nothingToRead) {
            content = content.substring(0, length - pattern.length());
        }
        this.buffer = new StringBuffer();

        return content.trim();
    }

    private void findMatch() throws IOException {
        // TODO Auto-generated method stub
        int M = pattern.length();

        int j = 0;
        while (j < M) {
            int c = br.read();

            if (c == -1) {
                this.nothingToRead = true;
                break;
            }
            buffer.append(String.valueOf((char) c));
            j = dfa[c][j];

        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(
            "/Users/jreddypyla/Documents/mystudy/workspace/ReadPatternSimple/src/testdata"));
        ReadPatternFromReaderSimple readerSimple = new ReadPatternFromReaderSimple(br, "<128>1");

        while (!readerSimple.nothingToRead) {
            System.out.println(readerSimple.parseMatchingContent());
            System.out.println();
        }
    }

}
