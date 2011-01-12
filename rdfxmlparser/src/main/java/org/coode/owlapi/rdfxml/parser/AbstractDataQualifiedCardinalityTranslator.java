package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public abstract class AbstractDataQualifiedCardinalityTranslator extends AbstractDataCardinalityTranslator {

    public AbstractDataQualifiedCardinalityTranslator(OWLRDFConsumer consumer, IRI cardinalityIRI) {
        super(consumer, cardinalityIRI);
    }

    @Override
    public boolean matches(IRI mainNode) {
        if(!super.matches(mainNode)) {
            return false;
        }
        IRI filler = getConsumer().getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_DATA_RANGE.getIRI(), false);
        return filler != null && getConsumer().isDataRange(filler);
    }

    protected OWLDataRange translateDataRange(IRI mainNode) {
        IRI filler = getConsumer().getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_DATA_RANGE.getIRI(), true);
        return getConsumer().translateDataRange(filler);
    }
}
