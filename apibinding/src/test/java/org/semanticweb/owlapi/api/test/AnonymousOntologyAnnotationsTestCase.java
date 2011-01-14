package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14/01/2011
 */
public class AnonymousOntologyAnnotationsTestCase extends AbstractRoundTrippingTest {
    @Override
    protected OWLOntology createOntology() {
        try {
            OWLOntology ont = getManager().createOntology();
            OWLAnnotationProperty prop = getFactory().getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/ontologies/test/annotationont#prop"));
            OWLLiteral value = getFactory().getOWLLiteral(33);
            OWLAnnotation annotation = getFactory().getOWLAnnotation(prop, value);
            getManager().applyChange(new AddOntologyAnnotation(ont, annotation));
            getManager().addAxiom(ont, getFactory().getOWLDeclarationAxiom(prop));
            return ont;
        }
        catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }
}
