package org.semanticweb.owlapi.api.test.annotations;

import org.semanticweb.owlapi.api.test.baseclasses.AbstractRoundTrippingTestCase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class ThreeLayersOfAnnotationsTestCase extends AbstractRoundTrippingTestCase {

    @Override
    protected OWLOntology createOntology() {
        String oboInOwl = "urn:obo:";
        OWLOntology o;
        try {
            o = m.createOntology(IRI.create("urn:nested:ontology"));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
        OWLClass dbxref = df.getOWLClass(IRI.create(oboInOwl + "DbXref"));
        OWLClass definition = df.getOWLClass(IRI.create(oboInOwl + "Definition"));
        OWLObjectProperty adjacent_to = df.getOWLObjectProperty(IRI.create(oboInOwl + "adjacent_to"));
        OWLAnnotationProperty hasDefinition = df.getOWLAnnotationProperty(IRI.create(oboInOwl + "hasDefinition"));
        OWLAnnotationProperty hasdbxref = df.getOWLAnnotationProperty(IRI.create(oboInOwl + "hasDbXref"));
        OWLDataProperty hasuri = df.getOWLDataProperty(IRI.create(oboInOwl + "hasURI"));
        OWLAnonymousIndividual ind1 = df.getOWLAnonymousIndividual();
        m.addAxiom(o, df.getOWLClassAssertionAxiom(dbxref, ind1));
        m.addAxiom(o, df.getOWLDataPropertyAssertionAxiom(hasuri, ind1, df.getOWLLiteral("urn:SO:SO_ke",
            OWL2Datatype.XSD_ANY_URI)));
        OWLAnonymousIndividual ind2 = df.getOWLAnonymousIndividual();
        m.addAxiom(o, df.getOWLClassAssertionAxiom(definition, ind2));
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(hasdbxref, ind2, ind1));
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(hasDefinition, adjacent_to.getIRI(), ind2));
        return o;
    }

    @Override
    public void testManchesterOWLSyntax() {
        // not supported in Manchester syntax
    }
}
