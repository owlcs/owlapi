package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class ObjectQualifiedCardinalityTranslator extends AbstractObjectQualifiedCardinalityTranslator {

    public ObjectQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_QUALIFIED_CARDINALITY.getIRI());
    }

    public OWLObjectExactCardinality translate(IRI mainNode) {
        return getDataFactory().getOWLObjectExactCardinality(translateCardinality(mainNode), translateProperty(mainNode), translateFiller(mainNode));
    }
}
