package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxStorerFactory;
import org.semanticweb.owlapi.krss2.renderer.KRSS2OWLSyntaxStorerFactory;
import org.semanticweb.owlapi.latex.renderer.LatexStorerFactory;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterSyntaxStorerFactory;
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

@SuppressWarnings("javadoc")
public class PriorityCollectionTestCase {

    @Test
    public void shouldStoreStorers() {
        List<OWLStorer> storers = Arrays
                .asList(new RioBinaryRdfStorerFactory().get(),
                        new RioJsonLDStorerFactory().get(),
                        new RioJsonStorerFactory().get(),
                        new RioN3StorerFactory().get(),
                        new RioNQuadsStorerFactory().get(),
                        new RioNTriplesStorerFactory().get(),
                        new RioRDFXMLStorerFactory().get(),
                        new RioTrigStorerFactory().get(),
                        new RioTrixStorerFactory().get(),
                        new RioTurtleStorerFactory().get(),
                        new OBOFormatStorerFactory().get(),
                        new RDFXMLStorerFactory().get(),
                        new OWLXMLStorerFactory().get(),
                        new FunctionalSyntaxStorerFactory().get(),
                        new ManchesterSyntaxStorerFactory().get(),
                        new KRSS2OWLSyntaxStorerFactory().get(),
                        new TurtleStorerFactory().get(),
                        new LatexStorerFactory().get());
        PriorityCollection<OWLStorer> pc = new PriorityCollection<>(PriorityCollectionSorting.ON_SET_INJECTION_ONLY);
        pc.set(storers);
        assertEquals(pc.toString(), storers.size(), pc.size());
    }
}
