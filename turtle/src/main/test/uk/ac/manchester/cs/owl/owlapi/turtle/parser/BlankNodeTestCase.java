package uk.ac.manchester.cs.owl.owlapi.turtle.parser;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.TestCase;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Jan-2010
 */
public class BlankNodeTestCase extends TestCase {

    public void testBlankNodes() throws Exception {
        try {
            Reader input = new StringReader( "_:foo <http://example.com/> _:bar ." );
            TurtleParser parser = new TurtleParser( input, new ConsoleTripleHandler(), "" );
            parser.parseDocument();
            assertTrue(true);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
