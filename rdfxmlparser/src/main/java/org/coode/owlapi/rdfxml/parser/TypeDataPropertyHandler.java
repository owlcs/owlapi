package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TypeDataPropertyHandler extends BuiltInTypeHandler {

    public TypeDataPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DATA_PROPERTY.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
        addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLDataProperty(subject), annos));
        getConsumer().addOWLDataProperty(subject);
    }
}
