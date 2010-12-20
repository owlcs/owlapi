package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class ObjectMinQualifiedCardinalityTranslator extends AbstractObjectQualifiedCardinalityTranslator {

    public ObjectMinQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_MIN_QUALIFIED_CARDINALITY.getIRI());
    }

    public OWLClassExpression translate(IRI mainNode) {
        return getDataFactory().getOWLObjectMinCardinality(translateCardinality(mainNode), translateProperty(mainNode), translateFiller(mainNode));
    }
}
