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
public class DataQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

    public DataQualifiedCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }

    public boolean matchesStrict(IRI mainNode) {
        return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_QUALIFIED_CARDINALITY) && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY) && isDataRangeStrict(mainNode, OWL_ON_CLASS);
    }

    public boolean matchesLax(IRI mainNode) {
        return isNonNegativeIntegerLax(mainNode, OWL_QUALIFIED_CARDINALITY) && isDataPropertyLax(mainNode, OWL_ON_PROPERTY) && isDataRangeLax(mainNode, OWL_ON_DATA_RANGE);
    }

    public OWLDataExactCardinality translate(IRI mainNode) {
        getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        int cardi = translateInteger(mainNode, OWL_QUALIFIED_CARDINALITY);
        IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
        OWLDataPropertyExpression property = getConsumer().translateDataPropertyExpression(propertyIRI);
        IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_DATA_RANGE, true);
        OWLDataRange filler = getConsumer().translateDataRange(fillerIRI);
        return getDataFactory().getOWLDataExactCardinality(cardi, property, filler);
    }
}
