package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TypeDatatypeHandler extends BuiltInTypeHandler {

    public TypeDatatypeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_DATATYPE.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (!getConsumer().isAnonymousNode(subject)) {
            OWLDatatype dt = getDataFactory().getOWLDatatype(subject);
            Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
            addAxiom(getDataFactory().getOWLDeclarationAxiom(dt, annos));
        }
        getConsumer().addDataRange(subject, true);
    }
}
