package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TypeObjectPropertyHandler extends BuiltInTypeHandler {

    public TypeObjectPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getIRI());
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (!isAnonymous(subject)) {
            OWLObjectProperty owlObjectProperty = getDataFactory().getOWLObjectProperty(subject);
            Set<OWLAnnotation> annos = getPendingAnnotations();
            addAxiom(getDataFactory().getOWLDeclarationAxiom(owlObjectProperty, annos));
        }
        getConsumer().addObjectProperty(subject, true);
    }
}
