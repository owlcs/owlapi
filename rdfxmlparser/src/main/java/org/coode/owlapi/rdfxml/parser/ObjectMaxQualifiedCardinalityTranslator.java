package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class ObjectMaxQualifiedCardinalityTranslator extends AbstractObjectQualifiedCardinalityTranslator {

    public ObjectMaxQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_MAX_QUALIFIED_CARDINALITY.getIRI());
    }

    public OWLObjectMaxCardinality translate(IRI mainNode) {
        return getDataFactory().getOWLObjectMaxCardinality(translateCardinality(mainNode), translateProperty(mainNode), translateFiller(mainNode));
    }
}
