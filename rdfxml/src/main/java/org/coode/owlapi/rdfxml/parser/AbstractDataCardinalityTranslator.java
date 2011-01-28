package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 * <br>
 * Translator for plain cardinality restrictions
 */
public abstract class AbstractDataCardinalityTranslator extends AbstractDataRestrictionTranslator {

    private IRI cardinalityPredicate;

    public AbstractDataCardinalityTranslator(OWLRDFConsumer consumer, IRI cardinalityPredicate) {
        super(consumer);
        this.cardinalityPredicate = cardinalityPredicate;
    }

    @Override
    public boolean matches(IRI mainNode) {
        if(!super.matches(mainNode)) {
            return false;
        }
        OWLLiteral cardinalityLiteral = getConsumer().getLiteralObject(mainNode, cardinalityPredicate, false);
        if(cardinalityLiteral == null) {
            return false;
        }
        if(getConsumer().getConfiguration().isStrict()) {
            OWL2Datatype xsdNNI = OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
            IRI strictType = xsdNNI.getIRI();
            if(!cardinalityLiteral.getDatatype().getIRI().equals(strictType)) {
                return false;
            }
            if(!xsdNNI.isInLexicalSpace(cardinalityLiteral.getLiteral())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Translates and consumes the cardinality triple.
     *
     * @param mainNode The main node of the restriction.
     * @return The cardinality of the restriction.
     */
    public int translateCardinality(IRI mainNode) {
        OWLLiteral cardiObject = getConsumer().getLiteralObject(mainNode, cardinalityPredicate, true);
        return Integer.parseInt(cardiObject.getLiteral().trim());
    }




//    /**
//     * Translates and consumes the triple that identifies the filler/quantifier for the
//     * restriction (the onClass triple at the time of writing). If there is no filler
//     * triple then the top datatype is returned.
//     *
//     * @param mainNode The main node of the restriction
//     * @return The class expression corresponding to the filler (not <code>null</code>)
//     */
//    private OWLDataRange translateFiller(IRI mainNode) {
//        IRI onDataRangeObject = getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_DATA_RANGE.getIRI(), true);
//        if (onDataRangeObject == null) {
//            return getDataFactory().getTopDatatype();
//        }
//        return getConsumer().translateDataRange(onDataRangeObject);
//    }


//    protected OWLClassExpression translateRestriction(IRI mainNode) {
//        int cardinality = translateCardinality(mainNode);
//        if(cardinality < 0) {
//            return getConsumer().getOWLClass(mainNode);
//        }
//        OWLDataPropertyExpression prop = translateOnProperty(mainNode);
//        if(prop == null) {
//            return getConsumer().getOWLClass(mainNode);
//        }
//        return createRestriction(prop, cardinality, translateFiller(mainNode));
//    }
//
//
//    /**
//     * Given a property expression, cardinality and filler, this method creates the appropriate
//     * OWLAPI object
//     */
//    protected abstract OWLClassExpression createRestriction(OWLDataPropertyExpression prop, int cardi,
//                                                            OWLDataRange filler);
}
