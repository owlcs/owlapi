package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class DataMinQualifiedCardinalityTranslator extends AbstractDataQualifiedCardinalityTranslator {

    public DataMinQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_MIN_QUALIFIED_CARDINALITY.getIRI());
    }

    public OWLDataMinCardinality translate(IRI mainNode) {
        return getDataFactory().getOWLDataMinCardinality(translateCardinality(mainNode), translateProperty(mainNode), translateDataRange(mainNode));
    }
}
