package org.semanticweb.owlapi.functional.parser;

import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import static org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserConstants.*;

/**
 * Created by ses on 6/9/14.
 */
public class CustomTokenizer implements TokenManager {

    private final PushbackReader in;
    private boolean eofSeen = false;

    public CustomTokenizer(Reader reader) {
        if (reader instanceof PushbackReader) {
            PushbackReader pushbackReader = (PushbackReader) reader;
            this.in = pushbackReader;
        } else {
            this.in = new PushbackReader(reader);
        }
    }

    /**
     * This gets the next token from the input stream.
     * A token of kind 0 (<EOF>) should be returned on EOF.
     */
    @Override
    public Token getNextToken() {
        while (true) {
            char c;
            try {
                c = findTokenStart();
                switch (c) {
                    case '(':
                        return new Token(OPENPAR);
                    case ')':
                        return new Token(CLOSEPAR);
                    case '@':
                        return new Token(LANGIDENTIFIER);
                    case '^':
                        c = readChar();
                        if(c == '^') {
                          return new Token(DATATYPEIDENTIFIER);
                        }  else {
                            return new Token(ERROR,"^" + c);
                        }
                    case '=':
                        return new Token(EQUALS);
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
            } catch (IOException e) {
                return new Token(EOF);
            }
        }
    }

    private void skipComment() throws IOException {
        for(char c = readChar(); c != '\n'; c = readChar()) {

        }

    }

    private Token readStringLiteralToken() throws IOException {
        StringBuilder buf = new StringBuilder();
        char c = readChar();
        while (true) {
            switch(c) {
                case '"':
                    return new Token(STRINGLITERAL,buf.toString());
                case '\\':
                    c = readChar();
                    if(c != '\\' && c != '\"') {
                        return new Token(ERROR,"Bad escape sequence in StringLiteral");
                    }
                    // fallthrough
                default:
                    buf.append(c);
            }
        }
    }

    private Token readFullIRI() {
        return null;
    }

    private Token readTextualToken(char c) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append(c);
        int colonIndex = -1;
        loop:
        while (true) {
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
                        in.unread(c);
                        break loop;
                    case ':':
                        colonIndex = buf.length();  // and fall through
                    default:
                        buf.append(c);
                }
            } catch (EOFException eof) {
                eofSeen = true;
                break;
            }
        }
        if(colonIndex >=0) {
            System.out.println("colonIndex >=0 - so expect abbreviated IRI from " + buf);
        }
        throw new NoSuchMethodError("not done");
    }


    private char findTokenStart() throws IOException {
        while (true) {
            char c = readChar();
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return c;
            }
        }
    }

    private char readChar() throws IOException {
        if (eofSeen) {
            throw new EOFException();
        }
        int c = in.read();
        if (c < 0) {
            eofSeen = true;
            throw new EOFException();
        } else {
            return (char) c;
        }
    }
}
