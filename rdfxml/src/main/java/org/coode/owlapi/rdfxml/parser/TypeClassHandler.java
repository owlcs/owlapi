package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TypeClassHandler extends BuiltInTypeHandler {

    public TypeClassHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_CLASS.getIRI());
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (!isAnonymous(subject)) {
            Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
            OWLClass owlClass = getDataFactory().getOWLClass(subject);
            addAxiom(getDataFactory().getOWLDeclarationAxiom(owlClass, annos));
        }
        getConsumer().addClassExpression(subject, true);
    }
}
