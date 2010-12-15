package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Nov-2009
 */
public class OntologyAnnotationsTestCase extends AbstractRoundTrippingTest {

    @Override
	protected OWLOntology createOntology() {
            OWLOntology ont = getOWLOntology("AnnotationOntology");
            OWLAnnotationProperty prop = getFactory().getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/ontologies/test/annotationont#prop"));
            OWLLiteral value = getFactory().getOWLLiteral(33);
            OWLAnnotation annotation = getFactory().getOWLAnnotation(prop, value);
            getManager().applyChange(new AddOntologyAnnotation(ont, annotation));
            return ont;
    }
}
