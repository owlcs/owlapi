package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class DataQualifiedCardinalityTranslator extends AbstractDataQualifiedCardinalityTranslator {

    public DataQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_QUALIFIED_CARDINALITY.getIRI());
    }

    public OWLDataExactCardinality translate(IRI mainNode) {
        return getDataFactory().getOWLDataExactCardinality(translateCardinality(mainNode), translateProperty(mainNode), translateDataRange(mainNode));
    }
}
