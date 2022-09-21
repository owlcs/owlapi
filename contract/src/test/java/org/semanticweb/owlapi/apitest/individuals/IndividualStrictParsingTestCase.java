package org.semanticweb.owlapi.apitest.individuals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.rioformats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.rioformats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.rioformats.TrigDocumentFormat;

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
