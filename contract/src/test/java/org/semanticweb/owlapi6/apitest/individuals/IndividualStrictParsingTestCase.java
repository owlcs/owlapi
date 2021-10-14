package org.semanticweb.owlapi6.apitest.individuals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.rioformats.NQuadsDocumentFormat;
import org.semanticweb.owlapi6.rioformats.NTriplesDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi6.rioformats.TrigDocumentFormat;

class IndividualStrictParsingTestCase extends TestBase {

    @Test
    void should() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();

        OWLObjectProperty p = df.getOWLObjectProperty("http://purl.obolibrary.org/obo/BFO_0000051");
        OWLClass c = df.getOWLClass("http://purl.obolibrary.org/obo/ENVO_00000191");
        OWLNamedIndividual individual =
            df.getOWLNamedIndividual("https://www.wikidata.org/wiki/Q2306597");

        o.add(df.getOWLDeclarationAxiom(p), df.getOWLDeclarationAxiom(c),
            df.getOWLDeclarationAxiom(individual),
            df.getOWLClassAssertionAxiom(df.getOWLObjectSomeValuesFrom(p, c), individual));
        OntologyConfigurator conf = new OntologyConfigurator().setStrict(true);
        roundTrip(o, new RDFXMLDocumentFormat(), conf);
        roundTrip(o, new RioRDFXMLDocumentFormat(), conf);
        roundTrip(o, new RDFJsonDocumentFormat(), conf);
        roundTrip(o, new OWLXMLDocumentFormat(), conf);
        roundTrip(o, new FunctionalSyntaxDocumentFormat(), conf);
        roundTrip(o, new TurtleDocumentFormat(), conf);
        roundTrip(o, new RioTurtleDocumentFormat(), conf);
        roundTrip(o, new ManchesterSyntaxDocumentFormat(), conf);
        roundTrip(o, new TrigDocumentFormat(), conf);
        roundTrip(o, new RDFJsonLDDocumentFormat(), conf);
        roundTrip(o, new NTriplesDocumentFormat(), conf);
        roundTrip(o, new NQuadsDocumentFormat(), conf);
    }
}
