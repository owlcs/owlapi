package org.semanticweb.owlapi6.functional.parser;

import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLOSEPAR;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPEIDENTIFIER;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUALS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ERROR;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.FULLIRI;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.LANGIDENTIFIER;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NODEID;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OPENPAR;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.PNAME_NS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.STRINGLITERAL;

import java.io.EOFException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ses on 6/9/14.
 */
class CustomTokenizer implements TokenManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTokenizer.class);
    private int unreadChar = -1;
    private final Provider in;
    private boolean eofSeen = false;
    private final StringBuilder buf = new StringBuilder();
    private int lineNo = 1;
    private int colNo = 0;
    private int startLine = -1;
    private int startCol = -1;
    // hack to adapt from Provider to CustomTokenizer. The Provider is already
    // buffered, so little
    // performance impact.
    private final char[] charReading = new char[1];

    public CustomTokenizer(Provider reader) {
        in = reader;
    }

    /**
     * This gets the next token from the input stream. A token of kind 0 (EOF) should be returned on
     * EOF.
     */
    @Override
    public Token getNextToken() {
        while (true) {
            char c;
            try {
                c = findTokenStart();
                switch (c) {
                    case '(':
                        return makeToken(OPENPAR, "(");
                    case ')':
                        return makeToken(CLOSEPAR, ")");
                    case '@':
                        return makeToken(LANGIDENTIFIER, "@");
                    case '^':
                        c = readChar();
                        if (c == '^') {
                            return makeToken(DATATYPEIDENTIFIER, "^^");
                        } else {
                            return makeToken(ERROR, "^" + c);
                        }
                    case '=':
                        return makeToken(EQUALS, "=");
                    case '#':
                        skipComment();
                        break;
                    case '"':
                        return readStringLiteralToken();
                    case '<':
                        return readFullIRI();
                    default:
                        return readTextualToken(c);
                }
            } catch (@SuppressWarnings("unused") EOFException e) {
                return makeToken(EOF, "");
            } catch (IOException e) {
                LOGGER.warn("IOException reading from functioanl stream", e);
                return makeToken(EOF, "");
            }
        }
    }

    private void skipComment() throws IOException {
        for (char c = readChar(); c != '\n'; c = readChar()) {
            // read to end of line
        }
    }

    private Token readStringLiteralToken() throws IOException {
        buf.setLength(0);
        buf.append('"');
        while (true) {
            char c = readChar();
            switch (c) {
                case '"':
                    buf.append(c);
                    return makeToken(STRINGLITERAL, buf.toString());
                case '\\':
                    buf.append(c);
                    c = readChar();
                    if (c != '\\' && c != '\"') {
                        return makeToken(ERROR, "Bad escape sequence in StringLiteral");
                    }
                    // fallthrough
                    // $FALL-THROUGH$
                default:
                    buf.append(c);
            }
        }
    }

    private Token readFullIRI() {
        try {
            buf.setLength(0);
            buf.append('<');
            while (true) {
                char c = readChar();
                buf.append(c);
                if (c == '>') {
                    return makeToken(FULLIRI, buf.toString());
                }
            }
        } catch (IOException e) {
            LOGGER.warn("IOException reading from functioanl stream", e);
            return makeToken(ERROR, "<");
        }
    }

    private Token readTextualToken(char input) throws IOException {
        char c = input;
        if (c >= '0' && c <= '9') {
            return readNumber(c);
        }
        buf.setLength(0);
        buf.append(c);
        int colonIndex = -1;
        if (c == ':') {
            colonIndex = 0;
        }
        loop: while (true) {
            try {
                c = readChar();
                switch (c) {
                    case '=':
                    case '"':
                    case '(':
                    case ')':
                    case '<':
                    case '>':
                    case '@':
                    case '^':
                    case '\r':
                    case '\n':
                    case ' ':
                    case '\t':
                        unread(c);
                        break loop;
                    case ':':
                        colonIndex = buf.length(); // and fall through
                        //$FALL-THROUGH$
                    default:
                        buf.append(c);
                }
            } catch (@SuppressWarnings("unused") EOFException eof) {
                break;
            }
        }
        String s = buf.toString();
        if (colonIndex >= 0) {
            if (colonIndex == s.length() - 1) {
                return makeToken(PNAME_NS, s);
            } else {
                if (s.startsWith("_:")) {
                    return makeToken(NODEID, s);
                }
                return makeToken(OWLFunctionalSyntaxParserConstants.PNAME_LN, s);
            }
        }
        return makeToken(TokenMap.tokenIndex(s), s);
    }

    private Token readNumber(char input) throws IOException {
        char c = input;
        buf.setLength(0);
        buf.append(c);
        while (true) {
            c = readChar();
            if (!Character.isDigit(c)) {
                unread(c);
                return makeToken(OWLFunctionalSyntaxParserConstants.INT, buf.toString());
            } else {
                buf.append(c);
            }
        }
    }

    private Token makeToken(int kind, String image) {
        Token result = new Token(kind, image);
        result.beginLine = startLine;
        result.beginColumn = startCol;
        return result;
    }

    private char findTokenStart() throws IOException {
        while (true) {
            char c = readChar();
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                startLine = lineNo;
                startCol = colNo;
                return c;
            }
        }
    }

    private void unread(char c) {
        unreadChar = c;
        if (c != '\n') {
            colNo--;
        }
    }

    private char readChar() throws IOException {
        if (eofSeen) {
            throw new EOFException();
        }
        int c;
        if (unreadChar < 0) {
            c = in.read(charReading, 0, 1);
            if (c == 1) {
                c = charReading[0];
            } else {
                c = -1;
            }
            if (c == '\n') {
                lineNo++;
                colNo = 0;
            }
        } else {
            c = unreadChar;
            unreadChar = -1;
        }
        colNo++;
        if (c < 0) {
            eofSeen = true;
            throw new EOFException();
        } else {
            return (char) c;
        }
    }
}
