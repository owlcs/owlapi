package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.net.URI;

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
 */
public abstract class AbstractObjectCardinalityTranslator extends AbstractObjectRestrictionTranslator {

    public AbstractObjectCardinalityTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }


    /**
     * Gets the predicate of the cardinality triple (e.g. minCardinality, cardinality,
     * maxCardinality)
     * @return The IRI corresponding to the predicate of the triple that identifies
     * the cardinality of the restriction.
     */
    protected abstract IRI getCardinalityTriplePredicate();

    protected abstract IRI getQualifiedCardinalityTriplePredicate();

    /**
     * Translates and consumes the cardinality triple.
     * @param mainNode The main node of the restriction.
     * @return The cardinality of the restriction or -1 if the cardinality triple was not found
     * @throws OWLException
     */
    private int translateCardinality(IRI mainNode) {
        OWLLiteral con = getLiteralObject(mainNode, getCardinalityTriplePredicate(), true);
        if(con == null) {
            con = getLiteralObject(mainNode, getQualifiedCardinalityTriplePredicate(), true);
        }
        if(con == null) {
            return -1;
        }
        return Integer.parseInt(con.getLiteral().trim());
    }


    /**
     * Translates and consumes the triple that identifies the filler/quantifier for the
     * restriction (the onClass triple at the time of writing). If there is no filler
     * triple then owl:Thing is returned.
     * @param mainNode The main node of the restriction
     * @return The class expression corresponding to the filler (not <code>null</code>)
     * @throws OWLException
     */
    private OWLClassExpression translateFiller(IRI mainNode) {
        IRI onClassObject = getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_CLASS.getIRI(), true);
        if(onClassObject == null) {
            return getDataFactory().getOWLThing();
        }
        return translateToClassExpression(onClassObject);
    }


    protected OWLClassExpression translateRestriction(IRI mainNode) {
        OWLObjectPropertyExpression prop = translateOnProperty(mainNode);
        if(prop == null) {
            return getConsumer().getOWLClass(mainNode);
        }
        int cardi = translateCardinality(mainNode);
        OWLClassExpression filler = translateFiller(mainNode);
        return createRestriction(prop, cardi, filler);
    }


    /**
     * Given a property expression, cardinality and filler, this method creates the appropriate
     * OWLAPI object
     */
    protected abstract OWLClassExpression createRestriction(OWLObjectPropertyExpression prop, int cardi, OWLClassExpression filler);
}
