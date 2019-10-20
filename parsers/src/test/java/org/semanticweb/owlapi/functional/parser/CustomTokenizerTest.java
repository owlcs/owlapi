package org.semanticweb.owlapi.functional.parser;

import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class CustomTokenizerTest {

    static final Logger logger = LoggerFactory.getLogger(CustomTokenizer.class);

    @Test
    public void testParseStringLiteral() {
        validateTokenizationOfString("\"hello world\"", false);
        validateTokenizationOfString("\"hello \\\" world \"", false);
        validateTokenizationOfString("\"hello \\\\ world\"", false);
        validateTokenizationOfString("\"hello\\\\\"" + "\"world\"", false);
    }

    @Test
    public void testParseFullURI() {
        // Good
        validateTokenizationOfString("<http://www.unc.edu/onto#foo>", false);
        // Bad
        validateTokenizationOfString("<http://www.unc.edu/onto#foo", true);
    }

    private static void validateTokenizationOfString(String text, boolean errorExpected) {
        try (StringReader reader1 = new StringReader(text);
            StringReader reader2 = new StringReader(text);) {
            validateTokenization(reader1, errorExpected);
        }
    }

    private static void validateTokenization(Reader reader1, boolean errorExpected) {
        CustomTokenizer customTokenizer = createCustomTokenizer(reader1);
        while (true) {
            Token customToken = customTokenizer.getNextToken();
            // If the end of file occurs in the middle of a token, the existing
            // lexical grammar creates an error token,
            // then keeps going. None of the production rules match the error,
            // so this will quickly lead to a parse
            // exception when called from the generated parser; the tokenizer
            // just keeps on chugging, as it doesn't
            // know that the error is a lexical error.
            // With the newest versions of JavaCC, tokenization errors are
            // treated as exceptions, rather than can't
            // happen errors, however the generated code doesn't compile with a
            // custom lexer.
            boolean eof = customToken.kind == OWLFunctionalSyntaxParserConstants.EOF;
            boolean error = customToken.kind == OWLFunctionalSyntaxParserConstants.ERROR;
            if (eof || error) {
                if (eof && !errorExpected || errorExpected && error) {
                    return;
                }
                fail("Error expected: " + errorExpected + " but " + (eof ? "end of input" : "error")
                    + " found");
            }
        }
    }

    static CustomTokenizer createCustomTokenizer(String s) {
        StringReader reader = new StringReader(s);
        return createCustomTokenizer(reader);
    }

    private static CustomTokenizer createCustomTokenizer(Reader reader) {
        return new CustomTokenizer(reader);
    }
}
