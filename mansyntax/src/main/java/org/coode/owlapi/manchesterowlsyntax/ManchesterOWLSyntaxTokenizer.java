package org.coode.owlapi.manchesterowlsyntax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19-May-2008<br><br>
 */
public class ManchesterOWLSyntaxTokenizer {

    public static final String EOF = "|EOF|";

    protected Set<Character> skip = new HashSet<Character>();

    protected Set<Character> commentDelimiters = new HashSet<Character>();

    protected Set<Character> delims = new HashSet<Character>();

    private String buffer;

    private int pos;

    private int col;

    private int row;

    int startPos = 0;

    int startCol = 1;

    int startRow = 1;

    List<Token> tokens = new ArrayList<Token>();

    private StringBuilder sb;

    private static final char ESCAPE_CHAR = '\\';


    public ManchesterOWLSyntaxTokenizer(String buffer) {
        this.buffer = buffer;
        skip.add(' ');
        skip.add('\n');
        skip.add('\r');
        skip.add('\t');
        commentDelimiters.add('#');
        commentDelimiters.add('*');
        delims.add('(');
        delims.add(')');
        delims.add('[');
        delims.add(']');
        delims.add(',');
        delims.add('{');
        delims.add('}');
        delims.add('^');
        delims.add('@');
        delims.add('<');
        delims.add('>');
        delims.add('=');
        delims.add('?');
    }


    private void reset() {
        sb = new StringBuilder();
        tokens.clear();
        startRow = 1;
        startCol = 1;
        startPos = 0;
        pos = 0;
        row = 1;
        col = 1;
    }


    public List<Token> tokenize() {
        reset();
        int bufferLen = buffer.length();
        char lastChar = ' ';
        while (pos < bufferLen) {
            char ch = readChar();
            if (ch == ESCAPE_CHAR) {
                lastChar = ch;
                ch = readChar();
            }
            if (ch == '\"' && lastChar != '\\') {
                readString('\"', true);
            }
            else if (ch == '\'' && lastChar != '\\') {
                readString('\'', true);
            }
            else if (ch == '<') {
                // Potentially the start of an IRI
                readIRI();
            }
            else if (skip.contains(ch)) {
                consumeToken();
            }
            else if (commentDelimiters.contains(ch)) {
                consumeToken();
                readComment();
            }
            else if (delims.contains(ch)) {
                consumeToken();
                sb.append(ch);
                if (ch != '@') {
                    consumeToken();
                }
            }
            else {
                sb.append(ch);
            }
            lastChar = ch;
        }
        consumeToken();
        tokens.add(new Token(EOF, pos, col, row));
        return new ArrayList<Token>(tokens);
    }


    private void consumeToken() {
        if (sb.length() > 0) {
            tokens.add(new Token(sb.toString(), startPos, startCol, startRow));
            sb = new StringBuilder();
        }
        startPos = pos;
        startCol = col;
        startRow = row;
    }

    private void readComment() {
        char ch = '#';
        while(ch != '\n' && pos < buffer.length()) {
            ch = readChar();
        }
        consumeToken();
    }

    private void readString(char terminator, boolean appendTerminator) {
        if (appendTerminator) {
            sb.append(terminator);
        }
        while (pos < buffer.length()) {
            char ch = readChar();
            if (ch == ESCAPE_CHAR) {
                int j = pos + 1;
                if (j < buffer.length()) {
                    char escapedChar = readChar();
                    if (escapedChar == '\"' || escapedChar == '\'' || escapedChar == '\\') {
                        sb.append(escapedChar);
                    }
                    else {
                        sb.append(ch);
                        sb.append(escapedChar);
                    }
                }
                else {
                    sb.append('\\');
                }
            }
            else if (ch == terminator) {
                if (appendTerminator) {
                    sb.append(ch);
                }
                break;
            }
            else {
                sb.append(ch);
            }
        }
        consumeToken();
    }

    private void readIRI() {
        sb = new StringBuilder("<");
        int startPos = pos;
        while (pos < buffer.length()) {
            char ch = readChar();
            if(Character.isWhitespace(ch)) {
                // Not an IRI -- go back to where we started
                pos = startPos;
                sb = new StringBuilder("<");
                consumeToken();
                break;
            }
            else if(ch == '>') {
                // End of IRI
                sb.append(">");
                consumeToken();
                break;
            }
            else {
                sb.append(ch);
            }
        }
    }


    private char readChar() {
        char ch = buffer.charAt(pos);
        pos++;
        col++;
        if (ch == '\n') {
            row++;
            col = 0;
        }
        return ch;
    }


    public static class Token {

        private String token;

        private int pos;

        private int col;

        private int row;


        public Token(String token, int pos, int col, int row) {
            this.token = token;
            this.pos = pos;
            this.col = col;
            this.row = row;
        }


        public String getToken() {
            return token;
        }


        public int getPos() {
            return pos;
        }


        public int getCol() {
            return col;
        }


        public int getRow() {
            return row;
        }


        @Override
		public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(token);
            sb.append(" [");
            sb.append(pos);
            sb.append(", ");
            sb.append(col);
            sb.append(", ");
            sb.append(row);
            sb.append("]");
            return sb.toString();
        }
    }
}
