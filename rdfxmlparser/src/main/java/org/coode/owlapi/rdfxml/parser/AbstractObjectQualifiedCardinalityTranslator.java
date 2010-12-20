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
public abstract class AbstractObjectQualifiedCardinalityTranslator extends AbstractObjectCardinalityTranslator {

    public AbstractObjectQualifiedCardinalityTranslator(OWLRDFConsumer consumer, IRI cardinalityPredicate) {
        super(consumer, cardinalityPredicate);
    }

    @Override
    public boolean matches(IRI mainNode) {
        if(!super.matches(mainNode)) {
            return false;
        }
        IRI filler = getConsumer().getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_CLASS, false);
        return filler != null && getConsumer().isClassExpression(filler);
    }

    protected OWLClassExpression translateFiller(IRI mainNode) {
        IRI filler = getConsumer().getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_CLASS, true);
        return getConsumer().translateClassExpression(filler);
    }

}
