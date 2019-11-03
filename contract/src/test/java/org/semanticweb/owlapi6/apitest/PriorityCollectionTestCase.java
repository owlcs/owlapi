package org.semanticweb.owlapi6.apitest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi6.functional.renderer.FunctionalSyntaxStorerFactory;
import org.semanticweb.owlapi6.io.OWLStorer;
import org.semanticweb.owlapi6.krss2.renderer.KRSS2OWLSyntaxStorerFactory;
import org.semanticweb.owlapi6.latex.renderer.LatexStorerFactory;
import org.semanticweb.owlapi6.manchestersyntax.renderer.ManchesterSyntaxStorerFactory;
import org.semanticweb.owlapi6.model.PriorityCollectionSorting;
import org.semanticweb.owlapi6.oboformat.OBOFormatStorerFactory;
import org.semanticweb.owlapi6.owlxml.renderer.OWLXMLStorerFactory;
import org.semanticweb.owlapi6.rdf.rdfxml.renderer.RDFXMLStorerFactory;
import org.semanticweb.owlapi6.rdf.turtle.renderer.TurtleStorerFactory;
import org.semanticweb.owlapi6.rio.RioBinaryRdfStorerFactory;
import org.semanticweb.owlapi6.rio.RioJsonLDStorerFactory;
import org.semanticweb.owlapi6.rio.RioJsonStorerFactory;
import org.semanticweb.owlapi6.rio.RioN3StorerFactory;
import org.semanticweb.owlapi6.rio.RioNQuadsStorerFactory;
import org.semanticweb.owlapi6.rio.RioNTriplesStorerFactory;
import org.semanticweb.owlapi6.rio.RioRDFXMLStorerFactory;
import org.semanticweb.owlapi6.rio.RioTrigStorerFactory;
import org.semanticweb.owlapi6.rio.RioTrixStorerFactory;
import org.semanticweb.owlapi6.rio.RioTurtleStorerFactory;
import org.semanticweb.owlapi6.utilities.PriorityCollection;

public class PriorityCollectionTestCase {

    @Test
    public void shouldStoreStorers() {
        List<OWLStorer> storers =
            Arrays.asList(new RioBinaryRdfStorerFactory().get(), new RioJsonLDStorerFactory().get(),
                new RioJsonStorerFactory().get(), new RioN3StorerFactory().get(),
                new RioNQuadsStorerFactory().get(), new RioNTriplesStorerFactory().get(),
                new RioRDFXMLStorerFactory().get(), new RioTrigStorerFactory().get(),
                new RioTrixStorerFactory().get(), new RioTurtleStorerFactory().get(),
                new OBOFormatStorerFactory().get(), new RDFXMLStorerFactory().get(),
                new OWLXMLStorerFactory().get(), new FunctionalSyntaxStorerFactory().get(),
                new ManchesterSyntaxStorerFactory().get(), new KRSS2OWLSyntaxStorerFactory().get(),
                new TurtleStorerFactory().get(), new LatexStorerFactory().get());
        PriorityCollection<OWLStorer> pc =
            new PriorityCollection<>(PriorityCollectionSorting.ON_SET_INJECTION_ONLY);
        pc.set(storers);
        assertEquals(pc.toString(), storers.size(), pc.size());
    }
}
