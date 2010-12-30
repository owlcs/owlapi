package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21-Feb-2007<br><br>
 */
@SuppressWarnings("deprecation")
public class TPDeclaredAsHandler extends TriplePredicateHandler {

    public TPDeclaredAsHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DECLARED_AS.getIRI());
    }


    @Override@SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }


    @Override@SuppressWarnings("unused")
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (object.equals(OWLRDFVocabulary.OWL_CLASS.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLClass(subject), getPendingAnnotations()));
        }
        else if (object.equals(OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLObjectProperty(subject), getPendingAnnotations()));
        }
        else if (object.equals(OWLRDFVocabulary.OWL_DATA_PROPERTY.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLDataProperty(subject), getPendingAnnotations()));
        }
        else if (object.equals(OWLRDFVocabulary.OWL_DATATYPE.getIRI())) {
            addAxiom(getDataFactory().getOWLDeclarationAxiom(getDataFactory().getOWLDatatype(subject), getPendingAnnotations()));
        }
    }
}
