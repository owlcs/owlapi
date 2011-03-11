package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class ObjectQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

    public ObjectQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }

    public boolean matchesStrict(IRI mainNode) {
        return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_QUALIFIED_CARDINALITY) && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY) && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
    }

    public boolean matchesLax(IRI mainNode) {
        return isNonNegativeIntegerLax(mainNode, OWL_QUALIFIED_CARDINALITY) && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY) && isClassExpressionLax(mainNode, OWL_ON_CLASS);
    }

    public OWLObjectExactCardinality translate(IRI mainNode) {
        int cardi = translateInteger(mainNode, OWL_QUALIFIED_CARDINALITY);
        IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
        OWLObjectPropertyExpression property = getConsumer().translateObjectPropertyExpression(propertyIRI);
        IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
        OWLClassExpression filler = getConsumer().translateClassExpression(fillerIRI);
        return getDataFactory().getOWLObjectExactCardinality(cardi, property, filler);
    }
}
