package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Oct-2009
 */
public class HasValueRestrictionTranslator extends AbstractRestrictionTranslator {

    public HasValueRestrictionTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }

    @Override
	protected OWLClassExpression translateRestriction(IRI mainNode) {
        IRI propertyIRI = getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI(), true);
        IRI fillerIRI = getResourceObject(mainNode, OWLRDFVocabulary.OWL_HAS_VALUE.getIRI(), true);
        OWLClassExpression ce = null;
        if(fillerIRI != null) {
            OWLClassExpression filler = getConsumer().translateClassExpression(fillerIRI);
            
          //  ce = getDataFactory().getOWLObjectHasValue(prop, filler);
        }
        else {
            OWLLiteral lit = getLiteralObject(mainNode, OWLRDFVocabulary.OWL_HAS_VALUE.getIRI(), true);

        }
        return null;
    }
}
