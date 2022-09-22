package org.semanticweb.owlapi.riotest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.krss2.parser.KRSS2OWLParserFactory;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory;
import org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParserFactory;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParserFactory;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParserFactory;
import org.semanticweb.owlapi.rio.RioBinaryRdfParserFactory;
import org.semanticweb.owlapi.rio.RioJsonLDParserFactory;
import org.semanticweb.owlapi.rio.RioJsonParserFactory;
import org.semanticweb.owlapi.rio.RioN3ParserFactory;
import org.semanticweb.owlapi.rio.RioNQuadsParserFactory;
import org.semanticweb.owlapi.rio.RioNTriplesParserFactory;
import org.semanticweb.owlapi.rio.RioRDFXMLParserFactory;
import org.semanticweb.owlapi.rio.RioRDFaParserFactory;
import org.semanticweb.owlapi.rio.RioTrigParserFactory;
import org.semanticweb.owlapi.rio.RioTrixParserFactory;
import org.semanticweb.owlapi.rio.RioTurtleParserFactory;
import org.semanticweb.owlapi.utilities.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class OWLParserFactoryRegistryTestCase extends TestBase {

    @Test
    void setUp() {
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
        PriorityCollection<OWLParserFactory> ontologyParsers = setupManager().getOntologyParsers();
        Set<Class<? extends OWLParserFactory>> found = new HashSet<>();
        for (OWLParserFactory p : ontologyParsers) {
            found.add(p.getClass());
        }
        for (Class<? extends OWLParserFactory> p : factories) {
            assertTrue(found.contains(p), "Expected among parsers: " + p.getSimpleName());
        }
    }
}
