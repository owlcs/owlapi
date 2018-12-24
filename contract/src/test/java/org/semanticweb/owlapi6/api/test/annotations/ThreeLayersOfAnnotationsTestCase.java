package org.semanticweb.owlapi6.api.test.annotations;

import org.semanticweb.owlapi6.api.test.baseclasses.AbstractRoundTrippingTestCase;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
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
        m.addAxiom(o, df.getOWLClassAssertionAxiom(dbxref, ind1));
        m.addAxiom(o, df.getOWLDataPropertyAssertionAxiom(hasuri, ind1,
                        df.getOWLLiteral("urn:SO:SO_ke", OWL2Datatype.XSD_ANY_URI)));
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
