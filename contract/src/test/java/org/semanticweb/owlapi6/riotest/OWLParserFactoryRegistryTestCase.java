package org.semanticweb.owlapi6.riotest;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxOWLParserFactory;
import org.semanticweb.owlapi6.io.OWLParserFactory;
import org.semanticweb.owlapi6.krss2.parser.KRSS2OWLParserFactory;
import org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntaxOntologyParserFactory;
import org.semanticweb.owlapi6.oboformat.OBOFormatOWLAPIParserFactory;
import org.semanticweb.owlapi6.owlxml.parser.OWLXMLParserFactory;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi6.rdf.turtle.parser.TurtleOntologyParserFactory;
import org.semanticweb.owlapi6.rio.RioBinaryRdfParserFactory;
import org.semanticweb.owlapi6.rio.RioJsonLDParserFactory;
import org.semanticweb.owlapi6.rio.RioJsonParserFactory;
import org.semanticweb.owlapi6.rio.RioN3ParserFactory;
import org.semanticweb.owlapi6.rio.RioNQuadsParserFactory;
import org.semanticweb.owlapi6.rio.RioNTriplesParserFactory;
import org.semanticweb.owlapi6.rio.RioRDFXMLParserFactory;
import org.semanticweb.owlapi6.rio.RioRDFaParserFactory;
import org.semanticweb.owlapi6.rio.RioTrigParserFactory;
import org.semanticweb.owlapi6.rio.RioTrixParserFactory;
import org.semanticweb.owlapi6.rio.RioTurtleParserFactory;
import org.semanticweb.owlapi6.utilities.PriorityCollection;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
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
