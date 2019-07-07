package org.semanticweb.owlapi.functional.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Ignore;
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

    @Ignore
    @Test
    public void testTokenizeGeneOntology() throws Exception {
        String fileName = "/Users/ses/ontologies/GO/go.ofn";
        Reader in = new BufferedReader(new FileReader(fileName));
        Reader in2 = new BufferedReader(new FileReader(fileName));
        validateTokenization(in, in2, false);
    }

    private static void validateTokenizationOfString(String text, boolean errorExpected) {
        try (StringReader reader1 = new StringReader(text);
            StringReader reader2 = new StringReader(text);) {
            validateTokenization(reader1, reader2, errorExpected);
        }
    }

    void validateTokenizationOfResource(String resourceName, boolean errorExpected)
        throws IOException {
        try (Reader reader1 = new InputStreamReader(getClass().getResourceAsStream(resourceName));
            Reader reader2 = new InputStreamReader(getClass().getResourceAsStream(resourceName))) {
            validateTokenization(reader1, reader2, errorExpected);
        }
    }

    private static void validateTokenization(Reader reader1, Reader reader2,
        boolean errorExpected) {
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

    private static void assertTokensMatch(Token expected, Token actual) {
        try {
            assertNotNull(expected);
            assertNotNull(actual);
            assertEquals(expected.kind, actual.kind);
            assertEquals(expected.image, actual.image);
            assertEquals(expected.getValue(), actual.getValue());
        } catch (AssertionError e) {
            String expected1 = OWLFunctionalSyntaxParserConstants.tokenImage[expected.kind];
            String actual1 = OWLFunctionalSyntaxParserConstants.tokenImage[actual.kind];
            logger.error("token match fail: expected " + expected1 + ", actual " + actual1);
            throw e;
        }
    }
}
