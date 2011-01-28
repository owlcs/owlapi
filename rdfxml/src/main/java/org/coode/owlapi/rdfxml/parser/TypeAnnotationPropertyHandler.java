package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 */
public class TypeAnnotationPropertyHandler extends BuiltInTypeHandler{

    public TypeAnnotationPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getIRI());
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (!isAnonymous(subject)) {
            Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
            OWLAnnotationProperty property = getDataFactory().getOWLAnnotationProperty(subject);
            addAxiom(getDataFactory().getOWLDeclarationAxiom(property, annos));
            consumeTriple(subject, predicate, object);
        }
        getConsumer().addAnnotationProperty(subject, true);
    }
}
