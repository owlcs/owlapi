package org.semanticweb.owlapi.rio;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.krss2.parser.KRSS2OWLParserFactory;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory;
import org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParserFactory;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParserFactory;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParserFactory;
import org.semanticweb.owlapi.util.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class OWLParserFactoryRegistryTestCase {

    @Test
    public void setUp() {
        // this test used to count the parsers. However, the extra parser in the
        // compatibility package will show up here in Eclipse tests, creating
        // confusion
        // Switched to list the expected parsers anc checking they all appear.
        // Any extra ones are welcome.
        Set<Class<? extends OWLParserFactory>> factories = new HashSet<>();
        factories.add(RDFXMLParserFactory.class);
        factories.add(OWLXMLParserFactory.class);
        factories.add(OWLFunctionalSyntaxOWLParserFactory.class);
        factories.add(TurtleOntologyParserFactory.class);
        factories.add(ManchesterOWLSyntaxOntologyParserFactory.class);
        factories.add(OBOFormatOWLAPIParserFactory.class);
        factories.add(KRSS2OWLParserFactory.class);
        factories.add(RioTurtleParserFactory.class);
        factories.add(RioNQuadsParserFactory.class);
        factories.add(RioJsonParserFactory.class);
        factories.add(RioNTriplesParserFactory.class);
        factories.add(RioTrigParserFactory.class);
        factories.add(RioBinaryRdfParserFactory.class);
        factories.add(RioJsonLDParserFactory.class);
        factories.add(RioN3ParserFactory.class);
        factories.add(RioRDFXMLParserFactory.class);
        factories.add(RioTrixParserFactory.class);
        factories.add(RioRDFaParserFactory.class);
        PriorityCollection<OWLParserFactory> ontologyParsers =
            OWLManager.createOWLOntologyManager().getOntologyParsers();
        Set<Class<? extends OWLParserFactory>> found = new HashSet<>();
        for (OWLParserFactory p : ontologyParsers) {
            found.add(p.getClass());
        }
        for (Class<? extends OWLParserFactory> p : factories) {
            assertTrue("Expected among parsers: " + p.getSimpleName(), found.contains(p));
        }
    }
}
