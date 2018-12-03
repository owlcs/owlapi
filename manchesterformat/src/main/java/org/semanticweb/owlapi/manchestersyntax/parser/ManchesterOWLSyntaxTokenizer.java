/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.manchestersyntax.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class ManchesterOWLSyntaxTokenizer {

    /**
     * EOF.
     */
    public static final String EOFTOKEN = "|EOF|";
    private static final char ESCAPE_CHAR = '\\';
    protected final Set<Character> skip = new HashSet<>();
    protected final Set<Character> commentDelimiters = new HashSet<>();
    protected final Set<Character> delims = new HashSet<>();
    private final String buffer;
    int startPos = 0;
    int startCol = 1;
    int startRow = 1;
    List<Token> tokens = new ArrayList<>();
    private int pos;
    private int col;
    private int row;
    private StringBuilder sb = new StringBuilder();

    /**
     * @param buffer buffer
     */
    public ManchesterOWLSyntaxTokenizer(String buffer) {
        this.buffer = buffer;
        skip.add(Character.valueOf(' '));
        skip.add(Character.valueOf('\n'));
        skip.add(Character.valueOf('\r'));
        skip.add(Character.valueOf('\t'));
        commentDelimiters.add(Character.valueOf('#'));
        commentDelimiters.add(Character.valueOf('*'));
        delims.add(Character.valueOf('('));
        delims.add(Character.valueOf(')'));
        delims.add(Character.valueOf('['));
        delims.add(Character.valueOf(']'));
        delims.add(Character.valueOf(','));
        delims.add(Character.valueOf('{'));
        delims.add(Character.valueOf('}'));
        delims.add(Character.valueOf('^'));
        delims.add(Character.valueOf('@'));
        delims.add(Character.valueOf('<'));
        delims.add(Character.valueOf('>'));
        delims.add(Character.valueOf('='));
        delims.add(Character.valueOf('?'));
    }

    /**
     * @param s string to check
     * @return true if EOF
     */
    public static boolean eof(String s) {
        return EOFTOKEN.equals(s);
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

    /**
     * @return tokens
     */
    public List<Token> tokenize() {
        reset();
        int bufferLen = buffer.length();
        char lastChar = ' ';
        while (pos < bufferLen) {
            lastChar = handleChar(lastChar);
        }
        consumeToken();
        tokens.add(new Token(EOFTOKEN, pos, col, row));
        return new ArrayList<>(tokens);
    }

    protected char handleChar(char last) {
        char lastChar = last;
        char ch = readChar();
        if (ch == ESCAPE_CHAR) {
            lastChar = ch;
            ch = readChar();
        }
        if (ch == '\"' && lastChar != '\\') {
            readString('\"', true);
        } else if (ch == '\'' && lastChar != '\\') {
            readString('\'', true);
        } else if (ch == '<') {
            // Potentially the start of an IRI
            readIRI();
        } else if (skip.contains(Character.valueOf(ch))) {
            consumeToken();
        } else if (commentDelimiters.contains(Character.valueOf(ch))) {
            consumeToken();
            readComment();
        } else if (delims.contains(Character.valueOf(ch))) {
            consumeToken();
            sb.append(ch);
            if (ch != '@') {
                consumeToken();
            }
        } else {
            sb.append(ch);
        }
        return ch;
    }

    private void consumeToken() {
        if (sb.length() > 0) {
            String string = sb.toString();
            tokens.add(new Token(string, startPos, startCol, startRow));
            sb = new StringBuilder();
        }
        startPos = pos;
        startCol = col;
        startRow = row;
    }

    private void readComment() {
        char ch = '#';
        while (ch != '\n' && pos < buffer.length()) {
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
                handleEscapeChar(ch, j);
            } else if (ch == terminator) {
                if (appendTerminator) {
                    sb.append(ch);
                }
                break;
            } else {
                sb.append(ch);
            }
        }
        consumeToken();
    }

    protected void handleEscapeChar(char ch, int j) {
        if (j < buffer.length()) {
            char escapedChar = readChar();
            if (escapedChar == '\"' || escapedChar == '\'' || escapedChar == '\\') {
                sb.append(escapedChar);
            } else {
                sb.append(ch);
                sb.append(escapedChar);
            }
        } else {
            sb.append('\\');
        }
    }

    private void readIRI() {
        sb = new StringBuilder("<");
        int startPos1 = pos;
        while (pos < buffer.length()) {
            char ch = readChar();
            if (Character.isWhitespace(ch)) {
                // Not an IRI -- go back to where we started
                pos = startPos1;
                sb = new StringBuilder("<");
                consumeToken();
                return;
            } else if (ch == '>') {
                // End of IRI
                sb.append('>');
                consumeToken();
                return;
            } else {
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

    /**
     * Token.
     */
    public static class Token {

        private final String currentToken;
        private final int pos;
        private final int col;
        private final int row;

        /**
         * @param token token
         * @param pos pos
         * @param col col
         * @param row row
         */
        public Token(String token, int pos, int col, int row) {
            currentToken = token;
            this.pos = pos;
            this.col = col;
            this.row = row;
        }

        /**
         * @return token
         */
        public String getToken() {
            return currentToken;
        }

        /**
         * @return position
         */
        public int getPos() {
            return pos;
        }

        /**
         * @return column
         */
        public int getCol() {
            return col;
        }

        /**
         * @return row
         */
        public int getRow() {
            return row;
        }

        @Override
        public String toString() {
            return currentToken + " [" + pos + ", " + col + ", " + row + ']';
        }
    }
}
