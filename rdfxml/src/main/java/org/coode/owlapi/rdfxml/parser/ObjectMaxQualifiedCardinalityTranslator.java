package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MAX_QUALIFIED_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_PROPERTY;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class ObjectMaxQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

    public ObjectMaxQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }

    public boolean matchesStrict(IRI mainNode) {
        return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MAX_QUALIFIED_CARDINALITY) && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY) && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
    }

    public boolean matchesLax(IRI mainNode) {
        return isNonNegativeIntegerLax(mainNode, OWL_MAX_QUALIFIED_CARDINALITY) && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY) && isClassExpressionLax(mainNode, OWL_ON_CLASS);
    }

    public OWLObjectMaxCardinality translate(IRI mainNode) {
        int cardi = translateInteger(mainNode, OWL_MAX_QUALIFIED_CARDINALITY);
        IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
        OWLObjectPropertyExpression property = getConsumer().translateObjectPropertyExpression(propertyIRI);
        IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
        OWLClassExpression filler = getConsumer().translateClassExpression(fillerIRI);
        return getDataFactory().getOWLObjectMaxCardinality(cardi, property, filler);
    }
}
