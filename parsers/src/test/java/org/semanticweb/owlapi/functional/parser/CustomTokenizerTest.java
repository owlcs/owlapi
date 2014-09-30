package org.semanticweb.owlapi.functional.parser;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserConstants.*;

import java.io.StringReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class CustomTokenizerTest {

    static final Logger LOGGER = LoggerFactory.getLogger(CustomTokenizer.class);

    @Test
    public void testParseStringLiteral() {
        validateTokenizationOfString("\"hello world\"");
        validateTokenizationOfString("\"hello \\\" world \"");
        validateTokenizationOfString("\"hello \\\\ world\"");
        validateTokenizationOfString("\"hello\\\\\"" + "\"world\"");
    }

    @Test
    public void testParseFullURI() {
        // Good
        validateTokenizationOfString("<http://www.unc.edu/onto#foo>");
        // Bad
        validateTokenizationOfString("<http://www.unc.edu/onto#foo");
    }

    private static void validateTokenizationOfString(String text) {
        try (StringReader reader1 = new StringReader(text);
                StringReader reader2 = new StringReader(text);) {
            CustomTokenizer customTokenizer = new CustomTokenizer(reader1);
            OWLFunctionalSyntaxParserTokenManager tokenManager = new OWLFunctionalSyntaxParserTokenManager(
                    new SimpleCharStream(reader2));
            Token customToken = customTokenizer.getNextToken();
            Token generatedToken = tokenManager.getNextToken();
            while (generatedToken.kind != EOF && generatedToken.kind != ERROR) {
                assertNotNull(generatedToken);
                assertNotNull(customToken);
                assertEquals(generatedToken.kind, customToken.kind);
                assertEquals(generatedToken.image, customToken.image);
                assertEquals(generatedToken.getValue(), customToken.getValue());
                // If the end of file occurs in the middle of a token, the
                // existing lexical grammar creates an error token, then keeps
                // going. None of the production rules match the error, so this
                // will quickly lead to a parse exception when called from the
                // generated parser; the tokenizer just keeps on chugging, as it
                // doesn't know that the error is a lexical error. With the
                // newest versions of JavaCC, tokenization errors are treated as
                // exceptions, rather than can't happen errors, however the
                // generated code doesn't compile with a custom lexer.
                customToken = customTokenizer.getNextToken();
                generatedToken = tokenManager.getNextToken();
            }
        }
    }
}
