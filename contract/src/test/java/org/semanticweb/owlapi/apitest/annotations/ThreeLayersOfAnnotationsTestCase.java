package org.semanticweb.owlapi.apitest.annotations;

import org.semanticweb.owlapi.apitest.baseclasses.AbstractRoundTrippingTestCase;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

public class ThreeLayersOfAnnotationsTestCase extends AbstractRoundTrippingTestCase {

    @Override
    protected OWLOntology createOntology() {
        String oboInOwl = "urn:obo:";
        OWLOntology o;
        try {
            o = m.createOntology(df.getIRI("urn:nested:", "ontology"));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
        OWLClass dbxref = df.getOWLClass(df.getIRI(oboInOwl, "DbXref"));
        OWLClass definition = df.getOWLClass(df.getIRI(oboInOwl, "Definition"));
        OWLObjectProperty adjacent_to = df.getOWLObjectProperty(df.getIRI(oboInOwl, "adjacent_to"));
        OWLAnnotationProperty hasDefinition =
            df.getOWLAnnotationProperty(df.getIRI(oboInOwl, "hasDefinition"));
        OWLAnnotationProperty hasdbxref =
            df.getOWLAnnotationProperty(df.getIRI(oboInOwl, "hasDbXref"));
        OWLDataProperty hasuri = df.getOWLDataProperty(df.getIRI(oboInOwl, "hasURI"));
        OWLAnonymousIndividual ind1 = df.getOWLAnonymousIndividual();
        o.addAxiom(df.getOWLClassAssertionAxiom(dbxref, ind1));
        o.addAxiom(df.getOWLDataPropertyAssertionAxiom(hasuri, ind1,
            df.getOWLLiteral("urn:SO:SO_ke", OWL2Datatype.XSD_ANY_URI)));
        OWLAnonymousIndividual ind2 = df.getOWLAnonymousIndividual();
        o.addAxiom(df.getOWLClassAssertionAxiom(definition, ind2));
        o.addAxiom(df.getOWLAnnotationAssertionAxiom(hasdbxref, ind2, ind1));
        o.addAxiom(df.getOWLAnnotationAssertionAxiom(hasDefinition, adjacent_to.getIRI(), ind2));
        return o;
    }

    @Override
    public void testManchesterOWLSyntax() {
        // not supported in Manchester syntax
    }
}
