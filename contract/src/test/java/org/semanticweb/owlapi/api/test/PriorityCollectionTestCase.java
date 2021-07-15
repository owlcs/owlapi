package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxStorerFactory;
import org.semanticweb.owlapi.krss2.renderer.KRSS2OWLSyntaxStorerFactory;
import org.semanticweb.owlapi.latex.renderer.LatexStorerFactory;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterSyntaxStorerFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLStorer;
import org.semanticweb.owlapi.model.PriorityCollectionSorting;
import org.semanticweb.owlapi.oboformat.OBOFormatStorerFactory;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLStorerFactory;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLStorerFactory;
import org.semanticweb.owlapi.rdf.turtle.renderer.TurtleStorerFactory;
import org.semanticweb.owlapi.rio.RioBinaryRdfStorerFactory;
import org.semanticweb.owlapi.rio.RioJsonLDStorerFactory;
import org.semanticweb.owlapi.rio.RioJsonStorerFactory;
import org.semanticweb.owlapi.rio.RioN3StorerFactory;
import org.semanticweb.owlapi.rio.RioNQuadsStorerFactory;
import org.semanticweb.owlapi.rio.RioNTriplesStorerFactory;
import org.semanticweb.owlapi.rio.RioRDFXMLStorerFactory;
import org.semanticweb.owlapi.rio.RioTrigStorerFactory;
import org.semanticweb.owlapi.rio.RioTrixStorerFactory;
import org.semanticweb.owlapi.rio.RioTurtleStorerFactory;
import org.semanticweb.owlapi.util.PriorityCollection;

class PriorityCollectionTestCase {

    @Test
    void shouldStoreStorers() {
        List<OWLStorer> storers =
            Arrays.asList(new RioBinaryRdfStorerFactory().apply(any(OWLOntology.class)),
                new RioJsonLDStorerFactory().apply(any(OWLOntology.class)),
                new RioJsonStorerFactory().apply(any(OWLOntology.class)),
                new RioN3StorerFactory().apply(any(OWLOntology.class)),
                new RioNQuadsStorerFactory().apply(any(OWLOntology.class)),
                new RioNTriplesStorerFactory().apply(any(OWLOntology.class)),
                new RioRDFXMLStorerFactory().apply(any(OWLOntology.class)),
                new RioTrigStorerFactory().apply(any(OWLOntology.class)),
                new RioTrixStorerFactory().apply(any(OWLOntology.class)),
                new RioTurtleStorerFactory().apply(any(OWLOntology.class)),
                new OBOFormatStorerFactory().apply(any(OWLOntology.class)),
                new RDFXMLStorerFactory().apply(any(OWLOntology.class)),
                new OWLXMLStorerFactory().apply(any(OWLOntology.class)),
                new FunctionalSyntaxStorerFactory().apply(any(OWLOntology.class)),
                new ManchesterSyntaxStorerFactory().apply(any(OWLOntology.class)),
                new KRSS2OWLSyntaxStorerFactory().apply(any(OWLOntology.class)),
                new TurtleStorerFactory().apply(any(OWLOntology.class)),
                new LatexStorerFactory().apply(any(OWLOntology.class)));
        PriorityCollection<OWLStorer> pc =
            new PriorityCollection<>(PriorityCollectionSorting.ON_SET_INJECTION_ONLY);
        pc.set(storers);
        assertEquals(storers.size(), pc.size(), pc.toString());
    }
}
